package com.example.demo.controller.ontology.language.types;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Float extends RealNumber{
    public Float(OntModel ontModel) {
        super(ontModel);
        this.ontClass = OntologyClasses.Language.FLOAT;
    }
}
