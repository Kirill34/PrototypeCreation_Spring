package com.example.demo.controller.ontology.session.answer;

import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class DataElementIdentifying extends Answer{
    private int leftBorder;
    private int rightBorder;
    public DataElementIdentifying(OntModel ontModel, int leftBorder, int rightBorder) {
        super(ontModel, OntologyClasses.Session.ANSWER_DATA_ELEMENT_IDENTIFYING);
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.setLiteralProperty(LiteralProperties.Problem.HAS_LEFT_BORDER, ontologyModel.createTypedLiteral(leftBorder));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_RIGHT_BORDER, ontologyModel.createTypedLiteral(rightBorder));
    }
}
