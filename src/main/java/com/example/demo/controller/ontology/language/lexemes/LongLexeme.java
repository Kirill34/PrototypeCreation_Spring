package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class LongLexeme extends TypeLexeme{
    public LongLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.LONG_LEXEM,"long");
    }
}
