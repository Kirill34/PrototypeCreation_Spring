package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class FunctionNameLexeme extends IDLexeme{
    public FunctionNameLexeme(OntModel ontModel, String value) {
        super(ontModel, OntologyClasses.Language.FUNCTION_NAME_LEXEM, value);
    }
}
