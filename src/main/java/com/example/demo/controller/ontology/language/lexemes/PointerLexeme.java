package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class PointerLexeme extends TypeLexeme{
    public PointerLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.POINTER_LEXEM,"*");
    }
}
