package com.example.demo.controller.ontology.problem;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DataElementImplementation extends AbstractOntologyObject {

    private List<DataElement> dataComponents = new ArrayList<>();

    public DataElementImplementation(OntModel ontModel, List<DataElement> dataComponents) {
        super(ontModel, OntologyClasses.Problem.DATA_ELEMENT_PRESENTATION);
        this.dataComponents=dataComponents;
        this.setObjectProperty(ObjectProperties.Problem.HAS_FIRST_COMPONENT, dataComponents.get(0));
        for (int i=1; i<dataComponents.size(); i++)
        {
            dataComponents.get(i-1).setNextDataElement(dataComponents.get(i));
        }
    }

    public List<DataElement> getDataComponents()
    {
        return dataComponents;
    }
}
