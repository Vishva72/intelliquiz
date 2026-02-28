package org.bits.IntelliQuiz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateQuizRequest {
    @NotBlank(message = "Quiz title cannot be empty")
    @Size(max = 100, message = "Quiz title cannot exceed 100 characters")
    private String quizTitle;
    @NotBlank(message = "Subject cannot be empty")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private String subject;

    public CreateQuizRequest() {}

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
}
