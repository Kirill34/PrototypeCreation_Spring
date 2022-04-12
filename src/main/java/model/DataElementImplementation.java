package model;

import javax.persistence.*;
import java.util.List;


@Entity
@Table
public class DataElementImplementation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String mission;

    @ManyToOne
    @JoinColumn(name = "data_element_id")
    private DataElement dataElement;

    public DataElement getDataElement() {
        return dataElement;
    }


    public Long getId() {
        return id;
    }

    public String getMission() {
        return mission;
    }

    public String getName() {
        return name;
    }

    public DataElementImplementation()
    {

    }

    public DataElementImplementation(String name, String mission, DataElement dataElement)
    {
        this.name = name;
        this.mission=mission;
        this.dataElement=dataElement;
    }
}
