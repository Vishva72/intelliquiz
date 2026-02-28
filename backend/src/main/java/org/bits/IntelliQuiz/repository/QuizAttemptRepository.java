package org.bits.IntelliQuiz.repository;

import org.bits.IntelliQuiz.entities.Participant;
import org.bits.IntelliQuiz.entities.Quiz;
import org.bits.IntelliQuiz.entities.QuizAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizAttemptRepository extends JpaRepository<QuizAttempt,Long> {
    boolean existsByQuizAndParticipant(Quiz quiz, Participant participant);
    boolean existsByQuiz(Quiz quiz);

    Optional<QuizAttempt>findByQuizAndParticipant(Quiz quiz, Participant participant);

    List<QuizAttempt> findByParticipant(Participant participant);

    List<QuizAttempt> findByQuizAndSubmittedFalse(Quiz quiz);

    List<QuizAttempt> findByQuiz(Quiz quiz);

    void deleteByQuizAndParticipant(Quiz quiz, Participant participant);
}
