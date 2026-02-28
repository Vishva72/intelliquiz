package org.bits.IntelliQuiz.ai.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.bits.IntelliQuiz.enums.DifficultyLevel;

public class AiQuestionRequest {

    private final String topic;
    private final DifficultyLevel difficulty;
    @Max(5)
    @Min(1)
    private final int numberOfQuestions;

    public AiQuestionRequest(
            String topic,
            DifficultyLevel difficulty,
            int numberOfQuestions) {

        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("Topic must not be empty");
        }
        if (numberOfQuestions <= 0) {
            throw new IllegalArgumentException("Question count must be positive");
        }

        this.topic = topic;
        this.difficulty = difficulty;
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getTopic() {
        return topic;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }
}