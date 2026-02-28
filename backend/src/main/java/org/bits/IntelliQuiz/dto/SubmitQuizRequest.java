package org.bits.IntelliQuiz.dto;

import java.util.Map;

public class SubmitQuizRequest {
    // questionId -> selectedOption (1–4)
    private Map<Long, Integer> answers;

    public Map<Long, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Integer> answers) {
        this.answers = answers;
    }
}
