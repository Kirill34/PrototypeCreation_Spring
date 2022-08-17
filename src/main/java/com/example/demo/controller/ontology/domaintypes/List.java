package com.example.demo.controller.ontology.domaintypes;

import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import org.apache.jena.ontology.OntModel;

public class List extends Collection{

    private int length;

    private boolean hasFixedLength;

    public static List createFixedLengthList(OntModel ontModel, int length)
    {
        List list = new List(ontModel);
        list.length=length;
        list.setLiteralProperty(LiteralProperties.Problem.HAS_FIXED_LENGTH, ontModel.createTypedLiteral(length));
        list.hasFixedLength=true;
        return list;
    }

    public static List createNotFixedLengthList(OntModel ontModel)
    {
        List list = new List(ontModel);
        list.hasFixedLength=false;
        return list;
    }

    private List(OntModel ontModel) {
        super(ontModel, OntologyClasses.Problem.DOMAIN_TYPE_LIST);
    }
}
