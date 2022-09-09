package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class CloseBracketLexeme extends BracketLexeme{
    public CloseBracketLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.CLOSE_BRACKET_LEXEM, ")");
    }
}
