package com.example.demo.controller.ontology.language.types;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class RealNumber extends Scalar{
    public RealNumber(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.REAL_NUMBER);
    }
}
