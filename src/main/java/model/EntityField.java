package model;

import javax.persistence.*;



@Entity
@Table
public class EntityField {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String mission;

    @ManyToOne
    @JoinColumn(name = "own_domain_type_id")
    private DomainType ownDomainType;

    @ManyToOne
    @JoinColumn(name = "domain_type_id")
    private DomainType domainType;

    public DomainType getOwnDomainType() {
        return ownDomainType;
    }


    public DomainType getDomainType() {
        return domainType;
    }

    public String getName() {
        return name;
    }

    public String getMission() {
        return mission;
    }

    public Long getId() {
        return id;
    }

    public EntityField()
    {

    }

    public EntityField(String name, String mission, DomainType ofDomainType, DomainType ownDomainType)
    {
        this.name=name;
        this.mission=mission;
        this.domainType=ofDomainType;
        this.ownDomainType=ownDomainType;
    }

}
