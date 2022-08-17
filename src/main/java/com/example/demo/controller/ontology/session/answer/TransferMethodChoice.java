package com.example.demo.controller.ontology.session.answer;

import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.session.SessionComponent;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.vocabulary.RDF;

public class TransferMethodChoice extends Answer{

    public static enum TransferMethod {
        INPUT_PARAMETER,
        OUTPUT_PARAMETER,
        CHANGED_PARAMETER,
        RETURN_VALUE
    }

    private SessionComponent sessionComponent;

    private TransferMethod transferMethod;

    public TransferMethodChoice(OntModel ontModel, SessionComponent sessionComponent, TransferMethod transferMethod) {
        super(ontModel, OntologyClasses.Session.ANSWER_TRANSFER_METHOD_CHOICE);
        this.sessionComponent=sessionComponent;
        this.transferMethod=transferMethod;
        switch (transferMethod)
        {
            case RETURN_VALUE:
                this.ontResource.addProperty(ontModel.getObjectProperty(ObjectProperties.Session.HAS_CHOSEN_TRANSFER_METHOD), ontModel.getOntClass(OntologyClasses.Session.TRANSFER_METHOD_RETURN_VALUE));
                break;
            case INPUT_PARAMETER:
                this.ontResource.addProperty(ontModel.getObjectProperty(ObjectProperties.Session.HAS_CHOSEN_TRANSFER_METHOD), ontModel.getOntClass(OntologyClasses.Session.TRANSFER_METHOD_INPUT_PARAMETER));
                break;
            case OUTPUT_PARAMETER:
                this.ontResource.addProperty(ontModel.getObjectProperty(ObjectProperties.Session.HAS_CHOSEN_TRANSFER_METHOD), ontModel.getOntClass(OntologyClasses.Session.TRANSFER_METHOD_OUTPUT_PARAMETER));
                break;
            case CHANGED_PARAMETER:
                this.ontResource.addProperty(ontModel.getObjectProperty(ObjectProperties.Session.HAS_CHOSEN_TRANSFER_METHOD), ontModel.getOntClass(OntologyClasses.Session.TRANSFER_METHOD_CHANGED_PARAMETER));
                break;
        }
    }
}
