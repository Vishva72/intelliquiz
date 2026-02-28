package org.bits.IntelliQuiz.scheduler;

import org.bits.IntelliQuiz.service.QuizService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class QuizScheduler {

    private final QuizService quizService;

    public QuizScheduler(QuizService quizService) {
        this.quizService = quizService;
    }

    // 🔁 Runs every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void runScheduledJobs() {

        // 1️⃣ Handle quiz state transitions
        // (SCHEDULED → LIVE, LIVE → CLOSED + auto-submit)
        quizService.handleQuizStateTransitions();

        // 2️⃣ Send quiz reminder emails (5 minutes before start)
        quizService.sendQuizReminders();
    }
}
