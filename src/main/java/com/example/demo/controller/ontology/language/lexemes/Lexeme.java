package com.example.demo.controller.ontology.language.lexemes;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public abstract class Lexeme extends AbstractOntologyObject {

    private Lexeme nextLexeme = null;

    private String value;

    public Lexeme(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }

    public Lexeme(OntModel ontModel, String ontClass, String value) {
        super(ontModel, ontClass);
        this.value=value;
        this.setLiteralProperty(LiteralProperties.Language.HAS_VALUE, ontologyModel.createTypedLiteral(value));
    }

    public void setNextLexeme(Lexeme nextLexeme)
    {
        this.nextLexeme = nextLexeme;
        this.setObjectProperty(ObjectProperties.Language.HAS_NEXT_LEXEME,nextLexeme);
    }

    public boolean hasNextLexeme()
    {
        return this.nextLexeme!=null;
    }

    public Lexeme getNextLexeme()
    {
        return nextLexeme;
    }
}
