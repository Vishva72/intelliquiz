package org.bits.IntelliQuiz.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "quiz_attempts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"participant_id", "quiz_id"})
        })
public class QuizAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    @Column(nullable = false)
    private boolean submitted = false;

    @Column(nullable = false)
    private int score = 0;

    @ElementCollection
    @CollectionTable(
            name = "attempt_answers",
            joinColumns = @JoinColumn(name = "attempt_id")
    )
    @MapKeyColumn(name = "question_id")
    @Column(name = "selected_option")
    private Map<Long, Integer> answers = new HashMap<>();

    protected QuizAttempt() {
        // Required by JPA
    }

    public QuizAttempt(Participant participant, Quiz quiz) {
        this.participant = participant;
        this.quiz = quiz;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Map<Long, Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<Long, Integer> answers) {
        this.answers = answers;
    }



}
