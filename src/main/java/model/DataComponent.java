package model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class DataComponent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String mission;

    @ManyToOne
    @JoinColumn(name = "domain_type_id")
    private DomainType domainType;

    @ManyToOne
    @JoinColumn(name = "implementation_id")
    private DataElementImplementation implementation;

    public DataElementImplementation getImplementation() {
        return implementation;
    }

    public DomainType getDomainType() {
        return domainType;
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMission() {
        return mission;
    }
}
