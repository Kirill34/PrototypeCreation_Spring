package com.example.demo.controller.ontology;

import com.example.demo.controller.ontology.problem.Problem;
import com.example.demo.controller.ontology.session.Session;
import com.example.demo.controller.ontology.session.Student;
import com.example.demo.controller.ontology.session.answer.Answer;
import com.example.demo.controller.ontology.session.answer.DataElementDirectionChoice;
import com.example.demo.controller.ontology.session.answer.DataElementIdentifying;
import model.DataElement;
import org.apache.jena.base.Sys;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.riot.RDFDataMgr;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import static org.apache.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;

public class Model {

    protected static String PROBLEM_ONTOLOGY_FILE = "ProblemOntology.owl";
    protected static String SESSION_ONTOLOGY_FILE = "SessionOntology.owl";
    protected static String LANGUAGE_ONTOLOGY_FILE = "LanguageOntology.owl";
    protected static String ERROR_ONTOLOGY_FILE = "ErrorOntology.owl";

    protected static String DATA_DIRECTION_RULES = "rules/2.rules";
    protected static String ELEMENT_BORDERS_RULES = "rules/1.rules";
    protected static String DATA_PRESENTATION_RULES = "rules/data_presentation.rules";
    protected static String PARAMETERS_RETURNS_RULES = "rules/parameters_returns.rules";
    protected static String TYPES_RULES = "rules/types.rules";
    protected static String PROTOTYPE_RULES = "rules/prototype.rules";

    protected org.apache.jena.rdf.model.Model model = null;

    /**
     * Онтологическая модель
     */
    protected OntModel ontologyModel = null;

    /**
     * Задачи на написание прототипа функции
     */
    protected List<Problem> problemList = new ArrayList<>();

    /**
     * Студенты
     */
    protected List<Student> studentList = new ArrayList<>();

    protected Reasoner[] reasoners = new Reasoner[6];

    /**
     * Прочитать модель из файла
     * @param modelFile Имя файла
     * @return Модель
     */
    protected org.apache.jena.rdf.model.Model readModel(String modelFile)
    {
        // create an empty model
        org.apache.jena.rdf.model.Model model = ModelFactory.createDefaultModel();
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

    public Model()
    {
        org.apache.jena.rdf.model.Model problemModel = readModel(PROBLEM_ONTOLOGY_FILE);
        org.apache.jena.rdf.model.Model sessionModel = readModel(SESSION_ONTOLOGY_FILE);
        org.apache.jena.rdf.model.Model languageModel = readModel(LANGUAGE_ONTOLOGY_FILE);
        org.apache.jena.rdf.model.Model errorModel = readModel(ERROR_ONTOLOGY_FILE);
        model = ModelFactory.createUnion(problemModel, sessionModel);
        model = ModelFactory.createUnion(model, languageModel);
        model = ModelFactory.createUnion(model, errorModel);



        //Ризонер для интеракции 0 "Выделение элементов данных из текста"
        reasoners[0] = createReasonerForInteraction(ELEMENT_BORDERS_RULES);

        //Ризонер для интеракции 1 "Направления элементов данных"
        reasoners[1] = createReasonerForInteraction(DATA_DIRECTION_RULES);
        /*
        //Ризонер для интеракции 2 "Представления элементов данных"
        reasoners[2] = createReasonerForInteraction(DATA_PRESENTATION_RULES);

        //Ризонер для интеракции 3 "Выбор параметров и возвращаемых значений"
        reasoners[3] = createReasonerForInteraction(PARAMETERS_RETURNS_RULES);

        //Ризонер для интерации 4 "Выбор типов параметров и возвращаемого значения"
        reasoners[4] = createReasonerForInteraction(TYPES_RULES);

        //Ризонер для интеракции 5 "Написание прототипа функции"
        reasoners[5] = createReasonerForInteraction(PROTOTYPE_RULES);

         */
        ontologyModel = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, model);
    }

    private Reasoner createReasonerForInteraction(String rulesFile)
    {
        InputStream stream = getClass().getClassLoader().getResourceAsStream(rulesFile);
        String rules = readStream( stream);
        Reasoner reasoner = new GenericRuleReasoner(Rule.parseRules(rules));
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

    public Problem addProblem(int id, String text, String notice, String funcName)
    {
        Problem p = new Problem(ontologyModel,id, text,notice, funcName);
        problemList.add(p);
        return p;
    }


    public Student addStudent(int id)
    {
        Student s = new Student(id, ontologyModel);
        studentList.add(s);
        return s;
    }

    public Session addSession(Student s, Problem p)
    {
        Session session = new Session(ontologyModel, s, p);
        return session;
    }

    public com.example.demo.controller.ontology.error.Error checkAnswer(Session session, Answer answer)
    {
        session.addAnswer(answer);

        if (answer instanceof DataElementIdentifying)
        {
            ontologyModel.write(System.out);
            InfModel infModel = ModelFactory.createInfModel(reasoners[0],ontologyModel);
            org.apache.jena.rdf.model.Model diff = infModel.getDeductionsModel().difference(ontologyModel);
            ontologyModel.add(diff);
            System.out.println(diff.toString());
            return answer.getError();
        }

        if (answer instanceof DataElementDirectionChoice)
        {
            InfModel infModel = ModelFactory.createInfModel(reasoners[1],ontologyModel);
            org.apache.jena.rdf.model.Model diff = infModel.getDeductionsModel().difference(ontologyModel);
            ontologyModel.add(diff);
            return answer.getError();
        }

        return null;
    }


}
