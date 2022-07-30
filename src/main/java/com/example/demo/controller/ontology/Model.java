package com.example.demo.controller.ontology;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.riot.RDFDataMgr;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.jena.ontology.OntModelSpec.OWL_MEM_MICRO_RULE_INF;

public class Model {

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

    protected org.apache.jena.rdf.model.Model model = null;

    /**
     * Онтологическая модель
     */
    protected OntModel ontologyModel = null;

    /**
     * Задачи на написание прототипа функции
     */
    protected List<Problem> problemList = new ArrayList<>();

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


        /*
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

         */
        ontologyModel = ModelFactory.createOntologyModel( OWL_MEM_MICRO_RULE_INF, model);
    }

    public Problem addProblem(int id, String text, String notice, String funcName)
    {
        Problem p = new Problem(ontologyModel,id, text,notice, funcName);
        problemList.add(p);
        return p;
    }


}
