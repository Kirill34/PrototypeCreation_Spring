package model;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Entity
@Table

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nullable
    private String firstName;

    @Nullable
    private String lastName;

    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    public Student(String firstName, String lastName, Optional<Problem> problem) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.problem = problem.get();
    }

    public Problem getProblem() {
        return problem;
    }

    public Student() {

    }

    public Long getId() {
        return id;
    }

    public Student(String firstName, String lastName, Problem problem)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.problem = problem;
        this.dateTime=LocalDateTime.now();
    }


}
