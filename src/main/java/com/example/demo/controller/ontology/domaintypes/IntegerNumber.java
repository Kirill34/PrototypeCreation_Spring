package com.example.demo.controller.ontology.domaintypes;

import org.apache.jena.ontology.OntModel;

public class IntegerNumber extends Number{

    private int min;

    private int max;

    public IntegerNumber(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }
}
