package org.bits.IntelliQuiz.service.mail;

import java.time.LocalDateTime;

public interface EmailService {

    // Sent once when participant account is created
    void sendParticipantCredentials(
            String toEmail,
            String password
    );

    // Sent when quiz is scheduled (timing exists)
    void sendQuizInvitation(
            String toEmail,
            String quizTitle,
            LocalDateTime startTime,
            LocalDateTime endTime
    );

    // Sent shortly before quiz starts
    void sendQuizReminder(
            String toEmail,
            String quizTitle,
            LocalDateTime startTime
    );
}


