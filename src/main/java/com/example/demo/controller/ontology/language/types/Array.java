package com.example.demo.controller.ontology.language.types;

import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class Array extends Type{

    private Type type;

    private int size;

    public Array(OntModel ontModel, Type type, int size) {
        super(ontModel, OntologyClasses.Language.ARRAY);
        this.size=size;
        this.type=type;
        this.setObjectProperty(ObjectProperties.Language.ARRAY_OF, type);
        this.setLiteralProperty(LiteralProperties.Language.HAS_ARRAY_LENGTH, ontModel.createTypedLiteral(size));
    }
}
