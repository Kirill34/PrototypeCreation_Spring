package com.example.demo.controller.ontology.domaintypes;

import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class FloatNumber extends Number{

    private float min;

    private float max;

    public FloatNumber(OntModel ontModel, float min, float max) {
        super(ontModel, OntologyClasses.Problem.DOMAIN_TYPE_FLOAT_NUMBER);
        this.min=min;
        this.max=max;
        this.setLiteralProperty(LiteralProperties.Problem.HAS_MIN,ontologyModel.createTypedLiteral(min));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_MAX,ontologyModel.createTypedLiteral(max));
    }
}
