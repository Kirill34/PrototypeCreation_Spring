package model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table

public class AnswerElementDirection {

    public enum ElementDirection {
        INPUT_DATA,
        OUTPUT_DATA,
        CHANGED_DATA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "element_id")
    private DataElement element;

    private ElementDirection direction;


    public AnswerElementDirection()
    {

    }

    public AnswerElementDirection(Student student, DataElement element, ElementDirection direction)
    {
        this.student = student;
        this.element = element;
        this.direction = direction;
        this.dateTime = LocalDateTime.now();
    }

    public DataElement getElement() {
        return element;
    }

    public Long getId() {
        return id;
    }

}
