package model;

import org.springframework.lang.Nullable;

import javax.persistence.*;



@Entity
@Table
public class DomainType {

    public enum HighlyLevelTypes {
        INTEGER_NUMBER,
        REAL_NUMBER,
        CHAR,
        LIST,
        SET,
        SEQUENCE,
        ENTITY,
        ANY
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private String mission;

    private HighlyLevelTypes type;

    @Nullable
    private float minValue;

    @Nullable
    private float maxValue;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMission() {
        return mission;
    }

    public HighlyLevelTypes getType() {
        return type;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public static DomainType createIntegerDomainType(String name, String mission, int minValue, int maxValue) {
        DomainType domainType = new DomainType();
        domainType.name = name;
        domainType.mission = mission;
        domainType.minValue = minValue;
        domainType.maxValue = maxValue;
        domainType.type = HighlyLevelTypes.INTEGER_NUMBER;
        return domainType;
    }
}
