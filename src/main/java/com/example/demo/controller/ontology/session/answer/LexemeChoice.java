package com.example.demo.controller.ontology.session.answer;

import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.language.lexemes.Lexeme;
import org.apache.jena.ontology.OntModel;

public class LexemeChoice extends Answer{

    private Lexeme lexeme;

    public LexemeChoice(OntModel ontModel, Lexeme lexeme) {
        super(ontModel, OntologyClasses.Session.ANSWER_LEXEME_CHOICE);
        this.lexeme=lexeme;
        this.setObjectProperty(ObjectProperties.Session.HAS_CHOSEN_LEXEME, lexeme);
    }
}
