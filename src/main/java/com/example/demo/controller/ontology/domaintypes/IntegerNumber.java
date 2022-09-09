package com.example.demo.controller.ontology.domaintypes;

import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class IntegerNumber extends Number{

    private long min;

    private long max;

    public IntegerNumber(OntModel ontModel, long min, long max) {
        super(ontModel, OntologyClasses.Problem.DOMAIN_TYPE_INTEGER_NUMBER);
        this.min=min;
        this.max=max;
        this.setLiteralProperty(LiteralProperties.Problem.HAS_MIN, ontModel.createTypedLiteral(min));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_MAX, ontModel.createTypedLiteral(max));
    }
}
