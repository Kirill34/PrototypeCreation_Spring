package com.example.demo.controller.ontology.language.types;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Long extends IntegerNumber{
    public Long(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.LONG);
    }
}
