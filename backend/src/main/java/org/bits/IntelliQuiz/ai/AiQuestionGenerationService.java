package org.bits.IntelliQuiz.ai;

import jakarta.validation.Valid;
import org.bits.IntelliQuiz.ai.dto.AiQuestionRequest;
import org.bits.IntelliQuiz.entities.Question;

import java.util.List;

public interface AiQuestionGenerationService {

    /**
     * Generates multiple-choice questions using AI.
     * This method:
     * - Calls an external AI provider
     * - Parses and validates the response
     * - Returns structured question data
     */

    List<Question> generateAndSaveQuestions(
            @Valid AiQuestionRequest request,
            Long quizId);

}
