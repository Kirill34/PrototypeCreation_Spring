package com.example.demo.controller.ontology.session;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Student extends AbstractOntologyObject {

    private int id;

    public Student(int id, OntModel ontModel)
    {
        super(ontModel, OntologyClasses.Session.STUDENT);
        this.id = id;
        this.setLiteralProperty(LiteralProperties.Session.HAS_ID,ontModel.createTypedLiteral(id));
    }

    public Student( OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }

    public int getId() {
        return id;
    }
}
