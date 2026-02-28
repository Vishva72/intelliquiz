package org.bits.IntelliQuiz.dto;

public class QuizResponse {
    private Long id;
    private String quizTitle;
    private String subject;
    private String state;
    private boolean resultPublished;

    public QuizResponse(Long id, String quizTitle, String subject, String state, boolean resultPublished) {
        this.id = id;
        this.quizTitle = quizTitle;
        this.subject = subject;
        this.state = state;
        this.resultPublished = resultPublished;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isResultPublished() {
        return resultPublished;
    }

    public void setResultPublished(boolean resultPublished) {
        this.resultPublished = resultPublished;
    }
}
