package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class CharLexeme extends TypeLexeme{
    public CharLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.CHAR_LEXEM,"char");
    }
}
