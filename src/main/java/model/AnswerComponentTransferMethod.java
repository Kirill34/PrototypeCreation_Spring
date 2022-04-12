package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table
public class AnswerComponentTransferMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    Student student;

    String elementName;

    String componentName;

    boolean isCorrect;

    String errorMessage;

    String transferMethod;

    private LocalDateTime dateTime;


    public Student getStudent() {
        return student;
    }

    public AnswerComponentTransferMethod()
    {

    }

    public AnswerComponentTransferMethod(Student student, String elementName, String componentName, boolean isCorrect, String errorMessage, String transferMethod)
    {
        this.student = student;
        this.elementName = elementName;
        this.componentName = componentName;
        this.isCorrect = isCorrect;
        this.errorMessage = errorMessage;
        this.transferMethod=transferMethod;
        this.dateTime=LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
