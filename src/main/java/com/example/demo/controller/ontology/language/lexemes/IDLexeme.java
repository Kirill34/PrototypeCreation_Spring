package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.LiteralProperties;
import org.apache.jena.ontology.OntModel;

public abstract class IDLexeme extends Lexeme{

    public IDLexeme(OntModel ontModel, String ontClass, String value) {
        super(ontModel, ontClass, value);
    }
}
