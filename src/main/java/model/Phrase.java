package model;

import javax.persistence.*;

@Entity
@Table
public class Phrase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "data_element_id")
    private DataElement dataElement;
    private int leftBorder;
    private int rightBorder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLeftBorder() {
        return leftBorder;
    }

    public int getRightBorder() {
        return rightBorder;
    }

    public DataElement getDataElement() {
        return dataElement;
    }

    public Phrase(DataElement dataElement, int leftBorder, int rightBorder)
    {
        this.dataElement = dataElement;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
    }
}
