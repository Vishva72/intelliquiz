package org.bits.IntelliQuiz.exceptions;

public class QuizNotFoundException extends RuntimeException{
    public QuizNotFoundException(Long quizId){
        super("Quiz Not Found with Id"+quizId);
    }
}
