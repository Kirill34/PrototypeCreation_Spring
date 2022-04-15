package model;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Table
public class AnswerElementPresentation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public AnswerElementPresentation() {

    }

    public Long getId() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "element_id")
    private DataElement element;

    @ManyToOne
    @JoinColumn(name = "implementation_id")
    private  DataElementImplementation implementation;

    private boolean isCorrect;

    private String message;

    public Student getStudent() {
        return student;
    }

    public DataElementImplementation getImplementation() {
        return implementation;
    }

    public DataElement getElement() {
        return element;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private LocalDateTime dateTime;

    public AnswerElementPresentation(Student student, DataElement dataElement, DataElementImplementation implementation, boolean isCorrect, String message)
    {
        this.student = student;
        this.element = dataElement;
        this.implementation = implementation;
        this.isCorrect = isCorrect;
        this.message = message;
        this.dateTime=LocalDateTime.now();
    }
}
