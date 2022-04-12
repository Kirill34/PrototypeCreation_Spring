package model;

import org.hibernate.persister.internal.PersisterFactoryInitiator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table
public class AnswerPrototypeCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String code;

    private boolean isCorrect;

    private boolean isCompleted;

    private String errorMessage;

    private LocalDateTime dateTime;


    public Student getStudent() {
        return student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AnswerPrototypeCode()
    {

    }

    public AnswerPrototypeCode(Student student, String code, boolean isCorrect, String errorMessage, boolean isCompleted)
    {
        this.student=student;
        this.code=code;
        this.isCorrect=isCorrect;
        this.errorMessage=errorMessage;
        this.isCompleted = isCompleted;
        this.dateTime=LocalDateTime.now();
    }
}
