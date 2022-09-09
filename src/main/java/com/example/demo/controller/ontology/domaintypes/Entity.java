package com.example.demo.controller.ontology.domaintypes;

import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import model.DataElement;
import org.apache.jena.ontology.OntModel;

import java.util.ArrayList;

public class Entity extends DomainType{

    private String name;

    private ArrayList<com.example.demo.controller.ontology.problem.DataElement> fields;

    public Entity(OntModel ontModel, String name, ArrayList<com.example.demo.controller.ontology.problem.DataElement> fields) {
        super(ontModel, OntologyClasses.Problem.DOMAIN_TYPE_ENTITY);
        this.name=name;
        this.setLiteralProperty(LiteralProperties.Problem.HAS_NAME,ontModel.createTypedLiteral(name));
        this.fields=fields;
        this.setObjectProperty(ObjectProperties.Problem.HAS_FIRST_FIELD,fields.get(0));
        for (int i=1; i<fields.size(); i++)
        {
            fields.get(i-1).setNextField(fields.get(i));
        }
    }
}
