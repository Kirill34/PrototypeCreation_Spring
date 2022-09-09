package com.example.demo.controller.ontology;

import com.example.demo.controller.ontology.domaintypes.DomainType;
import com.example.demo.controller.ontology.domaintypes.Entity;
import com.example.demo.controller.ontology.domaintypes.IntegerNumber;
import com.example.demo.controller.ontology.domaintypes.List;
import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.DataElementImplementation;
import com.example.demo.controller.ontology.problem.Problem;
import com.example.demo.controller.ontology.session.Session;
import com.example.demo.controller.ontology.session.SessionComponent;
import com.example.demo.controller.ontology.session.Student;
import com.example.demo.controller.ontology.session.answer.Answer;
import com.example.demo.controller.ontology.session.answer.DataElementImplementationChoice;
import com.example.demo.controller.ontology.session.answer.TransferMethodChoice;
import org.apache.jena.graph.Triple;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

@Testable
public class Test_TransferMethodChoice {

    @Test
    public void test_InputParameterForOutputComponent()
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

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.INPUT_PARAMETER_FOR_OUTPUT_COMPONENT));
    }

    @Test
    public void test_ChangedParameterForOutputComponent()
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

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.CHANGED_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.UPDATABLE_PARAMETER_FOR_OUTPUT_COMPONENT));
    }

    @Test
    public void test_OutputParameterForInputComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.OUTPUT_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.OUTPUT_PARAMETER_FOR_INPUT_COMPONENT));
    }


    @Test
    public void test_ChangedParameterForInputComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.CHANGED_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.UPDATABLE_PARAMETER_FOR_INPUT_COMPONENT));
    }

    @Test
    public void test_InputParameterForChangedComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.CHANGED_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.INPUT_PARAMETER_FOR_CHANGED_COMPONENT));
    }

    @Test
    public void test_OutputParameterForChangedComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.CHANGED_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.OUTPUT_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.OUTPUT_PARAMETER_FOR_UPDATABLE_COMPONENT));
    }

    @Test
    public void test_InputParameterForInputComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_InputParameterForInputComponent_TwoComponents()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);

        ArrayList<DataElement> fields = new ArrayList<>();
        fields.add(new DataElement(m.ontologyModel, "u","u",domainType));
        fields.add(new DataElement(m.ontologyModel,"o","o",domainType));

        DomainType entity = new Entity(m.ontologyModel,"entity",fields);

        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", entity);
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        DataElement component1 = new DataElement(m.ontologyModel, "m","m", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        components.add(component1);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==2);

        for (int i=0; i<2; i++)
        {
            SessionComponent sessionComponent = m.sessionComponentList.get(0);

            TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
            com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
            assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));
        }

    }

    @Test
    public void test_OutputParameterForOutputComponent()
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

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.OUTPUT_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_ChangedParameterForChangedComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.CHANGED_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.CHANGED_PARAMETER);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_ReturnValueForOutputComponent()
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

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.RETURN_VALUE);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_ReturnValueForOutputComponent_FewReturnValues()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);

        DataElement dataElement1 = new DataElement(m.ontologyModel, "t", "t", domainType);
        p.addDataElement(dataElement1, DataElement.Direction.OUTPUT_DATA, 4,5);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        Answer answer1 = new DataElementImplementationChoice(m.ontologyModel, dataElement1, implementation);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, answer1);

        assert (m.sessionComponentList.size()==2);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.RETURN_VALUE);
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));

        SessionComponent sessionComponent1 = m.sessionComponentList.get(1);

        TransferMethodChoice transferMethodChoice1 = new TransferMethodChoice(m.ontologyModel, sessionComponent1, TransferMethodChoice.TransferMethod.RETURN_VALUE);
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session, transferMethodChoice1);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.FEW_RETURN_VALUES));
    }

    @Test
    public void test_ReturnValueForInputComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.RETURN_VALUE);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.RETURN_VALUE_FOR_INPUT_COMPONENT));
    }

    @Test
    public void test_ReturnValueForChangedComponent()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.CHANGED_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.RETURN_VALUE);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.RETURN_VALUE_FOR_CHANGED_COMPONENT));
    }

    @Test
    public void test_ReturnValueForOutputComponent_Collection()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = List.createFixedLengthList(m.ontologyModel,5);//new IntegerNumber(m.ontologyModel, 10,20);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test", domainType);
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);

        DataElement component = new DataElement(m.ontologyModel, "t","t", domainType);
        ArrayList<DataElement> components = new ArrayList<>();
        components.add(component);
        DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
        Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);

        assert (m.sessionComponentList.size()==1);
        SessionComponent sessionComponent = m.sessionComponentList.get(0);

        TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.RETURN_VALUE);
        com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
        assert (Objects.equals(e1.ontClass, OntologyClasses.Error.CANT_RETURN_COLLECTION));
    }
}
