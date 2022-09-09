package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class ParameterNameLexeme extends IDLexeme{
    public ParameterNameLexeme(OntModel ontModel, String value) {
        super(ontModel, OntologyClasses.Language.PARAMETER_NAME_LEXEM, value);
    }
}
