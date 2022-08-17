package com.example.demo.controller.ontology.session;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.DataElementImplementation;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Resource;

public class SessionComponent extends AbstractOntologyObject {

    private DataElement dataElement;

    private DataElementImplementation implementation;

    private DataElement dataComponent;


    public SessionComponent(OntModel ontModel, Resource resource, DataElement element, DataElementImplementation implementation, DataElement dataComponent) {
        super(ontModel, OntologyClasses.Session.SESSIONS_COMPONENT, resource);
        this.dataElement=element;
        this.implementation=implementation;
        this.dataComponent=dataComponent;
    }
}
