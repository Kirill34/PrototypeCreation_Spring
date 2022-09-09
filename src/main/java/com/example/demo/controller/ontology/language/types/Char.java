package com.example.demo.controller.ontology.language.types;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Char extends IntegerNumber{
    public Char(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.CHAR);
    }
}
