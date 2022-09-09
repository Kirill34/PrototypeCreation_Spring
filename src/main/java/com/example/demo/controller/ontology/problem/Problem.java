package com.example.demo.controller.ontology.problem;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.LiteralProperties;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.github.andrewoma.dexx.collection.Pair;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Resource;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Problem extends AbstractOntologyObject {

    /**
     * ID задачи
     */
    private int id;

    /**
     * Текст задачи
     */
    private String text;

    /**
     * Примечание к задаче
     */
    private String notice;

    /**
     * Имя функции
     */
    private String funcName;

    /**
     * Фразы отсортированы
     */
    private boolean phrasesAreSorted;

    /**
     * Входные элементы данных
     */
    private Set<DataElement> inputDataElements = new HashSet<>();

    /**
     * Выходные элементы данных
     */
    private Set<DataElement> outputDataElements = new HashSet<>();

    /**
     * Обновляемые элементы данных
     */
    private Set<DataElement> changedDataElements = new HashSet<>();

    private List<DataElement> dataElements = new ArrayList<>();

    /**
     * Фразы, описывающие элементы данных
     */
    private Set<Phrase> phrases = new HashSet<>();

    /**
     * Конструктор
     * @param text Текст задачи
     * @param notice Примечание к задаче
     */
    public Problem(OntModel ontModel, int id, String text, String notice, String funcName)
    {
        super(ontModel, OntologyClasses.Problem.PROBLEM);
        this.ontologyModel = ontModel;
        this.setLiteralProperty(LiteralProperties.Problem.HAS_ID, ontologyModel.createTypedLiteral(id));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_FULL_TEXT, ontologyModel.createLiteral(text));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_NOTICE, ontologyModel.createLiteral(notice));
        this.setLiteralProperty(LiteralProperties.Problem.HAS_FUNC_NAME, ontologyModel.createLiteral(funcName));
        this.id=id;
        this.text=text;
        this.notice=notice;
        this.funcName = funcName;
    }

    public void sortPhrases()
    {
        if (!phrasesAreSorted)
        {
            setPhraseOrder();
        }
    }

    private void setPhraseOrder()
    {
        String queryString = "PREFIX po: <http://www.semanticweb.org/problem-ontology#> " +
                "SELECT ?lb ?phrase" +
                " WHERE { "+
                "?p a po:Problem ." +
                "?p po:hasID " + this.id +" ." +
                "?p po:hasData ?de . "+
                "?phrase po:describe ?de ."+
                "?phrase po:hasLeftBorder ?lb ."+
                " } ORDER BY ASC(?lb)";

        //String queryString = "SELECT ?problem ?parameter WHERE {?problem <http://www.semanticweb.org/problem-ontology#hasParameter> ?parameter }";
        Query query = QueryFactory.create(queryString);
        //InfModel infModel = ModelFactory.createInfModel(reasoner, inf);
        QueryExecution qExec = QueryExecutionFactory.create(query, ontologyModel);
        ResultSet rs = qExec.execSelect();

        List<Resource> phrases = new ArrayList<>();

        while ( (rs.hasNext()) ) {
            phrases.add(rs.next().get("?phrase").asResource());
        }
        if (!phrases.isEmpty())
        {
            this.ontResource.addProperty(ontologyModel.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasFirstPhrase"),phrases.get(0));
            for (int i=1; i<phrases.size(); i++)
            {
                phrases.get(i-1).addProperty(ontologyModel.getObjectProperty("http://www.semanticweb.org/problem-ontology#hasRighterPhrase"),phrases.get(i));
            }
        }
    }

    /**
     * Получить текст задачи
     * @return Текст задачи
     */
    public String getText()
    {
        return text;
    }

    /**
     * Получить примечание к задаче
     * @return Примечание к задаче
     */
    public String getNotice() {
        return notice;
    }

    /**
     * Получить имя функции
     * @return Имя функции
     */
    public String getFuncName() {
        return funcName;
    }

    public void addDataElement(DataElement dataElement, DataElement.Direction direction, int leftBorder, int rightBorder)
    {
        Phrase phrase = new Phrase(ontologyModel, leftBorder, rightBorder, dataElement);
        phrases.add(phrase);

        if (dataElements.isEmpty())
            this.setObjectProperty(ObjectProperties.Problem.HAS_FIRST_DATA_ELEMENT,dataElement);
        else
            dataElements.get(dataElements.size()-1).setSiblingDataElement(dataElement);

        dataElements.add(dataElement);
        switch (direction)
        {
            case INPUT_DATA:
                this.inputDataElements.add(dataElement);
                this.setObjectProperty(ObjectProperties.Problem.HAS_INPUT_DATA,dataElement);
                break;
            case OUTPUT_DATA:
                this.outputDataElements.add(dataElement);
                this.setObjectProperty(ObjectProperties.Problem.HAS_OUTPUT_DATA,dataElement);
                break;
            case CHANGED_DATA:
                this.changedDataElements.add(dataElement);
                this.setObjectProperty(ObjectProperties.Problem.HAS_CHANGED_DATA,dataElement);
                break;
        }
    }
}
