package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class VoidLexeme extends TypeLexeme{
    public VoidLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.VOID_LEXEM,"void");
    }
}
