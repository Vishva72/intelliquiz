package org.bits.IntelliQuiz.repository;

import org.bits.IntelliQuiz.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {

}
