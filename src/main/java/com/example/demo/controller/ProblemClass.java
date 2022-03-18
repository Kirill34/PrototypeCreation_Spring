package com.example.demo.controller;

//import com.sun.org.apache.bcel.internal.generic.Select;
//import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
//import com.sun.xml.internal.ws.api.ha.StickyFeature;
//import jdk.nashorn.internal.objects.annotations.Where;
//import com.github.andrewoma.dexx.collection.Pair;
import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.RDFDataMgr;
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
        addProblem();
        calcModel();
    }

    private void addProblem()
    {
        Individual problem = inf.createIndividual(inf.createResource());
        problem.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#Problem"));
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasFullText"), "вычисляет сумму доходов за январь и доходов за февраль");
        problem.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasID"), inf.createTypedLiteral(2));

        Individual domainTypeMoney = inf.createIndividual(inf.createResource());
        domainTypeMoney.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#IntegerNumber"));
        domainTypeMoney.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"Количество денег");
        domainTypeMoney.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#minValue"), inf.createTypedLiteral((long) 0));
        domainTypeMoney.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#maxValue"), inf.createTypedLiteral((long) 100000));


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

        Individual presentation = inf.createIndividual(inf.createResource());
        presentation.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElementPresentation"));
        presentation.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"),"number");
        presentation.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"),"Число");

        Individual moneyCount = inf.createIndividual(inf.createResource());
        moneyCount.setOntClass(inf.getOntClass("http://www.semanticweb.org/problem-ontology#DataElement"));
        moneyCount.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDomainType"), domainTypeMoney);
        moneyCount.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#name"), "money");
        moneyCount.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission"), "Количество денег");

        presentation.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstComponent"), moneyCount);

        data_element_sum.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentation);
        data_element_feb.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentation);
        data_element_jan.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasPresentation"), presentation);

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

    public Resource addStudent(String id)
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

            student.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#solves"), findProblemByID("2"));
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
        Resource student = addStudent(studentID);
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

        Resource student = addStudent(studentID);
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
        Resource student = addStudent(studentID);
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
                "SELECT ?name WHERE " +
                "{" +
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?choose a po:ParameterChoose . " +
                "?choose po:name ?name . " +
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
                    return c;
            }

        }
        return null;
    }

    public String getFullText(String studentID)
    {
        String queryString =
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                        "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?problem ?text WHERE { "+
                        "?student a so:Student . " +
                        "?student so:hasID \"" + studentID + "\" ." +
                        "?student so:solves ?problem ." +
                        "?problem a po:Problem ." +
                        "?problem  po:hasFullText  ?text ." +
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
            System.out.println(qs.get("?text").toString());
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

        Resource student = addStudent(studentID);
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
                "SELECT ?correct ?message WHERE " +
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
            result.put("message", qs.get("?message").asLiteral().getString());
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
        Resource student = addStudent(studentID);
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
            answ.put("message", getErrorString(answExemplar, studentID, Language.EN));
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
        Resource student = addStudent(studentID);
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
        Resource student = addStudent(studentID);
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
            answ.put("message", getErrorString(answExemplar, studentID, Language.EN) );

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
        Resource student = addStudent(studentID);
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
                        "SELECT ?correct ?message WHERE " +
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
            answ.put("message", qs.get("?message").asLiteral().getString());

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
            param.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDataType"), inf.getIndividual("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+typeName));

            Resource lexemType = createLexemByTypeAndName("IntLexem","int");
            lexemType.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentLexemNum"),inf.createTypedLiteral(1));
            Resource lexemParamName = createLexemByTypeAndName("ParameterNameLexem",parameterName);
            lexemType.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasNextLexem"),lexemParamName);
            lexemParamName.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasCurrentLexemNum"),inf.createTypedLiteral(2));
            param.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#hasFirstLexem"),lexemType);

        }
    }

    public HashMap<String, String> chooseReturnValuetype(String studentID, String typeName)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID);
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
                        "SELECT ?correct ?message WHERE " +
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
            answer.addProperty(inf.getDatatypeProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#isCorrectAnswer"),inf.createTypedLiteral(s));
            answ.put("message", qs.get("?message").asLiteral().getString());

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
            rv.addProperty(inf.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasDataType"), inf.getIndividual("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#"+typeName));
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
                "SELECT  DISTINCT ?param ?problem WHERE"+
                "{"+
                "?student a so:Student . " +
                " ?student so:hasID \""+studentID+"\" . " +
                "?param a po:ParameterChoose ."+
                "?param so:ofStudent ?student ."+
                "?student so:solves ?problem ." +
                "?problem po:hasData ?el ."+
                "?presChoose po:chosenPresentation ?pres ."+
                "?presChoose so:ofStudent ?student ."+
                "}";
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

    public List<HashMap<String,String>> getLexemesForPrototype(String studentID)
    {
        generateParameterSequence(studentID);
        generateLexemesForAlldatatypes(studentID);
        List<HashMap<String,String>> lexemes = new ArrayList<>();

        //Function name lexeme
        HashMap<String,String> funcNameLexem = new HashMap<>();
        funcNameLexem.put("type","FunctionNameLexem");
        funcNameLexem.put("value","funcName");
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
        intLexem.put("type","");
        intLexem.put("value","int");

        lexemes.add(intLexem);




        return lexemes;
    }

    public List<HashMap<String,String>> getParamList(String studentID)
    {
        List<HashMap<String,String>> paramList = new ArrayList<>();
        String queryString = "prefix rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"+
                "prefix rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                "PREFIX so: <http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#> " +
                "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "PREFIX lo: <http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#>" +
                "SELECT ?name ?subclass WHERE"+
                "{" +
                    "?param a po:ParameterChoose ."+
                    "?param a ?subclass ."+
                    "?subclass rdfs:subClassOf* po:ParameterChoose."+
                    "?param po:name ?name ."+
                    "?param so:ofStudent ?student ."+
                    "?student so:hasID \""+studentID+"\" ."+
                    "FILTER (?subclass != po:ParameterChoose) " +

                "}";
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
            paramList.add(paramDescription);
        }
        return paramList;
    }

    public HashMap<String, String> removeLastLexemFromPrototypeCode(String studentID)
    {
        HashMap<String, String> answ = new HashMap<String, String>();
        Resource student = addStudent(studentID);
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
        Resource student = addStudent(studentID);
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
            answ.put("message", qs.get("?message").asLiteral().getString());
            answ.put("correct", (qs.get("?correct").asLiteral().getInt()==1) ? "true" : "false" );

            Resource newCode = qs.get("?code").asResource();
            Resource newCodeLastLexem = getLastLexemOfPrototypeCode(newCode);

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
        Resource student = addStudent(studentID);
        code.addProperty(inf.getObjectProperty("http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#ofStudent"), student);
        code.addOntClass(inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#PrototypeCode"));
        return code;
    }

    private String getErrorString(Resource answer, String studentID, Language language)
    {
        HashSet<RDFNode> classes = getErrorClasses(answer);
        HashMap<String, RDFNode> ontClasses = new HashMap<>();
        String[] classNames = new String[]{"CorrectInput","CorrectOutput","CorrectUpdatable","IncorrectInput","IncorrectOutput","IncorrectUpdatable","CantReturn", "FewReturnValues", "InputParameterForOutputComponent", "UpdatableParameterForOutputComponent", "UpdatableParameterForInputComponent", "OutputParameterForInputComponent", "InputParameterForUpdatableComponent", "OutputParameterForUpdatableComponent"};

        for (String name: classNames)
        {
            ontClasses.put(name, inf.getOntClass("http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#"+name));
        }


        String elementName = answer.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasElementName")).getLiteral().getString();
        Resource element = findDataElementByName(elementName);


        assert element != null;
        String mission = element.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getLiteral().getString();
        String componentMission = "";

        if (answer.hasProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasComponentName"))) {
            String componentName = answer.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#hasComponentName")).getLiteral().getString();
            Resource component = findComponentByName(studentID, elementName, componentName);
            if (component != null)
                componentMission = component.getProperty(inf.getDatatypeProperty("http://www.semanticweb.org/problem-ontology#mission")).getLiteral().getString();
        }
        if (classes.contains(ontClasses.get("CorrectInput")) && classes.contains(ontClasses.get("IncorrectOutput")))
        {
            if (language == Language.RU)
                return "Разве "+ mission +" вычисляется?";
            if (language == Language.EN)
                return "Should \"" + mission + "\" be calculated by the function?";
        }
        if (classes.contains(ontClasses.get("CorrectInput")) && classes.contains(ontClasses.get("IncorrectUpdatable")))
        {
            if (language == Language.RU)
                return "Разве "+ mission +" вычисляется?";
            if (language == Language.EN)
                return "Should \"" + mission + "\" be calculated by the function?";
        }
        if (classes.contains(ontClasses.get("CorrectOutput")) && classes.contains(ontClasses.get("IncorrectUpdatable")))
        {
            if (language == Language.RU)
                return "Разве "+ mission +" известен?";
            if (language == Language.EN)
                return "Is \"" + mission + "\" initialized?";
        }
        if (classes.contains(ontClasses.get("CorrectOutput")) && classes.contains(ontClasses.get("IncorrectInput")))
        {
            if (language == Language.RU)
                return "Разве "+ mission +" известен?";
            if (language == Language.EN)
                return "Is \"" + mission + "\" initialized?";
        }
        if (classes.contains(ontClasses.get("CorrectUpdatable")) && classes.contains(ontClasses.get("IncorrectInput")))
        {
            if (language == Language.RU)
                return "Разве "+ mission +" не вычисляется заново?";
            if (language == Language.EN)
                return  "Should the \""+mission+"\" be calculated by the function?";
        }
        if (classes.contains(ontClasses.get("CorrectUpdatable")) && classes.contains(ontClasses.get("IncorrectOutput")))
        {
            if (language == Language.RU)
                return "Разве "+ mission +" не вычисляется заново?";
            if (language == Language.EN)
                return  "Is \"" + mission + "\" not calculated?";
        }

        if (classes.contains(ontClasses.get("CantReturn")))
        {
            if (language == Language.RU)
                return "Разве "+ mission + "(" + componentMission + ")" +" вычисляется?";
            if (language == Language.EN)
                return "Should \""+mission+"\".\""+componentMission+"\" be calculated by the function?";
        }

        if (classes.contains(ontClasses.get("FewReturnValues")))
        {
            if (language == Language.RU)
                return "Разве в языке Си разрешено использовать у функции несколько возвращаемых значений?";
            if (language == Language.EN)
                return "Is it allowed use few return values for a one function?";
        }

        if (classes.contains(ontClasses.get("InputParameterForOutputComponent")))
        {
            if (language == Language.RU)
                return "Разве "+ mission + "(" + componentMission + ")" +" известен?";
            if (language == Language.EN)
                return "Is \""+mission+"\".\""+componentMission+"\" initialized?";
        }

        if (classes.contains(ontClasses.get("InputParameterForUpdatableComponent")))
        {
            if (language == Language.RU)
                return "Разве "+ mission + "(" + componentMission + ")" +" вычисляется?";
            if (language == Language.EN)
                return "Should \""+mission+"\".\""+componentMission+"\" be calculated by the function?";
        }

        if (classes.contains(ontClasses.get("OutputParameterForUpdatableComponent")))
        {
            if (language == Language.RU)
                return "Разве "+ mission + "(" + componentMission + ")" +" не используется для вычисления?";
            if (language == Language.EN)
                return  "Is \""+mission+"\".\""+componentMission+"\" used for calculations?";
        }

        if (classes.contains(ontClasses.get("OutputParameterForInputComponent")))
        {
            if (language == Language.RU)
                return "Разве "+ mission + "(" + componentMission + ")" +" вычисляется?";
            if (language == Language.EN)
                return "Should \""+mission+"\".\""+componentMission+"\" be calculated by the function?";
        }

        if (classes.contains(ontClasses.get("InputParameterForUpdatableComponent")))
        {
            if (language == Language.RU)
                return "Разве "+ mission + "(" + componentMission + ")" +"не используется для вычислений?";
            if (language == Language.EN)
                return "Is \""+mission+"\".\""+componentMission+"\" not used for calculations?";
        }

        if (classes.contains(ontClasses.get("InputParameterForOutputComponent")))
        {
            if (language == Language.RU)
                return "Разве "+ mission + "(" + componentMission + ")" +" известен?";
            if (language == Language.EN)
                return "Is \""+mission+"\".\""+componentMission+"\" initialized?";
        }

        return "Описание ошибки";
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
