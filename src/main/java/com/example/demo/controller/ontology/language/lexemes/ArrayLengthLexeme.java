package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class ArrayLengthLexeme extends TypeLexeme{
    private int length;
    public ArrayLengthLexeme(OntModel ontModel, int length) {
        super(ontModel, OntologyClasses.Language.ARRAY_LENGTH_LEXEM, String.valueOf(length));
        this.length=length;
    }
}
