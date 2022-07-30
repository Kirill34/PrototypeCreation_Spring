package com.example.demo.controller.ontology;

import org.apache.jena.ontology.OntModel;

public class DataElement extends AbstractOntologyObject{

    /**
     * Имя элемента данных
     */
    private String name;

    /**
     * Описание элемента данных
     */
    private String mission;

    public DataElement(OntModel ontModel, String name, String mission)
    {
        super(ontModel, OntologyClasses.DATA_ELEMENT);
        this.name=name;
        this.mission=mission;
    }

    public String getName() {
        return name;
    }

    public String getMission() {
        return mission;
    }
}
