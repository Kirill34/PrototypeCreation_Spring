package model;

import javax.persistence.*;



@Entity
@Table
public class DataElement {
    public enum DataElementDirection
    {
        INPUT_DATA,
        OUTPUT_DATA,
        CHANGED_DATA
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    private DataElementDirection direction;

    private String name;

    private String mission;

    @ManyToOne
    @JoinColumn(name = "domain_type_id")
    private DomainType domainType;

    public DomainType getDomainType() {
        return domainType;
    }


    public Problem getProblem() {
        return problem;
    }

    public Long getId() {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getMission()
    {
        return mission;
    }

    public DataElementDirection getDirection() {
        return direction;
    }

    public DataElement(Problem problem, String name, String mission, DomainType domainType, DataElementDirection direction)
    {
        this.problem = problem;
        this.name = name;
        this.mission = mission;
        this.domainType = domainType;
        this.direction = direction;
    }
}
