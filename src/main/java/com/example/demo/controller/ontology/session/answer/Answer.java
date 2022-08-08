package com.example.demo.controller.ontology.session.answer;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.ObjectProperties;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import java.util.HashSet;

public class Answer extends AbstractOntologyObject {

    public Answer(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
    }

    public void setNextAnswer(Answer nextAnswer)
    {
        this.setObjectProperty(ObjectProperties.Session.HAS_NEXT_ANSWER, nextAnswer);
    }

    private Resource getErrorResource()
    {
        return this.ontResource.getPropertyResourceValue(ontologyModel.getObjectProperty(ObjectProperties.Session.HAS_ERROR));
    }

    public String getErrorClass()
    {
        HashSet<RDFNode> classes = new HashSet<>();
        //RDFNode errorObject = ontResource.getProperty(ontologyModel.getObjectProperty(ObjectProperties.Session.HAS_ERROR)).getObject();

        Resource error = getErrorResource();
        StmtIterator stmtit = error.listProperties();

        while (stmtit.hasNext()) {
            Statement stmt = stmtit.next();
            if (stmt.getPredicate().getURI().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
                classes.add(stmt.getObject());
                if (!stmt.getObject().asResource().getURI().equals("http://www.w3.org/2000/01/rdf-schema#Resource") && !stmt.getObject().asResource().getURI().equals("http://www.w3.org/2002/07/owl#Thing"))
                {
                    return stmt.getObject().asResource().getURI().toString();
                }

            }
        }
        return "";
    }

    public com.example.demo.controller.ontology.error.Error getError()
    {
        return new com.example.demo.controller.ontology.error.Error(ontologyModel, this.getErrorClass(), this.getErrorResource());
    }
}
