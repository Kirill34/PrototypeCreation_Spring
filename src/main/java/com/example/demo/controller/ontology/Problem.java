package com.example.demo.controller.ontology;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Literal;

import java.util.List;
import java.util.Set;

public class Problem extends AbstractOntologyObject{

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
     * Входные элементы данных
     */
    private Set<DataElement> inputDataElements;

    /**
     * Выходные элементы данных
     */
    private Set<DataElement> outputDataElements;

    /**
     * Обновляемые элементы данных
     */
    private Set<DataElement> changedDataElements;

    /**
     * Конструктор
     * @param text Текст задачи
     * @param notice Примечание к задаче
     */
    public Problem(OntModel ontModel, int id, String text, String notice, String funcName)
    {
        super(ontModel, OntologyClasses.PROBLEM);
        this.ontologyModel = ontModel;
        ontClass=OntologyClasses.PROBLEM;
        this.setLiteralProperty(LiteralProperties.HAS_ID, ontologyModel.createTypedLiteral(id));
        this.setLiteralProperty(LiteralProperties.HAS_FULL_TEXT, ontologyModel.createLiteral(text));
        this.setLiteralProperty(LiteralProperties.HAS_NOTICE, ontologyModel.createLiteral(notice));
        this.setLiteralProperty(LiteralProperties.HAS_FUNC_NAME, ontologyModel.createLiteral(funcName));
        this.id=id;
        this.text=text;
        this.notice=notice;
        this.funcName = funcName;
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
}
