package com.example.demo.controller.ontology.domaintypes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Char extends Scalar{
    public Char(OntModel ontModel) {
        super(ontModel, OntologyClasses.Problem.DOMAIN_TYPE_CHAR);
    }
}
