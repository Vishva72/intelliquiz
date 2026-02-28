package org.bits.IntelliQuiz.controller;

import org.bits.IntelliQuiz.dto.*;
import org.bits.IntelliQuiz.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    // ---------------- PARTICIPANT DASHBOARD ----------------
    @GetMapping("/quizzes")
    public ResponseEntity<List<ParticipantQuizResponse>> getInvitedQuizzes() {

        List<ParticipantQuizResponse> response =
                participantService.getInvitedQuizzes();

        return ResponseEntity.ok(response);
    }

    // ---------------- START QUIZ ----------------
    @PostMapping("/{quizId}/start")
    public ResponseEntity<Void> startQuiz(
            @PathVariable Long quizId) {

        participantService.startQuizAttempt(quizId);
        return ResponseEntity.ok().build();
    }

    // ---------------- SUBMIT QUIZ ----------------
    @PostMapping("/{quizId}/submit")
    public ResponseEntity<Void> submitQuiz(
            @PathVariable Long quizId,
            @RequestBody SubmitQuizRequest request) {

        participantService.submitQuizAttempt(
                quizId,
                request.getAnswers()
        );
        return ResponseEntity.ok().build();
    }

    // ---------------- VIEW RESULT ----------------
    @GetMapping("/quizzes/{quizId}/result")
    public ResponseEntity<QuizResultResponse> viewResult(
            @PathVariable Long quizId) {

        QuizResultResponse response =
                participantService.getQuizResult(quizId);

        return ResponseEntity.ok(response);
    }

}
