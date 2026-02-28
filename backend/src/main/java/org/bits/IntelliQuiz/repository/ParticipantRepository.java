package org.bits.IntelliQuiz.repository;

import org.bits.IntelliQuiz.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant,Long> {

    Optional<Participant> findByEmail(String email);

}
