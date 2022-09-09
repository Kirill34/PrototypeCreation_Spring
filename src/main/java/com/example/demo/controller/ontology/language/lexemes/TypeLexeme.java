package com.example.demo.controller.ontology.language.lexemes;

import org.apache.jena.ontology.OntModel;

public abstract class TypeLexeme extends Lexeme{
    public TypeLexeme(OntModel ontModel, String ontClass, String value) {
        super(ontModel,ontClass,value);
    }
}
