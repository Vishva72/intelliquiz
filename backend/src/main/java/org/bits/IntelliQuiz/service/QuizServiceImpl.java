package org.bits.IntelliQuiz.service;

import jakarta.transaction.Transactional;
import org.bits.IntelliQuiz.Util.PasswordGenerator;
import org.bits.IntelliQuiz.dto.CreateQuestionRequest;
import org.bits.IntelliQuiz.dto.QuestionResponse;
import org.bits.IntelliQuiz.entities.*;
import org.bits.IntelliQuiz.enums.QuizState;
import org.bits.IntelliQuiz.exceptions.ParticipantNotFoundException;
import org.bits.IntelliQuiz.exceptions.QuizNotFoundException;
import org.bits.IntelliQuiz.repository.ParticipantRepository;
import org.bits.IntelliQuiz.repository.QuizAttemptRepository;
import org.bits.IntelliQuiz.repository.QuizRepository;
import org.bits.IntelliQuiz.service.mail.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizServiceImpl implements QuizService{
    private final QuizRepository quizRepository;
    private final AdminService adminService;
    private final QuizAttemptRepository quizAttemptRepository;
    private final ParticipantRepository participantRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;


    public QuizServiceImpl(QuizRepository quizRepository,
                           AdminService adminService,
                           QuizAttemptRepository quizAttemptRepository,
                            ParticipantRepository participantRepository,
                           EmailService emailService,
                           PasswordEncoder passwordEncoder) {
        this.quizRepository = quizRepository;
        this.adminService = adminService;
        this.quizAttemptRepository = quizAttemptRepository;
        this.participantRepository = participantRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Quiz createQuiz(String quizTitle, String subject) {

        Admin admin = adminService.getCurrentAdmin();

        Quiz quiz = new Quiz();
        quiz.setQuizTitle(quizTitle.trim());
        quiz.setSubject(subject.trim());
        quiz.setAdmin(admin);
        quiz.setState(QuizState.DRAFT);
        quiz.setResultPublished(false);

        return quizRepository.save(quiz);
    }

    @Override
    public Quiz getQuizForAdminById(Long quizId) {

        adminService.validateAdminOwnership(quizId);

        return quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));
    }

    @Override
    public List<Quiz> getQuizzesForAdmin() {

        Admin admin = adminService.getCurrentAdmin();
        return quizRepository.findByAdmin(admin);
    }

    @Transactional
    @Override
    public void addQuestionsToQuiz(
            Long quizId,
            List<CreateQuestionRequest> requests) {

        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        if (quiz.getState() != QuizState.DRAFT) {
            throw new IllegalStateException(
                    "Questions can be added only in DRAFT state");
        }

        for (CreateQuestionRequest req : requests) {

            if (req.getCorrectOption() == null ||
                    req.getCorrectOption() < 1 ||
                    req.getCorrectOption() > 4 ) {
                throw new IllegalArgumentException("Invalid correct option");
            }

            Question question = new Question(
                    req.getQuestionTitle(),
                    req.getOption1(),
                    req.getOption2(),
                    req.getOption3(),
                    req.getOption4(),
                    req.getCorrectOption()
            );

            quiz.addQuestion(question);
        }

        quizRepository.save(quiz);
    }

    @Override
    public void addQuestionToQuiz(Long quizId, CreateQuestionRequest req) {
        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        if (quiz.getState() != QuizState.DRAFT) {
            throw new IllegalStateException(
                    "Questions can be added only in DRAFT state");
        }
        if (req.getCorrectOption() == null ||
                req.getCorrectOption() < 1 ||
                req.getCorrectOption() > 4 ) {
            throw new IllegalArgumentException("Invalid correct option");
        }
        Question question = new Question(
                req.getQuestionTitle(),
                req.getOption1(),
                req.getOption2(),
                req.getOption3(),
                req.getOption4(),
                req.getCorrectOption()
        );
        quiz.addQuestion(question);

        quizRepository.save(quiz);

    }


    @Transactional
    @Override
    public void scheduleQuiz(
            Long quizId,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        if (quiz.getState() != QuizState.DRAFT) {
            throw new IllegalStateException("Only DRAFT quizzes can be scheduled");
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalArgumentException("Invalid time range");
        }

        if (quiz.getQuestions().isEmpty()) {
            throw new IllegalStateException("Quiz must have questions");
        }

        if (!quizAttemptRepository.existsByQuiz(quiz)) {
            throw new IllegalStateException("Quiz must have participants");
        }

        quiz.setStartTime(startTime);
        quiz.setEndTime(endTime);
        quiz.setState(QuizState.SCHEDULED);
        quizRepository.save(quiz);

        List<QuizAttempt> attempts =
                quizAttemptRepository.findByQuiz(quiz);

        for (QuizAttempt attempt : attempts) {
            emailService.sendQuizInvitation(
                    attempt.getParticipant().getEmail(),
                    quiz.getQuizTitle(),
                    quiz.getStartTime(),
                    quiz.getEndTime()
            );
        }


    }

    @Transactional
    @Override
    public void addParticipantsToQuiz(Long quizId, List<String> emails) {

        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        if (quiz.getState() != QuizState.DRAFT) {
            throw new IllegalStateException(
                    "Participants can be added only in DRAFT state");
        }

        for (String rawEmail : emails) {

            String email = rawEmail.trim().toLowerCase();

            Participant participant =
                    participantRepository.findByEmail(email).orElse(null);

            boolean isNew = false;
            String password = null;

            if (participant == null) {
                isNew = true;
                password = PasswordGenerator.generate();
                String encodedPassword = passwordEncoder.encode(password);
                participant = participantRepository.save(
                        new Participant(email, encodedPassword));
            }

            if (quizAttemptRepository.existsByQuizAndParticipant(quiz, participant)) {
                continue;
            }

            quizAttemptRepository.save(
                    new QuizAttempt(participant, quiz));

            if (isNew) {
                emailService.sendParticipantCredentials(
                        email,
                        password
                );
            }

        }
    }

    @Transactional
    @Override
    public void autoSubmitAttempts(Quiz quiz) {

        List<QuizAttempt> attempts =
                quizAttemptRepository.findByQuizAndSubmittedFalse(quiz);

        LocalDateTime now = LocalDateTime.now();

        for (QuizAttempt attempt : attempts) {

            // Only auto-submit if participant actually started
            if (attempt.getStartTime() == null) {
                continue; // never started → ignore
            }

            int score = 0;

            for (Question question : quiz.getQuestions()) {
                Integer selected =
                        attempt.getAnswers().get(question.getId());

                if (selected != null &&
                        selected.equals(question.getCorrectOption())) {
                    score++;
                }
            }

            attempt.setScore(score);
            attempt.setSubmitted(true);
            attempt.setEndTime(now);
        }

        quizAttemptRepository.saveAll(attempts);
    }

    @Transactional
    @Override
    public void handleQuizStateTransitions() {

        LocalDateTime now = LocalDateTime.now();

        //SCHEDULED → LIVE
        List<Quiz> toGoLive =
                quizRepository.findByStateAndStartTimeBefore(
                        QuizState.SCHEDULED, now);

        for (Quiz quiz : toGoLive) {
            quiz.setState(QuizState.LIVE);
        }
        quizRepository.saveAll(toGoLive);

        //LIVE → CLOSED
        List<Quiz> toClose =
                quizRepository.findByStateAndEndTimeBefore(
                        QuizState.LIVE, now);

        for (Quiz quiz : toClose) {
            quiz.setState(QuizState.CLOSED);

            //  auto-submit attempts
            autoSubmitAttempts(quiz);
        }

        quizRepository.saveAll(toClose);
    }

    @Transactional
    @Override
    public void publishResults(Long quizId) {

        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        if (quiz.getState() != QuizState.CLOSED) {
            throw new IllegalStateException("Quiz must be CLOSED");
        }

        if (quiz.isResultPublished()) {
            throw new IllegalStateException("Results already published");
        }

        quiz.setResultPublished(true);
        quizRepository.save(quiz);
    }

    @Transactional
    @Override
    public void sendQuizReminders() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderWindowEnd = now.plusMinutes(5);

        List<Quiz> upcomingQuizzes =
                quizRepository.findByStateAndStartTimeBetween(
                        QuizState.SCHEDULED,
                        now,
                        reminderWindowEnd
                );

        for (Quiz quiz : upcomingQuizzes) {

            List<QuizAttempt> attempts =
                    quizAttemptRepository.findByQuiz(quiz);

            for (QuizAttempt attempt : attempts) {

                emailService.sendQuizReminder(
                        attempt.getParticipant().getEmail(),
                        quiz.getQuizTitle(),
                        quiz.getStartTime()
                );
            }
        }
    }

    @Override
    public List<QuestionResponse> getQuestionsForQuiz(Long quizId) {

        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        return quiz.getQuestions()
                .stream()
                .map(q -> new QuestionResponse(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4(),
                        q.getCorrectOption()
                ))
                .toList();
    }

    @Override
    public List<String> getParticipantsForQuiz(Long quizId) {

        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        return quizAttemptRepository.findByQuiz(quiz)
                .stream()
                .map(attempt -> attempt.getParticipant().getEmail())
                .toList();
    }

    @Override
    @Transactional
    public void deleteQuestion(Long quizId, Long questionId) {

        // Validate admin owns quiz
        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        if (quiz.getState() != QuizState.DRAFT) {
            throw new IllegalStateException(
                    "Questions can be deleted only in DRAFT state");
        }

        Question question = quiz.getQuestions()
                .stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Question does not belong to this quiz"));

        quiz.removeQuestion(question);

        quizRepository.save(quiz);
    }

    @Override
    public void deleteQuiz(Long quizId) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        // Validate admin owns quiz
        adminService.validateAdminOwnership(quizId);

        if (quiz.getState() != QuizState.DRAFT) {
            throw new IllegalStateException(
                    "Quiz can be deleted only in DRAFT state");
        }

        quizRepository.deleteById(quizId);
    }

    @Override
    public void removeParticipant(Long quizId, Long participantId) {

        adminService.validateAdminOwnership(quizId);

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new QuizNotFoundException(quizId));

        Participant participant = participantRepository.findById(participantId)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));

        //Delete attempt if exists
        quizAttemptRepository.deleteByQuizAndParticipant(quiz, participant);
    }


}
