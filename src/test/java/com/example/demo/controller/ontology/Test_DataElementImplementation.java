package com.example.demo.controller.ontology;

import com.example.demo.controller.ontology.domaintypes.*;
import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.DataElementImplementation;
import com.example.demo.controller.ontology.problem.Problem;
import com.example.demo.controller.ontology.session.Session;
import com.example.demo.controller.ontology.session.Student;
import com.example.demo.controller.ontology.session.answer.Answer;
import com.example.demo.controller.ontology.session.answer.DataElementDirectionChoice;
import com.example.demo.controller.ontology.session.answer.DataElementImplementationChoice;
import org.apache.jena.rdf.model.Resource;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.Objects;

@Testable
public class Test_DataElementImplementation {

    @Test
    public void test_NumberImplementationForNumberDomainType()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.NO_ERROR));
        Resource firstComponent = answer.ontResource.getProperty(m.ontologyModel.getObjectProperty(ObjectProperties.Session.HAS_FIRST_SESSION_COMPONENT)).getResource();
        assert (firstComponent != null);
        assert (!firstComponent.hasProperty(m.ontologyModel.getObjectProperty(ObjectProperties.Session.HAS_NEXT_SESSION_COMPONENT)));
    }

    @Test
    public void test_ScalarImplementationForNumberDomainType()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);

        DomainType domainType1 = new Collection(m.ontologyModel);
        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType1);

        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.IMPLEMENTATION_COLLECTION_FOR_SCALAR));

        assert (!answer.ontResource.hasProperty(m.ontologyModel.getObjectProperty(ObjectProperties.Session.HAS_FIRST_SESSION_COMPONENT)));
    }

    @Test
    public void test_UnfixedCollectionLength()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = List.createNotFixedLengthList(m.ontologyModel);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);

        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.UNDEFINED_COLLECTION_SIZE));
    }

    @Test
    public void test_FixedCollectionLength()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = List.createFixedLengthList(m.ontologyModel, 5);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);

        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_EntityFieldsAsScalars()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);

        DomainType domainType_Day = new IntegerNumber(m.ontologyModel, 1,31);
        DomainType domainType_Month = new IntegerNumber(m.ontologyModel,1,12);
        DomainType domainType_Year = new IntegerNumber(m.ontologyModel,1900,2100);

        ArrayList<DataElement> fields = new ArrayList<>();
        fields.add(new DataElement(m.ontologyModel, "day","number of day", domainType_Day));
        fields.add(new DataElement(m.ontologyModel, "month","number of month", domainType_Month));
        fields.add(new DataElement(m.ontologyModel,"year","number of year",domainType_Year));

        DomainType domainType = new Entity(m.ontologyModel,"date", fields);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);

        DataElement componentDay = new DataElement(m.ontologyModel, "day","day", domainType_Day);
        DataElement componentMonth = new DataElement(m.ontologyModel,"month","month",domainType_Month);
        DataElement componentYear = new DataElement(m.ontologyModel, "year" ,"year",domainType_Year);

        ArrayList<DataElement> components = new ArrayList<>();
        components.add(componentDay);
        components.add(componentMonth);
        components.add(componentYear);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.NO_ERROR));
    }
}
