package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table
public class AnswerElementBorders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDateTime dateTime;

    private int leftBorder;

    private int rightBorder;

    private boolean isCorrect;

    private String errorMessage;

    public AnswerElementBorders(Student student, int leftBorder, int rightBorder, boolean isCorrect, String errorMessage)
    {
        this.student = student;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.isCorrect = isCorrect;
        this.errorMessage = errorMessage;
        this.dateTime = LocalDateTime.now();
    }

    public AnswerElementBorders() {

    }

    public Student getStudent() {
        return student;
    }


    public Long getId() {
        return id;
    }


}
