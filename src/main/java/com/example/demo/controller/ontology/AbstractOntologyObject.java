package com.example.demo.controller.ontology;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.*;
import org.apache.jena.tdb.store.Hash;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractOntologyObject {
    /**
     * Онтологическая модель
     */
    protected OntModel ontologyModel;

    /**
     * Этот объект в онтологии
     */
    protected Resource ontResource;

    /**
     * Онтологический класс
     */
    protected String ontClass;

    /**
     * Связанные объекты
     */
    private HashMap<String, List<AbstractOntologyObject>> objectMap = new HashMap<>();


    public AbstractOntologyObject(OntModel ontModel, String ontClass, Resource resource)
    {
        this.ontClass = ontClass;
        this.ontologyModel = ontModel;
        this.ontResource=resource;
    }

    public AbstractOntologyObject(OntModel ontModel, String ontClass)
    {
        this.ontClass = ontClass;
        this.ontologyModel = ontModel;
        Individual r = ontologyModel.createIndividual(ontologyModel.createResource());
        r.setOntClass(ontologyModel.getOntClass(ontClass));
        ontResource = r;
    }

    /**
     * Возвращает объект, соответствующий объектному свойству
     * @param objectPropertyName Полное имя объектного свойства
     * @return Онтологический объект
     */
    protected List<AbstractOntologyObject> getObjectProperty(String objectPropertyName)
    {
        return objectMap.get(objectPropertyName);
    }

    /**
     * Извлекает свойство-литерал из модели
     * @param literalPropertyName  Полное имя свойства-литерала
     * @return Значение свойства-литерала
     */
    protected Literal getLiteralProperty(String literalPropertyName)
    {
        return ontResource.getProperty(ontologyModel.getDatatypeProperty(literalPropertyName)).getLiteral();
    }

    /**
     * Задать свойство-литерал объекту
     * @param literalPropertyName Имя свойства-литерала
     * @param value Значение свойства-литерала
     */
    protected void setLiteralProperty(String literalPropertyName, Literal value)
    {
        ontResource.addProperty(ontologyModel.getDatatypeProperty(literalPropertyName),value);
    }

    /**
     * Задать объектное свойство
     * @param objectPropertyName Имя объектного свойства
     * @param object Объект
     */
    protected void setObjectProperty(String objectPropertyName, AbstractOntologyObject object)
    {
        ontResource.addProperty(ontologyModel.getObjectProperty(objectPropertyName),object.ontResource);
        if (!this.objectMap.containsKey(objectPropertyName))
            this.objectMap.put(objectPropertyName, new ArrayList<>());
        this.objectMap.get(objectPropertyName).add(object);
    }
}
