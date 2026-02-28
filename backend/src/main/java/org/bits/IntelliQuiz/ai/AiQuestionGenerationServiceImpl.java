package org.bits.IntelliQuiz.ai;

import org.bits.IntelliQuiz.ai.dto.AiQuestionResponse;
import org.bits.IntelliQuiz.ai.dto.AiQuestionRequest;
import org.bits.IntelliQuiz.entities.Question;
import org.bits.IntelliQuiz.entities.Quiz;
import org.bits.IntelliQuiz.enums.QuizState;
import org.bits.IntelliQuiz.repository.QuestionRepository;
import org.bits.IntelliQuiz.repository.QuizRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiQuestionGenerationServiceImpl
        implements AiQuestionGenerationService {

    private final ChatClient chatClient;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public AiQuestionGenerationServiceImpl(
            ChatClient chatClient,
            QuestionRepository questionRepository,
            QuizRepository quizRepository) {

        this.chatClient = chatClient;
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> generateAndSaveQuestions(
            AiQuestionRequest request,
            Long quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Quiz not found with id: " + quizId));

        if (quiz.getState() != QuizState.DRAFT) {
            throw new IllegalStateException(
                    "Questions can be added only in DRAFT state");
        }


        String prompt = buildPrompt(request);

        List<AiQuestionResponse> aiResponses =
                chatClient.prompt()
                        .user(prompt)
                        .call()
                        .entity(new ParameterizedTypeReference<
                                List<AiQuestionResponse>>() {
                        });

        List<Question> questions = aiResponses.stream()
                .map(dto -> {
                    validate(dto);
                    return mapToEntity(dto, quiz);
                })
                .toList();

        return questionRepository.saveAll(questions);
    }

    private String buildPrompt(AiQuestionRequest request) {
        return String.format(
                PROMPT_TEMPLATE,
                request.getNumberOfQuestions(),
                request.getTopic(),
                request.getDifficulty()
        );
    }

    private void validate(AiQuestionResponse dto) {

        if (dto.getQuestionTitle() == null || dto.getQuestionTitle().isBlank()) {
            throw new IllegalStateException("AI returned empty question title");
        }

        if (dto.getCorrectOption() == null ||
                dto.getCorrectOption() < 1 ||
                dto.getCorrectOption() > 4) {

            throw new IllegalStateException(
                    "AI returned invalid correctOption");
        }

        if (dto.getOption1() == null ||
                dto.getOption2() == null ||
                dto.getOption3() == null ||
                dto.getOption4() == null) {

            throw new IllegalStateException(
                    "AI returned incomplete options");
        }
    }

    private Question mapToEntity(
            AiQuestionResponse dto,
            Quiz quiz) {

        Question question = new Question();

        question.setQuestionTitle(dto.getQuestionTitle());
        question.setOption1(dto.getOption1());
        question.setOption2(dto.getOption2());
        question.setOption3(dto.getOption3());
        question.setOption4(dto.getOption4());
        question.setCorrectOption(dto.getCorrectOption());
        question.setQuiz(quiz);

        return question;
    }

    private static final String PROMPT_TEMPLATE = """
You are an expert question paper setter.

Generate %d multiple-choice questions.

Topic: %s
Difficulty: %s

Rules:
1. Each question must have exactly 4 options.
2. Use fields: questionTitle, option1, option2, option3, option4.
3. correctOption must be a number between 1 and 4.
4. Do NOT include explanations.
5. Do NOT include markdown.
6. Output ONLY valid JSON array.

JSON format:
[
  {
    "questionTitle": "",
    "option1": "",
    "option2": "",
    "option3": "",
    "option4": "",
    "correctOption": 1
  }
]
""";

}
