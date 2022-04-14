package model;

import org.springframework.lang.Nullable;

import javax.persistence.*;



@Entity
@Table
public class DomainType {

    public enum HighlyLevelTypes {
        INTEGER_NUMBER,
        REAL_NUMBER,
        LOGICAL,
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

    @Nullable
    private long longMinValue=0;

    @Nullable
    private long longMaxValue=0;

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

    public long getLongMinValue()
    {
        return longMinValue;
    }

    public long getLongMaxValue() {
        return longMaxValue;
    }

    public static DomainType createLogicalDomainType(String name, String mission)
    {
        DomainType domainType = new DomainType();
        domainType.name=name;
        domainType.mission=mission;
        domainType.type=HighlyLevelTypes.LOGICAL;
        domainType.minValue=0;
        domainType.maxValue=1;
        return domainType;
    }


    public static DomainType createRealDomainType(String name, String mission, float minValue, float maxValue)
    {
        DomainType domainType = new DomainType();
        domainType.name=name;
        domainType.mission=mission;
        domainType.maxValue=maxValue;
        domainType.minValue=minValue;
        domainType.type=HighlyLevelTypes.REAL_NUMBER;
        return domainType;
    }

    public static DomainType createIntegerDomainType(String name, String mission, long minValue, long maxValue) {
        DomainType domainType = new DomainType();
        domainType.name = name;
        domainType.mission = mission;
        domainType.longMinValue = minValue;
        domainType.longMaxValue=maxValue;
        domainType.type = HighlyLevelTypes.INTEGER_NUMBER;
        return domainType;
    }

    public static DomainType createEntityDomainType(String name, String mission)
    {
        DomainType domainType = new DomainType();
        domainType.name=name;
        domainType.mission=mission;
        domainType.type=HighlyLevelTypes.ENTITY;
        domainType.minValue=0;
        domainType.maxValue=0;
        return domainType;
    }

    public DomainType()
    {

    }
}
