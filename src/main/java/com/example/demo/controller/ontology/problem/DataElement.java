package com.example.demo.controller.ontology.problem;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.domaintypes.DomainType;
import org.apache.jena.ontology.OntModel;

public class DataElement extends AbstractOntologyObject {

    public static enum Direction
    {
        INPUT_DATA,
        OUTPUT_DATA,
        CHANGED_DATA
    }

    /**
     * Имя элемента данных
     */
    private String name;

    /**
     * Описание элемента данных
     */
    private String mission;

    private DomainType domainType;

    public DataElement(OntModel ontModel, String name, String mission)
    {
        super(ontModel, OntologyClasses.Problem.DATA_ELEMENT);
        this.name=name;
        this.mission=mission;
        this.setLiteralProperty(LiteralProperties.Problem.HAS_NAME,ontModel.createTypedLiteral(name));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_MISSION, ontModel.createTypedLiteral(mission));
    }

    public DataElement(OntModel ontModel, String name, String mission, DomainType domainType)
    {
        super(ontModel, OntologyClasses.Problem.DATA_ELEMENT);
        this.name=name;
        this.mission=mission;
        this.domainType=domainType;
        this.setLiteralProperty(LiteralProperties.Problem.HAS_NAME,ontModel.createTypedLiteral(name));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_MISSION, ontModel.createTypedLiteral(mission));
        this.setObjectProperty(ObjectProperties.Problem.HAS_DOMAIN_TYPE, domainType);
    }

    public String getName() {
        return name;
    }

    public String getMission() {
        return mission;
    }

    public void setNextDataElement(DataElement dataElement)
    {
        this.setObjectProperty(ObjectProperties.Problem.HAS_NEXT_COMPONENT, dataElement);
    }

    public void setSiblingDataElement(DataElement dataElement)
    {
        this.setObjectProperty(ObjectProperties.Problem.HAS_NEXT_DATA_ELEMENT,dataElement);
    }

    public void setNextField(DataElement dataElement)
    {
        this.setObjectProperty(ObjectProperties.Problem.HAS_NEXT_FIELD, dataElement);
    }
}
