package org.bits.IntelliQuiz.exceptions;

public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(Long participantId) {
        super("participant not found with Id"+participantId);
    }
}
