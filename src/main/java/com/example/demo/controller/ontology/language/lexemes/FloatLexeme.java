package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class FloatLexeme extends TypeLexeme{
    public FloatLexeme(OntModel ontModel) {
        super(ontModel, OntologyClasses.Language.FLOAT_LEXEM,"float");
    }
}
