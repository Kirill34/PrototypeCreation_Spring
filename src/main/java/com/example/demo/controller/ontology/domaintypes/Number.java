package com.example.demo.controller.ontology.domaintypes;

import org.apache.jena.ontology.OntModel;

public abstract class Number extends Scalar {
    public Number(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }
}
