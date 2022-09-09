package com.example.demo.controller.ontology.language.types;

import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Pointer extends Type{

    private Type pointedType;

    public Pointer(OntModel ontModel, Type pointedType) {
        super(ontModel, OntologyClasses.Language.POINTER);
        this.pointedType=pointedType;
        this.setObjectProperty(ObjectProperties.Language.POINTS_TO, pointedType);
    }
}
