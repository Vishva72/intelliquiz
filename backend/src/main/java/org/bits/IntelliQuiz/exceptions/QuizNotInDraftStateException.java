package org.bits.IntelliQuiz.exceptions;

public class QuizNotInDraftStateException extends RuntimeException{
    public QuizNotInDraftStateException(String message){
        super(message);
    }
}
