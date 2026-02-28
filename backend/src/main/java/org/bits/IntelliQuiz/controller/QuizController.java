package org.bits.IntelliQuiz.controller;

import jakarta.validation.Valid;
import org.bits.IntelliQuiz.ai.AiQuestionGenerationService;
import org.bits.IntelliQuiz.ai.dto.AiQuestionRequest;
import org.bits.IntelliQuiz.dto.*;
import org.bits.IntelliQuiz.entities.Question;
import org.bits.IntelliQuiz.entities.Quiz;
import org.bits.IntelliQuiz.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/quizzes")
public class QuizController {

    private final QuizService quizService;
    private final AiQuestionGenerationService aiService;


    public QuizController(QuizService quizService,AiQuestionGenerationService aiService) {
        this.quizService = quizService;
        this.aiService = aiService;

    }

    // CREATE QUIZ (DRAFT)
    @PostMapping
    public ResponseEntity<QuizResponse> createQuiz(
            @Valid @RequestBody CreateQuizRequest request) {

        Quiz quiz = quizService.createQuiz(
                request.getQuizTitle(),
                request.getSubject()
        );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new QuizResponse(
                        quiz.getId(),
                        quiz.getQuizTitle(),
                        quiz.getSubject(),
                        quiz.getState().name(),
                        quiz.isResultPublished()
                ));
    }



    //   ------------Get Quiz by ID-------------
    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponse> getQuizById(
            @PathVariable Long quizId) {

        Quiz quiz = quizService.getQuizForAdminById(quizId);

        return ResponseEntity.ok(new QuizResponse(
                quiz.getId(),
                quiz.getQuizTitle(),
                quiz.getSubject(),
                quiz.getState().name(),
                quiz.isResultPublished()
        ));
    }
//Get all Quizzes of admin
    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuizzesForAdmin() {

        List<QuizResponse> response = quizService.getQuizzesForAdmin()
                .stream()
                .map(q -> new QuizResponse(
                        q.getId(),
                        q.getQuizTitle(),
                        q.getSubject(),
                        q.getState().name(),
                        q.isResultPublished()))
                .toList();

        return ResponseEntity.ok(response);
    }

    //    -----------Delete Quiz-------------
    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void>deleteQuiz(@PathVariable Long quizId){
        quizService.deleteQuiz(quizId);
        return ResponseEntity.ok().build();
    }

    //------------add questions to quiz------
    @PostMapping("/{quizId}/questions")
    public ResponseEntity<Void> addQuestionsToQuiz(
            @PathVariable Long quizId,
            @RequestBody List<CreateQuestionRequest> requests) {

        quizService.addQuestionsToQuiz(quizId, requests);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//-----------add question to quiz-------
    @PostMapping("/{quizId}/question")
    public ResponseEntity<Void> addQuestionToQuiz(
            @PathVariable Long quizId,
            @RequestBody CreateQuestionRequest requests) {

        quizService.addQuestionToQuiz(quizId, requests);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
//-------add AI Generated Questions--------
    @PostMapping("/{quizId}/generate-questions")
    public ResponseEntity<List<Question>> generateQuestions(
            @PathVariable Long quizId,
            @RequestBody @Valid AiQuestionRequest request
    ) {

        List<Question> questions =
                aiService.generateAndSaveQuestions(request, quizId);

        return ResponseEntity.ok(questions);
    }

//----------get questions in quiz--------
    @GetMapping("/{quizId}/questions")
    public ResponseEntity<List<QuestionResponse>> getQuestions(
            @PathVariable Long quizId) {

         List<QuestionResponse> questions =
               quizService.getQuestionsForQuiz(quizId);

         return ResponseEntity.ok(questions);
    }

    //    --------Delete Question------
    @DeleteMapping("/{quizId}/questions/{questionId}")
    public ResponseEntity<Void> deleteQuestion(
            @PathVariable Long quizId,
            @PathVariable Long questionId) {

        quizService.deleteQuestion(quizId, questionId);
        return ResponseEntity.ok().build();
    }


//----------add participant-----------
    @PostMapping("/{quizId}/participants")
    public ResponseEntity<Void> addParticipantsToQuiz(
            @PathVariable Long quizId,
            @RequestBody AddParticipantsRequest request) {

        quizService.addParticipantsToQuiz(quizId, request.getEmails());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    --------Get Participants--------
    @GetMapping("/{quizId}/participants")
    public ResponseEntity<List<String>> getParticipants(
            @PathVariable Long quizId) {

        List<String> participants =
                quizService.getParticipantsForQuiz(quizId);

        return ResponseEntity.ok(participants);
    }

    //    -------------Remove participant----------
    @DeleteMapping("/{quizId}/participants/{participantId}")
    public ResponseEntity<Void>removeParticipant(
            @PathVariable Long quizId,
            @PathVariable Long participantId){
        quizService.removeParticipant(quizId,participantId);
        return ResponseEntity.ok().build();
    }

//    -------Schedule Quiz---------
    @PutMapping("/{quizId}/schedule")
    public ResponseEntity<Void> scheduleQuiz(
            @PathVariable Long quizId,
            @RequestBody ScheduleQuizRequest request) {

        quizService.scheduleQuiz(
                quizId,
                request.getStartTime(),
                request.getEndTime()
        );

        return ResponseEntity.ok().build();
    }


//-----Publish Results-------
    @PostMapping("/{quizId}/publish-results")
    public ResponseEntity<Void> publishResults(
            @PathVariable Long quizId) {

        quizService.publishResults(quizId);
        return ResponseEntity.ok().build();
    }







}
