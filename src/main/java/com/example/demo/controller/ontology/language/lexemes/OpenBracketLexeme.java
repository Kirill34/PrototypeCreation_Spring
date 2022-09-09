package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class OpenBracketLexeme extends BracketLexeme{
    public OpenBracketLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.OPEN_BRACKET_LEXEM, "(");
    }
}
