package com.example.demo.controller;

//import com.sun.org.apache.bcel.internal.generic.Select;
//import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
//import com.sun.xml.internal.ws.api.ha.StickyFeature;
//import jdk.nashorn.internal.objects.annotations.Where;
//import com.github.andrewoma.dexx.collection.Pair;
import model.DataComponent;
import model.DataElement;
import model.DomainType;
import model.EntityField;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.tdb.store.Hash;
import org.apache.jena.vocabulary.RDF;
import org.springframework.data.util.Pair;

//import javax.jws.WebParam;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.apache.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;

public class ProblemClass {

    protected Model model = null;
    protected OntModel inf = null;
    Reasoner reasoner = null;
    Reasoner [] reasoners = new Reasoner[6];
    InfModel infModel = null;

    protected static String BASE_URL = "http://www.semanticweb.org/problem-ontology";
    protected static String PROBLEM_ONTOLOGY_FILE = "ProblemOntology.owl";
    protected static String SESSION_ONTOLOGY_FILE = "SessionOntology.owl";
    protected static String LANGUAGE_ONTOLOGY_FILE = "LanguageOntology.owl";
    protected static String ERROR_ONTOLOGY_FILE = "ErrorOntology.owl";

    protected static String DATA_DIRECTION_RULES = "rules/data_direction.rules";
    protected static String ELEMENT_BORDERS_RULES = "rules/element_borders.rules";
    protected static String DATA_PRESENTATION_RULES = "rules/data_presentation.rules";
    protected static String PARAMETERS_RETURNS_RULES = "rules/parameters_returns.rules";
    protected static String TYPES_RULES = "rules/types.rules";
    protected static String PROTOTYPE_RULES = "rules/prototype.rules";

    protected static String DATA_TRANSFER_METHOD_RETURN = "return";
    protected static String DATA_TRANSFER_METHOD_READ_ONLY = "read-only";
    protected static String DATA_TRANSFER_METHOD_WRITE_ONLY = "write-only";
    protected static String DATA_TRANSFER_METHOD_READ_WRITE = "read-write";

    protected Individual daysCount = null;
    protected Individual firstDate_DataElement = null;
    protected Individual secondDate_DataElement = null;

    protected Individual problem = null;

    protected Individual date_Entity = null;


    public ProblemClass()
    {
        createModel(DATA_DIRECTION_RULES);
        //addProblem();
        calcModel();
    }

    public String getAllModel()
    {
        return inf.toString();
    }

    private void addProblem()
    {
        Individual problem = inf.createIndividual(inf.createResource());
        problem.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Problem"));
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasFullText"), "вычисляет сумму доходов за январь и доходов за февраль");
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasID"), inf.createTypedLiteral(2));

        //Domain type: money count
        Individual domainTypeMoney = inf.createIndividual(inf.createResource());
        domainTypeMoney.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#IntegerNumber"));
        domainTypeMoney.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"Количество денег");
        domainTypeMoney.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#minValue"), inf.createTypedLiteral((long) 0));
        domainTypeMoney.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#maxValue"), inf.createTypedLiteral((long) 100000));

        //Domain type: array
        Individual domainTypeArray = inf.createIndividual(inf.createResource());
        domainTypeArray.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#List"));
        domainTypeArray.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"Количество денег");

        Individual data_element_sum = inf.createIndividual(inf.createResource());
        data_element_sum.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
        data_element_sum.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"sum");
        data_element_sum.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),"Сумма доходов за январь и февраль");
        data_element_sum.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"),domainTypeMoney);
        problem.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasOutputData"), data_element_sum);

        Individual phraseSum = inf.createIndividual(inf.createResource());
        phraseSum.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Phrase"));
        phraseSum.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#describe"), data_element_sum);
        phraseSum.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasLeftBorder"), inf.createTypedLiteral(2));
        phraseSum.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasRightBorder"), inf.createTypedLiteral(2));


        Individual data_element_jan = inf.createIndividual(inf.createResource());
        data_element_jan.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
        data_element_jan.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"january");
        data_element_jan.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),"Доходы за январь");
        data_element_jan.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"),domainTypeMoney);
        problem.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasInputData"), data_element_jan);

        Individual phraseJan = inf.createIndividual(inf.createResource());
        phraseJan.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Phrase"));
        phraseJan.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#describe"), data_element_jan);
        phraseJan.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasLeftBorder"), inf.createTypedLiteral(3));
        phraseJan.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasRightBorder"), inf.createTypedLiteral(5));

        Individual data_element_feb = inf.createIndividual(inf.createResource());
        data_element_feb.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
        data_element_feb.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"february");
        data_element_feb.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),"Доходы за февраль");
        data_element_feb.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"),domainTypeMoney);
        problem.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasInputData"), data_element_feb);

        Individual phraseFeb = inf.createIndividual(inf.createResource());
        phraseFeb.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Phrase"));
        phraseFeb.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#describe"), data_element_feb);
        phraseFeb.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasLeftBorder"), inf.createTypedLiteral(7));
        phraseFeb.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasRightBorder"), inf.createTypedLiteral(9));

        //Presentation number
        Individual presentation = inf.createIndividual(inf.createResource());
        presentation.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElementPresentation"));
        presentation.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"number");
        presentation.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),"Целое число [0; 10 000]");


        Individual moneyCount = inf.createIndividual(inf.createResource());
        moneyCount.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
        moneyCount.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"), domainTypeMoney);
        moneyCount.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), "money");
        moneyCount.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"), "Сумма доходов");

        presentation.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstComponent"), moneyCount);

        //Presentation array
        Individual presentationArray = inf.createIndividual(inf.createResource());
        presentationArray.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElementPresentation"));
        presentationArray.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"array");
        presentationArray.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),"Массив из 3 чисел");

        Individual arrayDataElement = inf.createIndividual(inf.createResource());
        arrayDataElement.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
        arrayDataElement.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"), domainTypeArray);
        arrayDataElement.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), "array");
        arrayDataElement.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"), "Сумма доходов");

        presentationArray.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstComponent"), arrayDataElement);



        data_element_sum.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentation);
        data_element_feb.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentation);
        data_element_jan.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentation);

        data_element_sum.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentationArray);
        data_element_feb.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentationArray);
        data_element_jan.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentationArray);

    }

    public void addNewProblem(int id, String text, String notice, String funcName)
    {
        Individual problem = inf.createIndividual(inf.createResource());
        problem.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Problem"));
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasFullText"), text);
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasNotice"), notice);
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasFuncName"),funcName);
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasID"), inf.createTypedLiteral(id));
    }

    public void setPhraseOrder(int problemId)
    {
        Resource problem = findProblemByID(String.valueOf(problemId));
        String queryString = "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?lb ?phrase" +
                " WHERE { "+
                "?p a po:Problem ." +
                "?p po:hasID " + problemId +" ." +
                "?p po:hasData ?de . "+
                "?phrase po:describe ?de ."+
                "?phrase po:hasLeftBorder ?lb ."+
                " } ORDER BY ASC(?lb)";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();

        List<Resource> phrases = new ArrayList<>();

        while ( (rs.hasNext()) ) {
            phrases.add(rs.next().get("?phrase").asResource());
        }
        if (!phrases.isEmpty())
        {
            problem.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstPhrase"),phrases.get(0));
            for (int i=1; i<phrases.size(); i++)
            {
                phrases.get(i-1).addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasRighterPhrase"),phrases.get(i));
            }
        }
    }

    public void addProblemDataElement(int problemId, int dataElementId, String name, String mission, DataElement.DataElementDirection direction, int leftBorder, int rightBorder)
    {
        Individual dataElement = inf.createIndividual(inf.createResource());
        dataElement.addOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
        dataElement.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),name);
        dataElement.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),mission);
        dataElement.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasID"), inf.createTypedLiteral(dataElementId));
        Resource problem = findProblemByID(String.valueOf(problemId));
        String directionEdge = "";
        switch (direction)
        {
            case INPUT_DATA:
                directionEdge = "http://www.semanticweb.org/problem-ontology#hasInputData";
                break;
            case OUTPUT_DATA:
                directionEdge = "http://www.semanticweb.org/problem-ontology#hasOutputData";
                break;
            case CHANGED_DATA:
                directionEdge = "http://www.semanticweb.org/problem-ontology#hasChangedData";
                break;
        }
        problem.addProperty(inf.getObjectProperty(directionEdge), dataElement);

        Individual phrase = inf.createIndividual(inf.createResource());
        phrase.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasLeftBorder"),inf.createTypedLiteral(leftBorder));
        phrase.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasRightBorder"),inf.createTypedLiteral(rightBorder));
        phrase.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Phrase"));
        phrase.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#describe"),dataElement);
    }

    public Resource findDataElementById(int id)
    {
        String queryString = "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?de " +
                " WHERE { "+
                "?de a po:DataElement ." +
                "?de po:hasID " + id +" ." +
                " }";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if ( (rs.hasNext()) ) {
            return rs.next().get("?de").asResource();
        }
        return null;
    }

    public Resource findPresentationById(int id)
    {
        String queryString = "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?de " +
                " WHERE { "+
                "?de a po:DataElementPresentation ." +
                "?de po:hasID " + id +" ." +
                " }";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if ( (rs.hasNext()) ) {
            return rs.next().get("?de").asResource();
        }
        return null;
    }

    public Resource findDomainTypeById(int id)
    {
        String queryString = "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?de " +
                " WHERE { "+
                "?de a po:DomainType ." +
                "?de po:hasID " + id +" ." +
                " }";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if ( (rs.hasNext()) ) {
            return rs.next().get("?de").asResource();
        }
        return null;
    }

    public void addDomainType(int id, String name, String mission, DomainType.HighlyLevelTypes type)
    {
        Individual domainType = inf.createIndividual(inf.createResource());
        domainType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasID"), inf.createTypedLiteral(id));
        domainType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), name);
        domainType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"), mission);

        HashMap<DomainType.HighlyLevelTypes, String> typeClasses = new HashMap<>();
        typeClasses.put(DomainType.HighlyLevelTypes.INTEGER_NUMBER, "http://www.semanticweb.org/problem-ontology#IntegerNumber");
        typeClasses.put(DomainType.HighlyLevelTypes.REAL_NUMBER, "http://www.semanticweb.org/problem-ontology#FloatNumber");
        typeClasses.put(DomainType.HighlyLevelTypes.ENTITY, "http://www.semanticweb.org/problem-ontology#Entity");

        domainType.addOntClass(inf.getOntClass(typeClasses.get(type)));
    }

    public void setDomainTypeMinAndMax(int domainTypeId, int min, int max)
    {
        Resource domainType = findDomainTypeById(domainTypeId);
        domainType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#minValue"),inf.createTypedLiteral(min));
        domainType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#maxValue"),inf.createTypedLiteral(max));
    }

    public void setDomainTypeForDataElement(int dataElementId, int domainTypeId)
    {
        Resource dataElement = findDataElementById(dataElementId);
        Resource domainType = findDomainTypeById(domainTypeId);
        dataElement.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"),domainType);
    }

    public void addPresentationOfDataElement(int dataElementId, int presentationID, String name, String mission)
    {
        Resource dataElement = findDataElementById(dataElementId);

        Individual presentation = inf.createIndividual(inf.createResource());
        presentation.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasID"), inf.createTypedLiteral(presentationID));
        presentation.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), name);
        presentation.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),mission);
        presentation.addOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElementPresentation"));

        dataElement.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentation);
    }

    public void setComponentsForPresentation(int presentationID, List<DataComponent> components)
    {
        Resource presentation = findPresentationById(presentationID);

        ArrayList<Resource> componentResources = new ArrayList<>();
        for (DataComponent component: components)
        {
            Individual comp = inf.createIndividual(inf.createResource());
            comp.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), component.getName());
            comp.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"), component.getMission());
            comp.addOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
            Resource domainType = findDomainTypeById(component.getDomainType().getId().intValue());
            if (domainType == null) {
                addDomainType(component.getDomainType().getId().intValue(), component.getDomainType().getName(), component.getDomainType().getMission(), component.getDomainType().getType());
                if (component.getDomainType().getType() == DomainType.HighlyLevelTypes.INTEGER_NUMBER)
                {
                    setDomainTypeMinAndMax(component.getDomainType().getId().intValue(), (int)component.getDomainType().getMinValue(), (int)component.getDomainType().getMaxValue());
                }
            }
            domainType = findDomainTypeById(component.getDomainType().getId().intValue());
            comp.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"), domainType);
            componentResources.add(comp);
        }

        if (!componentResources.isEmpty())
        {
            presentation.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstComponent"), componentResources.get(0));

            for (int i=1; i<componentResources.size(); i++)
            {
                componentResources.get(i-1).addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasNextComponent"), componentResources.get(i));
            }
        }

    }

    public void addDomainType(String name, String mission, DomainType.HighlyLevelTypes type)
    {
        Individual domainType = inf.createIndividual(inf.createResource());
        String ontClassIRI = "";
        switch (type)
        {
            case INTEGER_NUMBER:
                ontClassIRI = "http://www.semanticweb.org/problem-ontology#IntegerNumber";
                break;
            case REAL_NUMBER:
                ontClassIRI = "http://www.semanticweb.org/problem-ontology#FloatNumber";
                break;
            case ENTITY:
                ontClassIRI = "http://www.semanticweb.org/problem-ontology#Entity";
                break;
        }
        domainType.addOntClass(inf.getOntClass(ontClassIRI));
        domainType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), name);
        domainType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"), mission);
    }

    public void addEntityFields(int domainTypeId, List<EntityField> fields)
    {
        Resource domainType = findDomainTypeById(domainTypeId);
        ArrayList<Resource> fieldResources = new ArrayList<>();
        for (EntityField field: fields)
        {
            Individual fieldIndividual = inf.createIndividual(inf.createResource());
            fieldIndividual.addOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
            fieldIndividual.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), field.getName());
            fieldIndividual.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"), field.getMission());

            DomainType dt = field.getOwnDomainType();
            Resource ownDomainType = findDomainTypeById(dt.getId().intValue());
            if (ownDomainType == null)
            {
                addDomainType(dt.getId().intValue(),dt.getName(), dt.getMission(),dt.getType());
                setDomainTypeMinAndMax(dt.getId().intValue(), (int)dt.getMinValue(), (int)dt.getMaxValue());
                ownDomainType = findDomainTypeById(dt.getId().intValue());
            }
            fieldIndividual.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"),ownDomainType);

            fieldResources.add(fieldIndividual);
        }
        domainType.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstField"), fieldResources.get(0));
        for (int i=1; i<fieldResources.size(); i++)
        {
            fieldResources.get(i-1).addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasNextField"), fieldResources.get(i));
        }

    }

    protected Model readModel(String modelFile)
    {
        // create an empty model
        Model model = ModelFactory.createDefaultModel();
        String inputFileName=modelFile;
        // use the RDFDataMgr to find the input file
        InputStream in = RDFDataMgr.open( inputFileName );
        if (in == null) {
            throw new IllegalArgumentException(
                    "File: " + inputFileName + " not found");
        }

        // read the RDF/XML file
        model.read(in, null);
        return model;
    }

    protected void createModel(String rulesFile)
    {
        Model problemModel = readModel(PROBLEM_ONTOLOGY_FILE);
        Model sessionModel = readModel(SESSION_ONTOLOGY_FILE);
        Model languageModel = readModel(LANGUAGE_ONTOLOGY_FILE);
        Model errorModel = readModel(ERROR_ONTOLOGY_FILE);
        model = ModelFactory.createUnion(problemModel, sessionModel);
        model = ModelFactory.createUnion(model, languageModel);
        model = ModelFactory.createUnion(model, errorModel);


        //Ризонер для интеракции 0 "Выделение элементов данных из текста"
        reasoners[0] = createReasonerForInteraction(ELEMENT_BORDERS_RULES);

        //Ризонер для интеракции 1 "Направления элементов данных"
        reasoners[1] = createReasonerForInteraction(DATA_DIRECTION_RULES);

        //Ризонер для интеракции 2 "Представления элементов данных"
        reasoners[2] = createReasonerForInteraction(DATA_PRESENTATION_RULES);

        //Ризонер для интеракции 3 "Выбор параметров и возвращаемых значений"
        reasoners[3] = createReasonerForInteraction(PARAMETERS_RETURNS_RULES);

        //Ризонер для интерации 4 "Выбор типов параметров и возвращаемого значения"
        reasoners[4] = createReasonerForInteraction(TYPES_RULES);

        //Ризонер для интеракции 5 "Написание прототипа функции"
        reasoners[5] = createReasonerForInteraction(PROTOTYPE_RULES);


        inf = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, model);

    }

    private Reasoner createReasonerForInteraction(String rulesFile)
    {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(rulesFile);
        String rules = readStream( stream);
        reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
        reasoner.setDerivationLogging(true);
        reasoner.setDerivationLogging(true);
        reasoner.bindSchema(model);
        return reasoner;
    }

    protected static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder(512);
        try {
            Reader r = new InputStreamReader(is, "UTF-8");
            int c = 0;
            while ((c = r.read()) != -1) {
                sb.append((char) c);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

    protected void calcModel()
    {
        infModel = ModelFactory.createInfModel(reasoner, (infModel == null) ? inf : infModel);
    }

    public Resource addStudent(String id, String problemID)
    {


        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "SELECT ?student " +
                " WHERE { "+
                "?student <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasID> \"" + id +"\"" +
                " }";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (! (rs.hasNext()) ) {

            Individual student = inf.createIndividual(inf.createResource());
            student.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Student"));
            student.addProperty(inf.createDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasID"), id);
            student.addProperty(inf.createDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#notFoundElementsCount"), inf.createTypedLiteral(3));
            student.addProperty(inf.createDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#currentInteraction"), inf.createTypedLiteral(0));

            student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#solves"), findProblemByID(problemID));
        }

        while (rs.hasNext())
        {
            Resource student = rs.next().get("?student").asResource();
            return student;
        }
        return null;
    }

    private Resource findProblemByID(String id)
    {
        String queryString = "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?problem " +
                " WHERE { "+
                "?problem a po:Problem ." +
                "?problem po:hasID " + id +" ." +
                " }";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if ( (rs.hasNext()) ) {
            return rs.next().get("?problem").asResource();
        }
        return null;
    }

    private void setPresenatitionForStudent(String studentID, String dataElementName, String presentationName)
    {
        Resource student = addStudent(studentID,null);
        Resource dataElement = findDataElementByName(dataElementName);
        Resource presentation = findDataElementPresentationByName(dataElementName, presentationName);

        Individual presentationChoose = inf.createIndividual(inf.createResource());
        presentationChoose.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#PresentationChoose"));
        presentationChoose.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#ofDataElement"), dataElement);
        presentationChoose.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#chosenPresentation"), presentation);
        presentationChoose.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#ofStudent"), student);
        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#chosePresentation"), presentation);
        System.out.println("Student: "+studentID+" choose presentation "+presentationName );
    }

    private void addParameterForStudent(String studentID, String elementName, String componentName, String paramDirection)
    {
        HashMap<String,String> classesForDirections = new HashMap<>();
        classesForDirections.put("read-only","http://www.semanticweb.org/problem-ontology#InputParameterChoose");
        classesForDirections.put("read-write","http://www.semanticweb.org/problem-ontology#UpdatableParameterChoose");
        classesForDirections.put("write-only","http://www.semanticweb.org/problem-ontology#OutputParameterChoose");

        Resource student = addStudent(studentID,null);
        Resource component = findComponentByName(studentID, elementName, componentName);
        String paramName =
                (getStudentComponentsOfDataElement(studentID, elementName).size()>1) ?
                        elementName + "_" + componentName : elementName;

        Individual parameterChoose = inf.createIndividual(inf.createResource());
        parameterChoose.setOntClass(inf.getOntClass(classesForDirections.get(paramDirection)));
        parameterChoose.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#chosenComponent"), component);
        parameterChoose.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#ofStudent"), student);
        parameterChoose.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), paramName);
    }

    private void addReturnValueForStudent(String studentID, String elementName, String componentName)
    {
        Resource student = addStudent(studentID,null);
        Resource component = findComponentByName(studentID, elementName, componentName);

        Individual returnValueChoose = inf.createIndividual(inf.createResource());
        returnValueChoose.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#ReturnValueChoose"));
        returnValueChoose.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#chosenComponent"), component);
        returnValueChoose.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#ofStudent"), student);
        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasReturnValue"), returnValueChoose);
    }

    public List<HashMap<String, String>> getStudentComponentsOfDataElement(String studentID, String elementName)
    {
        List<HashMap<String, String>> components = new ArrayList<>();

        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?name ?mission ?comp WHERE " +
                "{" +
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?choose a po:PresentationChoose . " +
                "?choose so:ofStudent ?student . " +
                "?choose po:ofDataElement ?de . " +
                "?choose po:chosenPresentation ?pres . " +
                "?de po:hasPresentation ?pres . " +
                "?de a po:DataElement . " +
                "?de po:name \""+elementName+"\" . " +
                "?de po:mission ?demission . " +
                "?pres po:hasFirstComponent ?comp . " +
                "?comp po:name ?name ." +
                "?comp po:mission ?mission . " +
                "} ORDER BY DESC(?name)";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext())
        {
            System.out.println("Number count: "+rs.getRowNumber());
            QuerySolution qs = rs.next();
            //components.put(qs.get("?name").toString(), qs.get("?mission").toString());

            Resource component = qs.get("?comp").asResource();
            List<Resource> componentList = getSiblingComponents(component);
            for (Resource comp: componentList)
            {
                String name = comp.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name")).getLiteral().getString();
                String mission = comp.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getLiteral().getString();

                HashMap<String,String> map = new HashMap<String,String>();
                map.put("name", name);
                map.put("mission",mission);
                components.add(map);//put(qs.get("?dename") + "." + componentName, qs.get("?demission") + "." + componentMission);
                //components.put(name, mission);
            }

        }
        System.out.println("Components: ");
        System.out.println(components);
        System.out.println("Element name:");
        System.out.println(elementName);
        return components;
    }

    public HashMap<String,List<HashMap<String, String>>> getStudentComponents(String studentID)
    {
        HashMap<String,List<HashMap<String, String>>> components = new HashMap<>();

        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?dename ?demission ?name ?mission ?comp WHERE " +
                "{" +
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?choose a po:PresentationChoose . " +
                "?choose so:ofStudent ?student . " +
                "?choose po:ofDataElement ?de . " +
                "?choose po:chosenPresentation ?pres . " +
                "?de po:hasPresentation ?pres . " +
                "?de a po:DataElement . " +
                "?de po:name ?dename . " +
                "?de po:mission ?demission . " +
                "?pres po:hasFirstComponent ?comp . " +
                "?comp po:name ?name ." +
                "?comp po:mission ?mission . " +
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext())
        {
            System.out.println("Number count: "+rs.getRowNumber());
            QuerySolution qs = rs.next();
            //components.add(new HashMap<String, String>(qs.get("?dename") + "." + qs.get("?name").toString(), qs.get("?demission") + "." + qs.get("?mission").toString())); //put(qs.get("?dename") + "." + qs.get("?name").toString(), qs.get("?demission") + "." + qs.get("?mission").toString());
            Resource firstComponent = qs.get("?comp").asResource();

            components.put(qs.get("?dename").asLiteral().getString(), getStudentComponentsOfDataElement(studentID, qs.get("?dename").asLiteral().getString() ));

            /*for (Resource component: getSiblingComponents(firstComponent))
            {
                String componentName = component.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name")).getLiteral().getString();
                String componentMission = component.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getLiteral().getString();
                HashMap<String,String> map = new HashMap<String,String>();
                //map.put(qs.get("?dename") + "." + componentName, qs.get("?demission") + "." + componentMission);
                map.put("name", qs.get("?dename") + "." + componentName);
                map.put("mission",qs.get("?demission") + "." + componentMission);
                //components.add(map);//put(qs.get("?dename") + "." + componentName, qs.get("?demission") + "." + componentMission);
            }*/

        }
        System.out.println("Components: ");
        System.out.println(components);
        return components;
    }

    private List<Resource> getSiblingComponents(Resource firstComponent)
    {
        List<Resource> list = new ArrayList<>();
        //list.add(firstComponent);
        Resource currComponent = firstComponent;
        while (currComponent != null)
        {
            list.add(currComponent);
            currComponent = getNextComponent(currComponent);
        }
        return list;
    }

    private Resource getNextComponent(Resource component)
    {
        if (!component.hasProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasNextComponent")))
            return null;
        Resource nextComponent = component.getProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasNextComponent")).getResource();
        return nextComponent;
    }

    public ArrayList<String> getStudentParameters(String studentID)
    {
        ArrayList<String> parameters = new ArrayList<String>();

        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?name ?dt WHERE " +
                "{" +
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?choose a po:ParameterChoose . " +
                "?choose po:name ?name . " +
                //"?choose po:hasDataType ?dt"+
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext())
        {
            System.out.println("Number count: "+rs.getRowNumber());
            QuerySolution qs = rs.next();
            parameters.add(qs.get("?name").toString());
        }

        return parameters;
    }

    private Resource findDataElementByName(String name)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?de WHERE " +
                "{" +
                "?de a po:DataElement . " +
                "?problem a po:Problem . " +
                "?de po:name " + "\"" + name + "\" . " +
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext())
        {
            return rs.next().get("?de").asResource();
        }
        return null;
    }

    private Resource findDataElementPresentationByName(String dataElementName, String presentationName)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?pres WHERE " +
                "{" +
                "?de a po:DataElement . " +
                "?problem a po:Problem . " +
                "?de po:name " + "\"" + dataElementName + "\" . " +
                "?de po:hasPresentation ?pres . " +
                "?pres po:name " + "\"" + presentationName + "\" . " +
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext())
        {
            return rs.next().get("?pres").asResource();
        }
        return null;
    }

    private Resource findComponentByName(String studentID, String dataElementName, String componentName)
    {

        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?comp WHERE " +
                "{" +
                "?student a so:Student . " +
                "?student so:hasID \"" + studentID + "\" . " +
                "?prchoose a po:PresentationChoose . " +
                "?prchoose po:ofDataElement ?de . " +
                "?prchoose po:chosenPresentation ?pres . " +
                "?de a po:DataElement . " +
                "?problem a po:Problem . " +
                "?de po:name " + "\"" + dataElementName + "\" . " +
                "?pres po:hasFirstComponent ?comp . " +
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext())
        {
            Resource component = rs.next().get("?comp").asResource();
            List<Resource> components = getSiblingComponents(component);

            for (Resource c: components)
            {
                String name = c.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name")).getLiteral().getString();
                if (name.equals(componentName))
                {
                    c.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#ofDataElement"), findDataElementByName(dataElementName));
                    return c;
                }
            }

        }
        return null;
    }

    public String getFullText(String studentID)
    {
        String queryString =
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?problem WHERE { "+
                        "?student a so:Student . " +
                        "?student so:hasID \"" + studentID + "\" ." +
                        "?student so:solves ?problem ." +
                        //"?problem a po:Problem ." +
                        //"?problem  po:hasFullText  ?text ." +
                "}";

        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoners[0], inf);
        //inf.write(System.out);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        //System.out.println(rs.getRowNumber());

        if ( rs.hasNext())
        {
            System.out.println("+");
            QuerySolution qs = rs.next();
            //System.out.println(qs.get("?text").toString());
            Resource problem = qs.get("?problem").asResource();
            String fullText = problem.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasFullText")).getLiteral().getString();
            return fullText;
        }
        return "No value";
        //return "";
    }

    public boolean studentHasChosenElement(String studentID, String elementName)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#>" +
                " SELECT (count(?element) as ?cnt)  WHERE {" +
                " ?student a so:Student ." +
                " ?student so:hasID \"" + studentID + "\" . " +
                " ?student so:foundElement ?element ." +
                " ?element <http://www.semanticweb.org/problem-ontology#name>  \"" + elementName + "\" ." +
                "}";

        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext())
        {
            QuerySolution qs = rs.next();
            int s = qs.get("?cnt").asLiteral().getInt();
            return (s>0);
        }
        return false;
    }

    public HashMap<String, String> chooseDataElementBorders(String studentID, String leftBorderNumLexem, String rightBorderNumLexem)
    {

        HashMap<String, String> result = new HashMap<String,String>();

        Resource student = addStudent(studentID,null);
        Individual answer = inf.createIndividual(inf.createResource());
        answer.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Answer"));
        answer.addOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Phrase"));
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasLeftBorder"),ResourceFactory.createTypedLiteral(Integer.parseInt(leftBorderNumLexem)));
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasRightBorder"), ResourceFactory.createTypedLiteral(Integer.parseInt(rightBorderNumLexem)));

        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"), answer);
        infModel = ModelFactory.createInfModel(reasoners[0], inf);
        //calcModel();
        //infModel.write(System.out);

        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?correct ?message ?answ WHERE " +
                "{" +
                "?student a so:Student ." +
                " ?student so:hasID \""+studentID+"\" . " +
                "?student so:hasAnswer ?answ. " +
                "?answ po:hasLeftBorder "+leftBorderNumLexem+" . " +
                "?answ po:hasRightBorder "+rightBorderNumLexem+" . " +
                "?answ so:isCorrectAnswer ?correct . " +
                "?student so:hasMessage ?message . " +
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext())
        {
            QuerySolution qs = rs.next();
            int s = qs.get("?correct").asLiteral().getInt();
            result.put("correct", (s==1) ? "true" : "false");
            Resource answExemplar = qs.getResource("?answ").asResource();
            result.put("message", getErrorString(answExemplar, studentID).get(Language.RU));
            answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            if (s==1)
            {
                String queryStringElementName = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                        "SELECT ?mission ?name ?element ?notfound ?interaction WHERE " +
                        "{ " +
                        "?phrase a po:Phrase . " +
                        "?phrase po:hasLeftBorder " + leftBorderNumLexem + " ." +
                        "?phrase po:hasRightBorder " + rightBorderNumLexem + " ." +
                        "?phrase po:describe ?element . " +
                        "?element po:mission ?mission . " +
                        "?element po:name ?name . " +
                        "?student a so:Student . " +
                        " ?student so:hasID \""+studentID+"\" . " +
                        "?student so:notFoundElementsCount ?notfound . " +
                        "?student so:currentInteraction ?interaction . " +
                        "} ";
                Query queryElementName = QueryFactory.create(queryStringElementName);
                QueryExecution qExecElementName = QueryExecutionFactory.create(queryElementName, infModel);
                ResultSet rsElementName = qExecElementName.execSelect();
                if (rsElementName.hasNext())
                {
                    QuerySolution qsElementName  = rsElementName.next();
                    Literal missionLit = qsElementName.get("?mission").asLiteral();
                    String mission = missionLit.getString();
                    result.put("mission", mission);

                    Literal nameLit = qsElementName.get("?name").asLiteral();
                    String name = nameLit.getString();
                    result.put("name", name);

                    Resource element = qsElementName.get("?element").asResource();
                    student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#foundElement"), element);
                    int notFound = qsElementName.get("?notfound").asLiteral().getInt();
                    student.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#notFoundElementsCount")).changeLiteralObject(notFound);
                    int interaction_num = qsElementName.get("?interaction").asLiteral().getInt();
                    student.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#currentInteraction")).changeLiteralObject(interaction_num);
                    result.put("interaction", String.valueOf(interaction_num));
                }
            }
        }

        student.removeAll(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"));

        return result;
    }

    public HashMap<String, String> chooseElementDirection(String studentID, String elementName, String direction)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID,null);
        Individual answer = inf.createIndividual(inf.createResource());
        answer.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Answer"));
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasDirection"), direction);
        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"), answer);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasElementName"), elementName);
        infModel = ModelFactory.createInfModel(reasoners[1], inf);
        String queryString="PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?correct ?message ?answ WHERE " +
                "{" +
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?student so:hasAnswer ?answ. " +
                "?answ po:hasElementName \"" + elementName + "\" ." +
                "?answ po:hasDirection \"" + direction + "\" . " +
                "?answ so:isCorrectAnswer ?correct . " +
                "?student so:hasMessage ?message . " +
                "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            int s = qs.get("?correct").asLiteral().getInt();
            answ.put("correct", (s == 1) ? "true" : "false");
            answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            Resource answExemplar = qs.getResource("?answ").asResource();
            answ.put("message", getErrorString(answExemplar, studentID).get(Language.RU));
        }
        infModel.toString();
        //answ.put("correct","true");
        return answ;
    }

    public HashMap<String, String> getPresentationsForDataElement(String studentID, String elementName)
    {
        HashMap<String,String> presentations = new HashMap<>();

        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?pres_name ?pres_mission  WHERE " +
                "{" +
                "?de a po:DataElement . " +
                "?problem a po:Problem . " +
                "?de po:name " + "\"" + elementName + "\" . " +
                "?de po:hasPresentation ?pres . " +
                "?pres po:name  ?pres_name . " +
                "?pres po:mission ?pres_mission. " +
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext())
        {
            QuerySolution qs = rs.next();
            String presName = qs.get("?pres_name").asLiteral().getString();
            String presMission = qs.get("?pres_mission").asLiteral().getString();
            presentations.put(presName,presMission);
        }

        return presentations;
    }
    public HashMap<String, String> choosePresentationForDataElement(String studentID, String elementName, String presentationName)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID,null);
        Individual answer = inf.createIndividual(inf.createResource());
        answer.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Answer"));
        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"), answer);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasElementName"), elementName);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasPresentationName"), presentationName);
        infModel = ModelFactory.createInfModel(reasoners[2], inf);


        String queryString="PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?correct ?message WHERE " +
                "{" +
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?student so:hasAnswer ?answ. " +
                "?answ po:hasElementName \"" + elementName + "\" ." +
                "?answ po:hasPresentationName \"" + presentationName + "\" . " +
                "?answ so:isCorrectAnswer ?correct . " +
                "?answ so:hasMessage ?message . " +
                "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            int s = qs.get("?correct").asLiteral().getInt();
            answ.put("correct", (s == 1) ? "true" : "false");

            if (s==1)
            {
                setPresenatitionForStudent(studentID, elementName, presentationName);
            }

            //answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            answ.put("message", qs.get("?message").asLiteral().getString());
        }
        infModel.toString();
        student.removeAll(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"));

        /*
        if ((elementName.equals("first_date_at_school") || elementName.equals("date_of_birth")) && presentationName.equals("freefields2"))
        {
            answ.put("correct", "false");
            answ.put("message","A property \"year\" is not implemented.");
        }
        else
        {
            answ.put("correct","true");
            answ.put("message","Верно");
        }*/



        //answ.put("correct","true");
        return answ;
    }

    public HashMap<String, String> chooseParameterOrReturnValue(String studentID, String elementName, String componentName, String parameterOrReturn)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID,null);
        Individual answer = inf.createIndividual(inf.createResource());
        answer.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Answer"));
        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"), answer);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasElementName"), elementName);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasComponentName"), componentName);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasTransferMethod"), parameterOrReturn);
        infModel = ModelFactory.createInfModel(reasoners[3], inf);

        String queryString=
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                        "SELECT ?correct ?answ WHERE " +
                        "{" +
                        "?student a so:Student . " +
                        " ?student so:hasID \""+studentID+"\" . " +
                        "?student so:hasAnswer ?answ. " +
                        "?answ po:hasComponentName \"" + componentName + "\" ." +
                        "?answ po:hasTransferMethod \"" + parameterOrReturn + "\" . " +
                        "?answ so:isCorrectAnswer ?correct . " +
                        "?answ so:hasMessage ?message . " +
                        "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            int s = qs.get("?correct").asLiteral().getInt();
            answ.put("correct", (s == 1) ? "true" : "false");
            answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            Resource answExemplar = qs.get("?answ").asResource();
            answ.put("message", getErrorString(answExemplar, studentID).get(Language.RU) );

            if (s==1 && (parameterOrReturn.equals("read-only") || parameterOrReturn.equals("write-only") || parameterOrReturn.equals("read-write")))
            {
                addParameterForStudent(studentID, elementName, componentName, parameterOrReturn);
            }
            if (s==1 && parameterOrReturn.equals("return"))
            {
                addReturnValueForStudent(studentID, elementName, componentName);
            }
        }
        infModel.toString();

        student.removeAll(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"));

        //answ.put("correct","true");
        return answ;
    }

    public HashMap<String, String> chooseParameterType(String studentID, String parameterName, String typeName)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID,null);
        Individual type = inf.getIndividual("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+typeName);

        Individual answer = inf.createIndividual(inf.createResource());
        answer.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Answer"));
        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"), answer);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#ofParameterWithName"), parameterName);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasTypeName"), typeName);
        answer.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasType"), type);

        infModel = ModelFactory.createInfModel(reasoners[4], inf);

        String queryString=
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                        "SELECT ?correct ?message ?answ WHERE " +
                        "{" +
                        "?student a so:Student . " +
                        " ?student so:hasID \""+studentID+"\" . " +
                        "?student so:hasAnswer ?answ. " +
                        "?answ po:ofParameterWithName \"" + parameterName + "\" ." +
                        "?answ po:hasTypeName \"" + typeName + "\" . " +
                        "?answ so:isCorrectAnswer ?correct . " +
                        "?answ so:hasMessage ?message . " +
                        "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            int s = qs.get("?correct").asLiteral().getInt();
            answ.put("correct", (s == 1) ? "true" : "false");
            if (s==1)
            {
                setParameterType(studentID, parameterName, typeName);
            }
            answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            Resource answExemplar = qs.getResource("?answ").asResource();
            answ.put("message", getErrorString(answExemplar, studentID).get(Language.RU));

        }
        infModel.toString();

        return answ;
    }

    private void setParameterType(String studentID, String parameterName, String typeName)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?param WHERE"+
                "{"+
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?param a po:ParameterChoose ."+
                "?param so:ofStudent ?student ."+
                "?param po:name \""+parameterName+"\"."+
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();

        if (rs.hasNext())
        {
            QuerySolution qs = rs.next();
            Resource param = qs.get("?param").asResource();
            Individual datatype = inf.getIndividual("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+typeName);
            param.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDataType"), datatype);

            Resource lexemType = createLexemByTypeAndName("IntLexem","int");

            List<Resource> typeLexemes = getLexemesForDataType(datatype);
            Resource lexemParamName = createLexemByTypeAndName("ParameterNameLexem",parameterName);
            typeLexemes.add(lexemParamName);

            param.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"),typeLexemes.get(0));

            for (int i=1; i<typeLexemes.size(); i++)
            {
                typeLexemes.get(i-1).addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem"),typeLexemes.get(i));
            }

            for (int i=0; i<typeLexemes.size(); i++)
            {
                typeLexemes.get(i).addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentLexemNum"),inf.createTypedLiteral(i+1));
            }

            /*
            lexemType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentLexemNum"),inf.createTypedLiteral(1));
            lexemType.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem"),lexemParamName);
            lexemParamName.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentLexemNum"),inf.createTypedLiteral(2));
            param.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"),lexemType);
             */

        }
    }

    public HashMap<String, String> chooseReturnValuetype(String studentID, String typeName)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID,null);
        Individual type = inf.getIndividual("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+typeName);

        Individual answer = inf.createIndividual(inf.createResource());
        answer.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Answer"));
        student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasAnswer"), answer);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#ofReturnValue"),inf.createTypedLiteral(1) );
        answer.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasType"), type);
        answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasTypeName"), typeName);

        infModel = ModelFactory.createInfModel(reasoners[4], inf);

        String queryString=
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                        "SELECT ?answ ?correct ?message WHERE " +
                        "{" +
                        "?student a so:Student . " +
                        " ?student so:hasID \""+studentID+"\" . " +
                        "?student so:hasAnswer ?answ. " +
                        "?answ po:ofReturnValue 1 ." +
                        "?answ po:hasTypeName \"" + typeName + "\" . " +
                        "?answ so:isCorrectAnswer ?correct . " +
                        "?answ so:hasMessage ?message . " +
                        "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            int s = qs.get("?correct").asLiteral().getInt();
            answ.put("correct", (s == 1) ? "true" : "false");
            if (s==1)
            {
                setReturnValueType(studentID, typeName);
            }

            /*
            answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            answ.put("message", qs.get("?message").asLiteral().getString());

             */
            answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            Resource answExemplar = qs.getResource("?answ").asResource();
            answ.put("message", getErrorString(answExemplar, studentID).get(Language.RU));

        }
        infModel.toString();

        return answ;
    }

    private void setReturnValueType(String studentID, String typeName)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                            "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?rv WHERE "+
                "{"+
                "?rv a po:ReturnValueChoose ."+
                "?rv so:ofStudent ?student ." +
                "?student a so:Student ."+
                "?student so:hasID \""+studentID+"\"."+
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();

        if (rs.hasNext())
        {
            QuerySolution qs = rs.next();
            Resource rv = qs.get("?rv").asResource();
            Individual datatype = inf.getIndividual("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+typeName);
            rv.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDataType"), datatype);
            List<Resource> datatypeLexemes = getLexemesForDataType(datatype);
            rv.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"), datatypeLexemes.get(0));

            for (int i=1; i<datatypeLexemes.size(); i++)
            {
                datatypeLexemes.get(i-1).addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem"), datatypeLexemes.get(i));
            }

            for (int i=0; i<datatypeLexemes.size(); i++)
            {
                datatypeLexemes.get(i).addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentLexemNum"), inf.createTypedLiteral(i+1));
            }

            /*
            Resource intLexem = createLexemByTypeAndName("IntLexem","int");
            intLexem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentLexemNum"), inf.createTypedLiteral(1));
            rv.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"), intLexem);
             */
        }


    }

    private void generateDatatypeLexemes(Resource datatype)
    {
        RDFNode datatypeClass = getDatatypeClass(datatype);
        datatype.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"), createLexemByTypeAndName("Int", "int"));
    }

    private void generateLexemesForAlldatatypes(String studentID)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT DISTINCT ?dt WHERE"+
                "{"+
                "{"+
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                //"?param a po:ParameterChoose ."+
                "?param so:ofStudent ?student ."+
                "?param po:hasDataType ?dt"+
                "}"+
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext())
        {
            QuerySolution qs = rs.next();
            Resource dt = qs.get("?dt").asResource();
            generateDatatypeLexemes(dt);
            break;
        }
    }

    private void generateParameterSequence(String studentID)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT  DISTINCT ?param ?problem ?name WHERE"+
                "{"+
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?param a po:ParameterChoose ."+
                "?param po:name ?name ."+
                "?param so:ofStudent ?student ."+
                "?student so:solves ?problem ." +
                "?problem po:hasData ?el ."+
                "?presChoose po:chosenPresentation ?pres ."+
                "?presChoose so:ofStudent ?student ."+
                "} ORDER BY (?name)";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();

        List<Resource> paramList = new ArrayList<>();
        Resource problemResource = null;
        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            Resource param = qs.get("?param").asResource();
            problemResource = qs.get("?problem").asResource();
            paramList.add(param);
        }
        int len = paramList.size();
        problemResource.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstParameter"),paramList.get(0));
        //paramList.get(0).addProperty(inf.getObjectProperty(""));
        for (int i=1; i<len; i++)
        {
            paramList.get(i-1).addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasNextParameter"), paramList.get(i));
        }

        for (int i=0; i<len; i++)
        {
            paramList.get(i).addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasNumber"), inf.createTypedLiteral(i+1));
        }
    }

    private String getFuncName(String studentID)
    {
        String queryString =
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                        "SELECT ?problem WHERE { "+
                        "?student a so:Student . " +
                        "?student so:hasID \"" + studentID + "\" ." +
                        "?student so:solves ?problem ." +
                        //"?problem a po:Problem ." +
                        //"?problem  po:hasFullText  ?text ." +
                        "}";

        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoners[0], inf);
        //inf.write(System.out);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        //System.out.println(rs.getRowNumber());

        if ( rs.hasNext())
        {
            System.out.println("+");
            QuerySolution qs = rs.next();
            //System.out.println(qs.get("?text").toString());
            Resource problem = qs.get("?problem").asResource();
            String fullText = problem.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasFuncName")).getLiteral().getString();
            return fullText;
        }
        return "No value";
    }

    public List<HashMap<String,String>> getLexemesForPrototype(String studentID)
    {

        generateParameterSequence(studentID);
        generateLexemesForAlldatatypes(studentID);
        List<HashMap<String,String>> lexemes = new ArrayList<>();

        //Function name lexeme
        HashMap<String,String> funcNameLexem = new HashMap<>();
        funcNameLexem.put("type","FunctionNameLexem");
        funcNameLexem.put("value", getFuncName(studentID));
        lexemes.add(funcNameLexem);

        //Parameter name lexemes
        List<HashMap<String,String>> paramList = getParamList(studentID);
        for (HashMap<String,String> map: paramList)
        {
            HashMap<String,String> paramLexem = new HashMap<>();
            paramLexem.put("type","ParameterNameLexem");
            paramLexem.put("value",map.get("name"));
            lexemes.add(paramLexem);
        }

        //Brackets
        HashMap<String,String> openBracket = new HashMap<>();
        openBracket.put("type","OpenBracketLexem");
        openBracket.put("value","(");

        HashMap<String,String> closeBracket = new HashMap<>();
        closeBracket.put("type","CloseBracketLexem");
        closeBracket.put("value",")");

        lexemes.add(openBracket);
        lexemes.add(closeBracket);

        //Semicolon
        HashMap<String,String> semicolon = new HashMap<>();
        semicolon.put("type","SemicolonLexem");
        semicolon.put("value",";");

        lexemes.add(semicolon);

        //Comma
        HashMap<String,String> comma = new HashMap<>();
        comma.put("type","CommaLexem");
        comma.put("value",",");

        lexemes.add(comma);

        //Type (int)
        HashMap<String,String> intLexem = new HashMap<>();
        intLexem.put("type","IntLexem");
        intLexem.put("value","int");

        //Type (char)
        HashMap<String,String> charLexem = new HashMap<>();
        charLexem.put("type","CharLexem");
        charLexem.put("value","char");

        //Pointer (*)
        HashMap<String,String> pointerLexem = new HashMap<>();
        pointerLexem.put("type","PointerLexem");
        pointerLexem.put("value","*");

        //Void
        HashMap<String,String> voidLexem = new HashMap<>();
        voidLexem.put("type","VoidLexem");
        voidLexem.put("value","void");

        lexemes.add(intLexem);
        lexemes.add(charLexem);
        lexemes.add(voidLexem);
        lexemes.add(pointerLexem);




        return lexemes;
    }

    public List<Resource> getLexemesForDataType(Resource datatype)
    {
        ArrayList<Resource> lexemes = new ArrayList<>();
        if (datatype.hasProperty(RDF.type, inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Int")))
        {
            lexemes.add(createLexemByTypeAndName("Int","int"));
        }
        if (datatype.hasProperty(RDF.type, inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Char")))
        {
            lexemes.add(createLexemByTypeAndName("Char","char"));
        }
        if (datatype.hasProperty(RDF.type, inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Pointer")))
        {
            Resource pointedType = datatype.getPropertyResourceValue(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#pointsTo"));
            List<Resource> pointedTypeLexemes = getLexemesForDataType(pointedType);
            lexemes.addAll(pointedTypeLexemes);
            lexemes.add(createLexemByTypeAndName("Pointer","*"));
        }
        return lexemes;
    }

    public HashMap<String,String> getReturnValue(String studentID)
    {
        HashMap<String,String> returnValue = new HashMap<>();
        String queryString = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "PREFIX lo: <http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#>" +
                "SELECT ?subclass ?dt ?compMission ?comp WHERE"+
                "{" +
                "?param a po:ReturnValueChoose ."+
                "?param so:ofStudent ?student ."+
                "?param po:hasDataType ?dt ." +
                "?param po:chosenComponent ?comp ." +
                "?comp po:mission ?compMission ." +
                "?student so:hasID \""+studentID+"\" ."+
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();

        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            Resource datatype = qs.get("?dt").asResource();
            String componentName = qs.get("?compMission").toString();
            List<Resource> lexemes = getLexemesForDataType(datatype);
            StringBuilder datatypeLexemes = new StringBuilder();
            for (Resource lexeme: lexemes)
            {
                datatypeLexemes.append(lexeme.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#value")).getLiteral().getString()+" ");
            }
            returnValue.put("type",datatypeLexemes.toString());

            Resource dataElement = qs.get("?comp").asResource().getPropertyResourceValue(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#ofDataElement"));
            String dataElementName = dataElement.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getString();
            returnValue.put("mission", dataElementName+" ("+componentName+")");
        }

        return returnValue;
    }

    public List<HashMap<String,String>> getParamList(String studentID)
    {
        List<HashMap<String,String>> paramList = new ArrayList<>();
        String queryString = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "PREFIX lo: <http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#>" +
                "SELECT ?name ?subclass ?dt ?compMission ?comp WHERE"+
                "{" +
                    "?param a po:ParameterChoose ."+
                    "?param a ?subclass ."+
                    "?subclass rdfs:subClassOf* po:ParameterChoose."+
                    "?param po:name ?name ."+
                    "?param so:ofStudent ?student ."+
                    "?param po:hasDataType ?dt ." +
                    "?param po:chosenComponent ?comp ." +
                    "?comp po:mission ?compMission ." +
                    "?student so:hasID \""+studentID+"\" ."+
                    "FILTER (?subclass != po:ParameterChoose) " +
                "} ORDER BY (?name)";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        while (rs.hasNext()) {
            QuerySolution qs = rs.next();
            HashMap<String,String> paramDescription = new HashMap<>();
            paramDescription.put("name",qs.get("?name").toString());

            HashMap<String,String> classDirections = new HashMap<>();
            classDirections.put("http://www.semanticweb.org/problem-ontology#InputParameterChoose","input");
            classDirections.put("http://www.semanticweb.org/problem-ontology#OutputParameterChoose","output");
            classDirections.put("http://www.semanticweb.org/problem-ontology#UpdatableParameterChoose","updatable");

            paramDescription.put("direction", classDirections.get(qs.get("?subclass").toString()));

            Resource datatype = qs.get("?dt").asResource();

            String componentName = qs.get("?compMission").toString();

            List<Resource> lexemes = getLexemesForDataType(datatype);
            StringBuilder datatypeLexemes = new StringBuilder();
            for (Resource lexeme: lexemes)
            {
                datatypeLexemes.append(lexeme.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#value")).getLiteral().getString()+" ");
            }
            paramDescription.put("type",datatypeLexemes.toString());

            Resource dataElement = qs.get("?comp").asResource().getPropertyResourceValue(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#ofDataElement"));
            String dataElementName = dataElement.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getString();
            paramDescription.put("mission", dataElementName+" ("+componentName+")");

            paramList.add(paramDescription);
        }
        return paramList;
    }

    public HashMap<String, String> removeLastLexemFromPrototypeCode(String studentID)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID,null);
        Resource code = getStudentsPrototypeCode(studentID);
        removeLastLexem(code);

        infModel = ModelFactory.createInfModel(reasoners[5], inf);

        String queryString=
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                        "PREFIX lo: <http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#>" +
                        "SELECT ?message ?correct WHERE " +
                        "{" +
                        "?student a so:Student . " +
                        " ?student so:hasID \""+studentID+"\" . " +
                        "?code a lo:PrototypeCode . " +
                        "?code so:ofStudent ?student . " +
                        "?code so:hasMessage ?message . " +
                        "?code so:isCorrectAnswer ?correct . " +
                        "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            answ.put("message", qs.get("?message").asLiteral().getString());
            answ.put("correct", (qs.get("?correct").asLiteral().getInt()==1) ? "true" : "false" );
        }
        infModel.toString();
        answ.put("code", getLexemSequenceString(code));

        return answ;
    }
    public HashMap<String, String> addLexemToPrototypeCode(String studentID, String lexemType, String lexemValue)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID,null);
        Resource code = getStudentsPrototypeCode(studentID);
        Resource lexem = createLexemByTypeAndName(lexemType, lexemValue);
        Resource firstLexem = getFirstLexemOfPrototypeCode(code);
        if (firstLexem == null)
        {
            setFirstLexemToPrototypeCode(code, lexem);
        }
        else
        {
            setLastLexemOfPrototypeCode(code, lexem);
        }
        infModel = ModelFactory.createInfModel(reasoners[5], inf);

        String queryString=
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                        "PREFIX lo: <http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#>" +
                        "SELECT ?message ?correct ?code WHERE " +
                        "{" +
                        "?student a so:Student . " +
                        " ?student so:hasID \""+studentID+"\" . " +
                        "?code a lo:PrototypeCode . " +
                        "?code so:ofStudent ?student . " +
                        "?code so:hasMessage ?message . " +
                        "?code so:isCorrectAnswer ?correct . " +
                        "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, infModel);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            answ.put("correct", (qs.get("?correct").asLiteral().getInt()==1) ? "true" : "false" );

            Resource newCode = qs.get("?code").asResource();
            Resource newCodeLastLexem = getLastLexemOfPrototypeCode(newCode);

            if (newCode.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#isCompleted")))
            {
                answ.put("completed","true");
            }

            if (newCode.hasProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasError")))
            {
                answ.put("message",getErrorString(newCode,studentID).get(Language.RU));
            }

            ArrayList<String> properties = new ArrayList<>();
            properties.add("hasCurrentParamNum");
            properties.add("hasCurrentLexemNum");



            for (String propertyName: properties)
            {
                if (newCodeLastLexem.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+propertyName)))
                {
                    int paramNum = newCodeLastLexem.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+propertyName)).getInt();
                    lexem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+propertyName), inf.createTypedLiteral(paramNum));
                }
            }




        }
        infModel.toString();
        answ.put("code", getLexemSequenceString(code));

        return answ;
    }

    private String getLexemSequenceString(Resource code)
    {
        String res = "";
        Resource lexem = getFirstLexemOfPrototypeCode(code);
        if (lexem == null)
            return "";
        if (lexem != null)
            res = lexem.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#value")).getLiteral().getString();
        Resource nextLexem = nextLexemOf(lexem);

        while (nextLexem!=null)
        {
            lexem = nextLexem;
            res += " " + lexem.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#value")).getLiteral().getString();
            nextLexem = nextLexemOf(lexem);
        }

        return res;
    }

    private void removeLastLexem(Resource code)
    {
        ArrayList<Resource> lexems = new ArrayList<>();
        Resource lexem = getFirstLexemOfPrototypeCode(code);
        Resource nextLexem = nextLexemOf(lexem);
        lexems.add(lexem);

        while (nextLexem!=null)
        {
            lexem = nextLexem;
            lexems.add(lexem);
            nextLexem = nextLexemOf(lexem);
        }
        if (lexems.size()>=2) {
            Resource preLastLexem = lexems.get(lexems.size() - 2);
            preLastLexem.removeAll(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem"));
        }
        if (lexems.size() == 1)
        {
            code.removeAll(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"));
        }

    }

    private Resource getLastLexemOfPrototypeCode(Resource code)
    {
        Resource lexem = getFirstLexemOfPrototypeCode(code);
        Resource nextLexem = nextLexemOf(lexem);

        while (nextLexem!=null)
        {
            lexem = nextLexem;
            nextLexem = nextLexemOf(lexem);
        }

        return lexem;
    }

    private void setLastLexemOfPrototypeCode(Resource code, Resource lexem)
    {
        Resource lastLexem = getLastLexemOfPrototypeCode(code);
        setNextLexem(lastLexem, lexem);
    }

    private void setNextLexem(Resource lexem, Resource nextLexem)
    {
        lexem.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem"), nextLexem);
    }

    private Resource nextLexemOf(Resource lexem)
    {
        if (!lexem.hasProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem")))
            return null;
        Resource nextLexem = lexem.getProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem")).getResource();
        return nextLexem;
    }

    private Resource getFirstLexemOfPrototypeCode(Resource code)
    {
        if (!code.hasProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem")))
            return null;
        return code.getProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem")).getResource();
    }

    private void setFirstLexemToPrototypeCode(Resource code, Resource lexem)
    {
        code.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"), lexem);
    }

    public Resource createLexemByTypeAndName(String lexemType, String lexemValue)
    {
        Individual lexem = inf.createIndividual(inf.createResource());
        lexem.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+lexemType));
        lexem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#value"), inf.createTypedLiteral(lexemValue));
        return lexem;
    }

    public Resource getStudentsPrototypeCode(String studentID)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "PREFIX lo: <http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#> " +
                "SELECT ?code WHERE " +
                "{" +
                "?student a so:Student . " +
                "?student so:hasID \"" + studentID + "\" . " +
                "?code a lo:PrototypeCode . " +
                "?code so:ofStudent ?student . " +
                "} ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext())
        {
            return rs.next().get("?code").asResource();
        }
        return createPrototypeCodeForStudent(studentID);
    }

    private Resource createPrototypeCodeForStudent(String studentID)
    {
        Individual code = inf.createIndividual(inf.createResource());
        Resource student = addStudent(studentID,null);
        code.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#ofStudent"), student);
        code.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#PrototypeCode"));
        return code;
    }

    private Resource getParameter(String paramName, String studentID)
    {
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?param WHERE"+
                "{" +
                "?param a po:ParameterChoose ."+
                "?param so:ofStudent ?student ."+
                "?param po:name \""+paramName+"\" ."+
                "?student so:hasID \""+studentID+"\" ."+
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            return qs.get("?param").asResource();
        }
        return null;
    }

    private Resource getParameterDomainType(String paramName, String studentID)
    {
        Resource domainType = null;
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?dt WHERE"+
                "{" +
                "?param a po:ParameterChoose ."+
                "?param so:ofStudent ?student ."+
                "?param po:name \""+paramName+"\" ."+
                "?param po:chosenComponent ?comp ." +
                "?comp po:hasDomainType ?dt ." +
                "?student so:hasID \""+studentID+"\" ."+
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            return qs.get("?dt").asResource();
        }
        return domainType;
    }

    private Resource getReturnValueDomainType(String studentID)
    {
        Resource domainType = null;
        String queryString = "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?dt WHERE"+
                "{" +
                "?param a po:ReturnValueChoose ."+
                "?param so:ofStudent ?student ."+
                "?param po:chosenComponent ?comp ." +
                "?comp po:hasDomainType ?dt ." +
                "?student so:hasID \""+studentID+"\" ."+
                "}";
        Query query = QueryFactory.create(queryString);
        QueryExecution qExec = QueryExecutionFactory.create(query, inf);
        ResultSet rs = qExec.execSelect();
        if (rs.hasNext()) {
            QuerySolution qs = rs.next();
            return qs.get("?dt").asResource();
        }
        return domainType;
    }

    private HashMap<Language,String> getErrorString(Resource answer, String studentID)
    {
        HashSet<RDFNode> classes = getErrorClasses(answer);
        HashMap<String, RDFNode> ontClasses = new HashMap<>();
        HashMap<Language,String> messages = new HashMap<>();
        String[] classNames = new String[]{"CorrectInput","CorrectOutput","CorrectUpdatable","IncorrectInput","IncorrectOutput","IncorrectUpdatable",
                "CantReturn", "FewReturnValues", "InputParameterForOutputComponent", "UpdatableParameterForOutputComponent", "UpdatableParameterForInputComponent", "OutputParameterForInputComponent", "InputParameterForUpdatableComponent", "OutputParameterForUpdatableComponent",
                "ElementAlreadyDefined", "LongPhrase", "PhraseDoesntContainElements", "PhrasePartlyDescribesElement","PhraseDoesntDescribeElement",
                "CollectionForScalar","EntityForScalar","ExcessType","InputParameterByPointer","IntegerTypeForRealNumber","NotEnoughtType","OutputParameterByValue","RealTypeForInteger","ReturnPointer","ScalarForCollection","ScalarForEntity","ReturnPointer",
                "CommaAfterAllParameters","FunctionNameExpected","IncorrectFinishOfParamList","IncorrectFinishOfPrototype","IncorrectLexemOfReturnType","IncorrectLexemParamType","IncorrectNameOfParam","IncorrectParamSeparator","IncorrectStartOfParamList","IncorrectStartOfReturnType","IncorrectStartParamType","NotAllParameters"};

        for (String name: classNames)
        {
            ontClasses.put(name, inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#"+name));
        }


        Resource element = null;
        String elementName = null;
        if (answer.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasElementName"))) {
            elementName = answer.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasElementName")).getLiteral().getString();
            element = findDataElementByName(elementName);
        }

        String mission = null;
        if (element != null) {
            mission = element.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getLiteral().getString();
        }
        String componentMission = "";

        if (answer.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasComponentName"))) {
            String componentName = answer.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasComponentName")).getLiteral().getString();
            Resource component = findComponentByName(studentID, elementName, componentName);
            if (component != null)
                componentMission = component.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getLiteral().getString();
        }


        String lastLexemValue = "";
        int lastLexemParamNumber = 0;
        if (answer.hasProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem")))
        {
            Resource lastLexem = getLastLexemOfPrototypeCode(answer);
            lastLexemValue = lastLexem.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#value")).getLiteral().getString();
            if (lastLexem.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentParamNum")))
            {
                lastLexemParamNumber = lastLexem.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentParamNum")).getInt();
            }
        }

        String parameterName = "";
        if (answer.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#ofParameterWithName")))
        {
            parameterName = answer.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#ofParameterWithName")).getLiteral().getString();
        }

        Resource paramDomainType = getParameterDomainType(parameterName, studentID);
        Resource parameter = getParameter(parameterName, studentID);
        String paramDomainTypeMission = null;
        if (paramDomainType!=null)
        {
            paramDomainTypeMission = paramDomainType.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getString();
        }

        Resource dataType = null;
        String dataTypeString = "";
        if (answer.hasProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasType")))
        {
            dataType = answer.getProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasType")).getResource();
            List<Resource> lexemes = getLexemesForDataType(dataType);

            for (Resource lexeme: lexemes)
            {
                dataTypeString += lexeme.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#value")).getString()+" ";
            }
        }

        String returnValueDomainTypeMission = null;
        if (answer.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#ofReturnValue")))
        {
            Resource returnValueDomainType = getReturnValueDomainType(studentID);
            returnValueDomainTypeMission = returnValueDomainType.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getString();
        }

        String domainTypeMission = (returnValueDomainTypeMission == null) ? paramDomainTypeMission : returnValueDomainTypeMission;

        if (classes.contains(ontClasses.get("CorrectInput")) && classes.contains(ontClasses.get("IncorrectOutput")))
        {
            messages.put(Language.RU, "Разве элемент данных \""+ mission +"\" вычисляется функцией?");
            messages.put(Language.EN, "Should \"" + mission + "\" be calculated by the function?");
        }
        if (classes.contains(ontClasses.get("CorrectInput")) && classes.contains(ontClasses.get("IncorrectUpdatable")))
        {
            messages.put(Language.RU, "Разве элемент данных \""+ mission +"\" вычисляется функцией?");
            messages.put(Language.EN, "Should \"" + mission + "\" be calculated by the function?");
        }
        if (classes.contains(ontClasses.get("CorrectOutput")) && classes.contains(ontClasses.get("IncorrectUpdatable")))
        {
            messages.put(Language.RU, "Разве функция использует исходное значение элемента данных \""+ mission +"\" ?");
            messages.put(Language.EN, "Is \"" + mission + "\" initialized?");
        }
        if (classes.contains(ontClasses.get("CorrectOutput")) && classes.contains(ontClasses.get("IncorrectInput")))
        {
            messages.put(Language.RU, "Разве функция использует исходное значение у элемента данных \""+ mission +"\" ?");
            messages.put(Language.EN, "Is \"" + mission + "\" initialized?");
        }
        if (classes.contains(ontClasses.get("CorrectUpdatable")) && classes.contains(ontClasses.get("IncorrectInput")))
        {
            messages.put(Language.RU, "Разве элемент данных \""+ mission +"\" не вычисляется функцией заново?");
            messages.put(Language.EN, "Should the \""+mission+"\" be calculated by the function?");
        }
        if (classes.contains(ontClasses.get("CorrectUpdatable")) && classes.contains(ontClasses.get("IncorrectOutput")))
        {
            messages.put(Language.RU, "Разве функция не использует исходное значение у элемента данных \""+ mission +"\" ?");
            messages.put(Language.EN, "Is \"" + mission + "\" not calculated?");
        }

        if (classes.contains(ontClasses.get("CantReturn")))
        {
            messages.put(Language.RU, "Разве \""+ mission + "(" + componentMission + ")" +"\" вычисляется?");
            messages.put(Language.EN, "Should \""+mission+"\".\""+componentMission+"\" be calculated by the function?");
        }

        if (classes.contains(ontClasses.get("FewReturnValues")))
        {
            messages.put(Language.RU, "Разве в языке Си разрешено использовать у функции несколько возвращаемых значений?");
            messages.put(Language.EN, "Is it allowed use few return values for a one function?");
        }

        if (classes.contains(ontClasses.get("InputParameterForOutputComponent")))
        {
            messages.put(Language.RU, "Разве \""+ mission + "(" + componentMission + ")" +"\" известен?");
            messages.put(Language.EN, "Is \""+mission+"\".\""+componentMission+"\" initialized?");
        }

        if (classes.contains(ontClasses.get("InputParameterForUpdatableComponent")))
        {
            messages.put(Language.RU, "Разве \""+ mission + "(" + componentMission + ")" +"\" не вычисляется заново?");
            messages.put(Language.EN, "Should \""+mission+"\".\""+componentMission+"\" be calculated by the function?");
        }

        if (classes.contains(ontClasses.get("OutputParameterForUpdatableComponent")))
        {
            messages.put(Language.RU, "Разве \""+ mission + "(" + componentMission + ")" +"\" не используется для вычисления?");
            messages.put(Language.EN, "Is \""+mission+"\".\""+componentMission+"\" used for calculations?");
        }

        if (classes.contains(ontClasses.get("OutputParameterForInputComponent")))
        {
            messages.put(Language.RU, "Разве \""+ mission + "(" + componentMission + ")" +"\" вычисляется?");
            messages.put(Language.EN, "Should \""+mission+"\".\""+componentMission+"\" be calculated by the function?");
        }

        if (classes.contains(ontClasses.get("InputParameterForUpdatableComponent")))
        {
            messages.put(Language.RU, "Разве \""+ mission + "(" + componentMission + ")" +"\" не вычисляется заново?");
            messages.put(Language.EN, "Is \""+mission+"\".\""+componentMission+"\" not used for calculations?");
        }

        if (classes.contains(ontClasses.get("InputParameterForOutputComponent")))
        {
            messages.put(Language.RU, "Разве \""+ mission + "(" + componentMission + ")" +"\" известен?");
            messages.put(Language.EN, "Is \""+mission+"\".\""+componentMission+"\" initialized?");
        }

        if (classes.contains(ontClasses.get("UpdatableParameterForInputComponent")))
        {
            messages.put(Language.RU, "Разве \""+mission+"("+componentMission+")\""+" вычисляется?");
        }

        if (classes.contains(ontClasses.get("UpdatableParameterForOutputComponent")))
        {
            messages.put(Language.RU,"Разве \""+mission+"("+componentMission+")\""+" известен?");
        }

        if (classes.contains(ontClasses.get("ElementAlreadyDefined")))
        {
            messages.put(Language.RU,"Повторно выделен элемент");
        }

        if (classes.contains(ontClasses.get("LongPhrase")))
        {
            messages.put(Language.RU,"Выделена слишком длинная фраза");
        }

        if (classes.contains(ontClasses.get("PhraseDoesntContainElements")))
        {
            messages.put(Language.RU,"Выделена фраза, которая не описывает никакой элемент данных");
        }

        if (classes.contains(ontClasses.get("PhrasePartlyDescribesElement")))
        {
            messages.put(Language.RU,"Выделена слишком короткая фраза - она описывает один элемент данных частично");
        }

        if (classes.contains(ontClasses.get("PhraseDoesntDescribeElement")))
        {
            messages.put(Language.RU,"Выделенная фраза не описывает ни один из элементов данных");
        }

        if (classes.contains(ontClasses.get("CollectionForScalar")))
        {
            messages.put(Language.RU,"Разве можно(рационально) представлять сущность массивом элементов?");
        }

        if (classes.contains(ontClasses.get("EntityForScalar")))
        {
            messages.put(Language.RU,"Разве можно(рационально) представлять одно скалярное значение сущностью со множеством характеристик?");
        }

        if (classes.contains(ontClasses.get("ExcessType")))
        {
            messages.put(Language.RU,"Разве тип данных "+dataTypeString+" не избыточен для представления \""+domainTypeMission+"\"?");
        }

        if (classes.contains(ontClasses.get("InputParameterByPointer")))
        {
            messages.put(Language.RU,"Разве вы хотите изменять параметр "+parameterName+", используя указатель на него?");
        }

        if (classes.contains(ontClasses.get("IntegerTypeForRealNumber")))
        {
            messages.put(Language.RU,"Разве для представления вещественного числа можно использовать целочисленный тип?");
        }

        if (classes.contains(ontClasses.get("NotEnoughtType")))
        {
            messages.put(Language.RU,"Разве для представления \""+domainTypeMission+"\" достаточно использовать тип данных  "+dataTypeString+"?");
        }

        if (classes.contains(ontClasses.get("OutputParameterByValue")))
        {
            messages.put(Language.RU,"Как будете изменять параметр \""+parameterName+"\", если в функцию передается его копия?");
        }

        if (classes.contains(ontClasses.get("RealTypeForInteger")))
        {
            messages.put(Language.RU,"Разве для представления целого числа рационально использовать вещественный тип?");
        }

        if (classes.contains(ontClasses.get("ReturnPointer")))
        {
            messages.put(Language.RU,"Вы пытаетесь вернуть указатель на локальную переменную функции? Разве она не удаляется после выполнения функции?");
        }

        if (classes.contains(ontClasses.get("ScalarForCollection")))
        {
            messages.put(Language.RU,"Разве можно представить коллекцию элементов одним скалярным значением?");
        }

        if (classes.contains(ontClasses.get("ScalarForEntity")))
        {
            messages.put(Language.RU,"Разве можно представить сущность со множеством характеристик одним скалярным значением?");
        }

        //Interaction 6
        if (classes.contains(ontClasses.get("CommaAfterAllParameters")))
        {
            messages.put(Language.RU,"Разве у этой функции есть еще параметры?");
        }

        if (classes.contains(ontClasses.get("FunctionNameExpected")))
        {
            messages.put(Language.RU,"Разве лексема \""+lastLexemValue+"\" является именем функции?");
        }

        if (classes.contains(ontClasses.get("IncorrectFinishOfParamList")))
        {
            messages.put(Language.RU,"Разве  лексема \""+lastLexemValue+"\" завершает список параметров функции?");
        }

        if (classes.contains(ontClasses.get("IncorrectFinishOfPrototype")))
        {
            messages.put(Language.RU,"Разве прототип функции завершается лексемой \""+lastLexemValue+"\" ?");
        }

        if (classes.contains(ontClasses.get("IncorrectLexemOfReturnType")))
        {
            messages.put(Language.RU,"Разве лексема \""+lastLexemValue+"\" является типом возвращаемого значения у функции?");
        }

        if (classes.contains(ontClasses.get("IncorrectLexemParamType")))
        {
            messages.put(Language.RU,"Разве тип "+String.valueOf(lastLexemParamNumber)+" параметра продолжается лексемой \""+lastLexemValue+"\"?");
        }

        if (classes.contains(ontClasses.get("IncorrectNameOfParam")))
        {
            messages.put(Language.RU,"Разве лексема \""+lastLexemValue+"\" является именем функции?");
        }

        if (classes.contains(ontClasses.get("IncorrectParamSeparator")))
        {
            messages.put(Language.RU,"Разве параметры функции разделяются лесемой "+lastLexemValue+" ?");
        }

        if (classes.contains(ontClasses.get("IncorrectStartOfParamList")))
        {
            messages.put(Language.RU,"Разве  лексема \""+lastLexemValue+"\" предваряет список параметров функции?");
        }

        if (classes.contains(ontClasses.get("IncorrectStartOfReturnType")))
        {
            messages.put(Language.RU,"Разве тип возвращаемого значения у функции начинается с лексемы \""+lastLexemValue+"\"?");
        }

        if (classes.contains(ontClasses.get("IncorrectStartParamType")))
        {
            messages.put(Language.RU,"Разве тип "+String.valueOf(lastLexemParamNumber)+" параметра начинается с лексемы \""+lastLexemValue+"\"?");
        }

        if (classes.contains(ontClasses.get("NotAllParameters")))
        {
            messages.put(Language.RU,"Разве у этой функции "+String.valueOf(lastLexemParamNumber)+" параметров?");
        }

        return messages;
    }

    private HashSet<RDFNode> getErrorClasses(Resource answer)
    {
        HashSet<RDFNode> classes = new HashSet<>();
        if (answer.hasProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasError"))) {
            Resource error = answer.getPropertyResourceValue(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasError"));
            StmtIterator stmtit = error.listProperties();

            while (stmtit.hasNext()) {
                Statement stmt = stmtit.next();
                if (stmt.getPredicate().getURI().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
                    classes.add(stmt.getObject());
                }
            }
        }
        return classes;
    }

    private RDFNode getDatatypeClass(Resource datatype)
    {
        //HashSet<RDFNode> classes = new HashSet<>();
            //Resource error = answer.getPropertyResourceValue(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#hasError"));
        StmtIterator stmtit = datatype.listProperties();

        while (stmtit.hasNext()) {
            Statement stmt = stmtit.next();
            if (stmt.getPredicate().getURI().equals("http://www.w3.org/1999/02/22-rdf-syntax-ns#type")) {
                return (stmt.getObject());
            }
        }
        return null;
    }

}
