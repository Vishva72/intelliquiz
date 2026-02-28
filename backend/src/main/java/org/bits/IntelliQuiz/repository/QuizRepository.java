package org.bits.IntelliQuiz.repository;

import org.bits.IntelliQuiz.entities.Admin;
import org.bits.IntelliQuiz.entities.Quiz;
import org.bits.IntelliQuiz.enums.QuizState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    List<Quiz> findByAdmin(Admin admin);

    Optional<Quiz> findByIdAndAdmin(Long quizId, Admin admin);

    List<Quiz> findByStateAndStartTimeBefore(QuizState quizState, LocalDateTime now);

    List<Quiz> findByStateAndEndTimeBefore(QuizState quizState, LocalDateTime now);

    List<Quiz> findByStateAndStartTimeBetween(
            QuizState state,
            LocalDateTime from,
            LocalDateTime to);

}
