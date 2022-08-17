package com.example.demo.controller.ontology.language;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import org.apache.jena.ontology.OntModel;

public class Type extends AbstractOntologyObject {
    public Type(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }
}
