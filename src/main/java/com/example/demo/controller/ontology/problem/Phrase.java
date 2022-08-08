package com.example.demo.controller.ontology.problem;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

import javax.xml.crypto.Data;

public class Phrase extends AbstractOntologyObject {

    private int leftBorder;

    private int rightBorder;

    private DataElement dataElement = null;

    public Phrase(OntModel ontModel, int leftBorder, int rightBorder, DataElement dataElement) {
        super(ontModel, OntologyClasses.Problem.PHRASE);
        this.leftBorder=leftBorder;
        this.rightBorder=rightBorder;
        this.dataElement=dataElement;
        this.setObjectProperty(ObjectProperties.Problem.DESCRIBES, dataElement);
        this.setLiteralProperty(LiteralProperties.Problem.HAS_LEFT_BORDER, ontologyModel.createTypedLiteral(leftBorder));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_RIGHT_BORDER, ontologyModel.createTypedLiteral(rightBorder));
    }

    public int getRightBorder() {
        return rightBorder;
    }

    public int getLeftBorder() {
        return leftBorder;
    }
}
