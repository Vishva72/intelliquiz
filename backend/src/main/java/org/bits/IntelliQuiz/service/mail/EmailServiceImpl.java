package org.bits.IntelliQuiz.service.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // ----------------PARTICIPANT CREDENTIALS ----------------

    @Override
    public void sendParticipantCredentials(
            String toEmail,
            String password) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Quiz Platform Login Credentials");

        message.setText(
                "You have been invited to the quiz platform.\n\n" +
                        "Login Credentials:\n" +
                        "Email: " + toEmail + "\n" +
                        "Password: " + password + "\n\n" +
                        "Please keep these credentials safe.\n" +
                        "Quiz details will be shared separately."
        );

        mailSender.send(message);
    }

    // ---------------- QUIZ INVITATION (SCHEDULED) ----------------

    @Override
    public void sendQuizInvitation(
            String toEmail,
            String quizTitle,
            LocalDateTime startTime,
            LocalDateTime endTime) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Quiz Scheduled: " + quizTitle);

        message.setText(
                "You have been invited to participate in a quiz.\n\n" +
                        "Quiz: " + quizTitle + "\n" +
                        "Start Time: " + startTime + "\n" +
                        "End Time: " + endTime + "\n\n" +
                        "Please login before the start time and be ready."
        );

        mailSender.send(message);
    }

    // ---------------- QUIZ REMINDER ----------------

    @Override
    public void sendQuizReminder(
            String toEmail,
            String quizTitle,
            LocalDateTime startTime) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Quiz Reminder: " + quizTitle);

        message.setText(
                "Reminder: Your quiz is about to start.\n\n" +
                        "Quiz: " + quizTitle + "\n" +
                        "Start Time: " + startTime + "\n\n" +
                        "Please login and be prepared."
        );

        mailSender.send(message);
    }
}


