package model;

import org.hibernate.persister.internal.PersisterFactoryInitiator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table
public class AnswerDatatype {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private String datatype;

    private String paramName;

    private boolean isCorrect;

    private String errorMessage;

    LocalDateTime dateTime;

    public AnswerDatatype()
    {

    }

    public AnswerDatatype(Student student, String datatype, String paramName, boolean isCorrect, String errorMessage)
    {
        this.student=student;
        this.datatype=datatype;
        this.paramName=paramName;
        this.isCorrect=isCorrect;
        this.errorMessage=errorMessage;
        this.dateTime=LocalDateTime.now();
    }

    public Student getStudent() {
        return student;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
