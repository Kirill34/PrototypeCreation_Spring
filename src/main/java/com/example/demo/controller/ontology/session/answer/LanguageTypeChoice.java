package com.example.demo.controller.ontology.session.answer;

import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.language.Type;
import com.example.demo.controller.ontology.session.SessionComponent;
import org.apache.jena.ontology.OntModel;

public class LanguageTypeChoice extends Answer{

    private SessionComponent sessionComponent;

    private Type chosenType;

    public LanguageTypeChoice(OntModel ontModel, SessionComponent sessionComponent, Type chosenType) {
        super(ontModel, OntologyClasses.Session.ANSWER_LANGUAGE_TYPE_CHOICE);
        this.chosenType=chosenType;
        this.sessionComponent=sessionComponent;
        this.setObjectProperty(ObjectProperties.Session.OF_SESSION_COMPONENT,sessionComponent);
        this.setObjectProperty(ObjectProperties.Session.HAS_CHOSEN_TYPE, chosenType);
    }

}
