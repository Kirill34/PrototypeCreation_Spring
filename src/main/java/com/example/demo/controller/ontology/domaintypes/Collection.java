package com.example.demo.controller.ontology.domaintypes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Collection extends DomainType{
    public Collection(OntModel ontModel) {
        super(ontModel, OntologyClasses.Problem.DOMAIN_TYPE_COLLECTION);
    }

    public Collection(OntModel ontModel, String ontClass)
    {
        super(ontModel,ontClass);
    }
}
