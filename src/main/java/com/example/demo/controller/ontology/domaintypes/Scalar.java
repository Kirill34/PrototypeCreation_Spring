package com.example.demo.controller.ontology.domaintypes;

import org.apache.jena.ontology.OntModel;

public abstract class Scalar extends DomainType{
    public Scalar(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }
}
