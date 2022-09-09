package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class IntLexeme extends TypeLexeme {
    public IntLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.INT_LEXEM,"int");
    }
}
