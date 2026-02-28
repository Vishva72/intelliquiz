package org.bits.IntelliQuiz.dto;

public class QuizResultResponse {

    private Long quizId;
    private String quizTitle;
    private int score;
    private int totalQuestions;
    private boolean submitted;

    public QuizResultResponse(
            Long quizId,
            String quizTitle,
            int score,
            int totalQuestions,
            boolean submitted
    ) {
        this.quizId = quizId;
        this.quizTitle = quizTitle;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.submitted = submitted;
    }

    // getters only

    public Long getQuizId() {
        return quizId;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public boolean isSubmitted() {
        return submitted;
    }
}

