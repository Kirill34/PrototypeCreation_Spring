package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class CommaLexeme extends Lexeme{
    public CommaLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.COMMA_LEXEM, ",");
    }
}
