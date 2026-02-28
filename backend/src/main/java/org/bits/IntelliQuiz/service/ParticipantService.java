package org.bits.IntelliQuiz.service;

import org.bits.IntelliQuiz.dto.ParticipantQuizResponse;
import org.bits.IntelliQuiz.dto.QuizResultResponse;

import java.util.List;
import java.util.Map;

public interface ParticipantService {

    List<ParticipantQuizResponse> getInvitedQuizzes();

    void startQuizAttempt(Long quizId);

    void submitQuizAttempt(Long quizId, Map<Long, Integer> answers);

    QuizResultResponse getQuizResult(Long quizId);
}

