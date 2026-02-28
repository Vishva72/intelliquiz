package org.bits.IntelliQuiz.service;

import jakarta.transaction.Transactional;

import org.bits.IntelliQuiz.dto.ParticipantQuizResponse;
import org.bits.IntelliQuiz.dto.QuizResultResponse;
import org.bits.IntelliQuiz.entities.Participant;
import org.bits.IntelliQuiz.entities.Question;
import org.bits.IntelliQuiz.entities.Quiz;
import org.bits.IntelliQuiz.entities.QuizAttempt;
import org.bits.IntelliQuiz.enums.QuizState;
import org.bits.IntelliQuiz.repository.ParticipantRepository;
import org.bits.IntelliQuiz.repository.QuizAttemptRepository;
import org.bits.IntelliQuiz.repository.QuizRepository;
import org.bits.IntelliQuiz.security.SecurityUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository participantRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final QuizRepository quizRepository;

    public ParticipantServiceImpl(ParticipantRepository participantRepository,
                                  QuizAttemptRepository quizAttemptRepository,
                                  QuizRepository quizRepository) {

        this.participantRepository = participantRepository;
        this.quizAttemptRepository = quizAttemptRepository;
        this.quizRepository = quizRepository;
    }

    private Participant getCurrentParticipant() {
        Long participantId = SecurityUtils.getCurrentUserId();
        return participantRepository.findById(participantId)
                .orElseThrow(() ->
                        new IllegalStateException("Authenticated participant not found"));
    }

    @Override
    public List<ParticipantQuizResponse> getInvitedQuizzes() {

        Participant participant = getCurrentParticipant();

        List<QuizAttempt> attempts =
                quizAttemptRepository.findByParticipant(participant);

        return attempts.stream()
                .map(attempt -> {
                    Quiz quiz = attempt.getQuiz();
                    return new ParticipantQuizResponse(
                            quiz.getId(),
                            quiz.getQuizTitle(),
                            quiz.getState(),
                            quiz.getStartTime(),
                            quiz.getEndTime(),
                            attempt.getStartTime() != null,
                            attempt.isSubmitted()
                    );
                })
                .toList();
    }

    // ---------------- START QUIZ ----------------
    @Transactional
    @Override
    public void startQuizAttempt(Long quizId) {

        Participant participant = getCurrentParticipant();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quiz.getState() != QuizState.LIVE) {
            throw new IllegalStateException("Quiz is not live");
        }

        QuizAttempt attempt = quizAttemptRepository
                .findByQuizAndParticipant(quiz, participant)
                .orElseThrow(() ->
                        new IllegalStateException("Not invited to quiz"));

        if (attempt.getStartTime() != null) {
            throw new IllegalStateException("Quiz already started");
        }

        if (LocalDateTime.now().isAfter(quiz.getEndTime())) {
            throw new IllegalStateException("Quiz time is over");
        }

        attempt.setStartTime(LocalDateTime.now());
        quizAttemptRepository.save(attempt);
    }


    // ---------------- SUBMIT QUIZ ----------------
    @Transactional
    @Override
    public void submitQuizAttempt(
            Long quizId,
            Map<Long, Integer> answers) {

        Participant participant = getCurrentParticipant();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        QuizAttempt attempt = quizAttemptRepository
                .findByQuizAndParticipant(quiz, participant)
                .orElseThrow(() ->
                        new RuntimeException("No attempt found"));

        if (attempt.isSubmitted()) {
            throw new IllegalStateException("Already submitted");
        }

        if (attempt.getStartTime() == null) {
            throw new IllegalStateException("Quiz not started");
        }

        if (LocalDateTime.now().isAfter(quiz.getEndTime())) {
            throw new IllegalStateException("Quiz time is over");
        }

        int score = 0;
        for (Question question : quiz.getQuestions()) {
            Integer selected = answers.get(question.getId());
            if (selected != null &&
                    selected.equals(question.getCorrectOption())) {
                score++;
            }
        }

        attempt.setScore(score);
        attempt.setSubmitted(true);
        attempt.setEndTime(LocalDateTime.now());

        quizAttemptRepository.save(attempt);
    }





    @Transactional
    @Override
    public QuizResultResponse getQuizResult(Long quizId) {

        Participant participant = getCurrentParticipant();

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quiz.getState() != QuizState.CLOSED) {
            throw new IllegalStateException("Quiz not closed yet");
        }

        if (!quiz.isResultPublished()) {
            throw new IllegalStateException("Results not published yet");
        }

        QuizAttempt attempt = quizAttemptRepository
                .findByQuizAndParticipant(quiz, participant)
                .orElseThrow(() ->
                        new RuntimeException("No attempt found"));

        return new QuizResultResponse(
                quiz.getId(),
                quiz.getQuizTitle(),
                attempt.getScore(),
                quiz.getQuestions().size(),
                attempt.isSubmitted()
        );
    }



}

