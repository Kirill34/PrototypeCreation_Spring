package com.example.demo.controller.ontology.domaintypes;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import org.apache.jena.ontology.OntModel;

public abstract class DomainType extends AbstractOntologyObject {

    private String mission;

    public DomainType(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }

    public String getMission() {
        return mission;
    }
}
