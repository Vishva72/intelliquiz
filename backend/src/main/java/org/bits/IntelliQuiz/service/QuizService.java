package org.bits.IntelliQuiz.service;

import org.bits.IntelliQuiz.dto.CreateQuestionRequest;
import org.bits.IntelliQuiz.dto.QuestionResponse;
import org.bits.IntelliQuiz.entities.Quiz;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizService {

    Quiz createQuiz(String quizTitle, String subject);

    Quiz getQuizForAdminById(Long quizId);

    List<Quiz> getQuizzesForAdmin();

    void addQuestionsToQuiz(Long quizId, List<CreateQuestionRequest> requests);

    void addQuestionToQuiz(Long quizId, CreateQuestionRequest question);

    void scheduleQuiz(Long quizId, LocalDateTime startTime, LocalDateTime endTime);

    void addParticipantsToQuiz(Long quizId, List<String> emails);

    void publishResults(Long quizId);

    void handleQuizStateTransitions();

    void autoSubmitAttempts(Quiz quiz);

    void sendQuizReminders();

    List<QuestionResponse> getQuestionsForQuiz(Long quizId);

    List<String> getParticipantsForQuiz(Long quizId);

    void deleteQuestion(Long quizId, Long questionId);

    void deleteQuiz(Long quizId);

    void removeParticipant(Long quizId, Long participantId);
}

