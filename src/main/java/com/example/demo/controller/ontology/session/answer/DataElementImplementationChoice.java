package com.example.demo.controller.ontology.session.answer;

import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.DataElementImplementation;
import com.github.jsonldjava.utils.Obj;
import org.apache.jena.ontology.OntModel;

public class DataElementImplementationChoice extends Answer{

    private DataElement dataElement;

    private com.example.demo.controller.ontology.problem.DataElementImplementation implementation;

    public DataElementImplementationChoice(OntModel ontModel, DataElement dataElement, DataElementImplementation implementation) {
        super(ontModel, OntologyClasses.Session.ANSWER_DATA_ELEMENT_IMPLEMENTATION);
        this.setObjectProperty(ObjectProperties.Session.OF_DATA_ELEMENT,dataElement);
        this.setObjectProperty(ObjectProperties.Session.HAS_CHOSEN_IMPLEMENTATION, implementation);
        this.dataElement=dataElement;
        this.implementation=implementation;
    }

    public DataElement getDataElement() {
        return dataElement;
    }

    public DataElementImplementation getImplementation() {
        return implementation;
    }
}
