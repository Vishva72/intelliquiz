package org.bits.IntelliQuiz.dto;

import org.bits.IntelliQuiz.enums.QuizState;

import java.time.LocalDateTime;

public class ParticipantQuizResponse {
    private Long quizId;
    private String quizTitle;
    private QuizState state;
    private LocalDateTime quizEndTime;

    private boolean started;
    private boolean submitted;

    private LocalDateTime quizStartTime;

    public Long getQuizId() {
        return quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public QuizState getState() {
        return state;
    }

    public LocalDateTime getQuizStartTime() {
        return quizStartTime;
    }

    public LocalDateTime getQuizEndTime() {
        return quizEndTime;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public ParticipantQuizResponse(
            Long quizId,
            String quizTitle,
            QuizState state,
            LocalDateTime quizStartTime,
            LocalDateTime quizEndTime,
            boolean started,
            boolean submitted
    ) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.state = state;
        this.quizStartTime = quizStartTime;
        this.quizEndTime = quizEndTime;
        this.started = started;
        this.submitted = submitted;
    }
}
