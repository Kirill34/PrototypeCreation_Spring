package com.example.demo.controller.ontology.language.types;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Int extends IntegerNumber{
    public Int(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.INT);
    }
}
