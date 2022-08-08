package com.example.demo.controller.ontology.session.answer;

import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.problem.DataElement;
import org.apache.jena.ontology.OntModel;

public class DataElementDirectionChoice extends Answer{

    private DataElement dataElement;

    private DataElement.Direction direction;
    public DataElementDirectionChoice(OntModel ontModel, DataElement dataElement, DataElement.Direction direction) {
        super(ontModel, OntologyClasses.Session.ANSWER_DATA_ELEMENT_DIRECTION_CHOICE);
        this.direction = direction;
        this.setLiteralProperty(LiteralProperties.Session.HAS_DIRECTION, ontModel.createTypedLiteral((direction.ordinal())));
        this.dataElement = dataElement;
        this.setObjectProperty(ObjectProperties.Session.OF_DATA_ELEMENT, dataElement);
    }
}
