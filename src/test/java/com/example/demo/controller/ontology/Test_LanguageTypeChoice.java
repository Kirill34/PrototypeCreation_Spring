package com.example.demo.controller.ontology;

import com.example.demo.controller.ontology.domaintypes.Char;
import com.example.demo.controller.ontology.domaintypes.DomainType;
import com.example.demo.controller.ontology.domaintypes.FloatNumber;
import com.example.demo.controller.ontology.domaintypes.IntegerNumber;
import com.example.demo.controller.ontology.domaintypes.List;
import com.example.demo.controller.ontology.language.types.*;
import com.example.demo.controller.ontology.language.types.Float;
import com.example.demo.controller.ontology.language.types.Long;
import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.DataElementImplementation;
import com.example.demo.controller.ontology.problem.Problem;
import com.example.demo.controller.ontology.session.Session;
import com.example.demo.controller.ontology.session.SessionComponent;
import com.example.demo.controller.ontology.session.Student;
import com.example.demo.controller.ontology.session.answer.Answer;
import com.example.demo.controller.ontology.session.answer.DataElementImplementationChoice;
import com.example.demo.controller.ontology.session.answer.LanguageTypeChoice;
import com.example.demo.controller.ontology.session.answer.TransferMethodChoice;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.Objects;

@Testable
public class Test_LanguageTypeChoice {


    @Test
    public void test_ExcessLengthForArray()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = List.createFixedLengthList(m.ontologyModel, 3);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Array(m.ontologyModel, new Int(m.ontologyModel), 4));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.EXCESS_ARRAY_LENGTH));
    }

    @Test
    public void test_NotEnoughLengthForArray()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = List.createFixedLengthList(m.ontologyModel, 4);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Array(m.ontologyModel, new Int(m.ontologyModel), 3));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NOT_ENOUGH_ARRAY_LENGTH));
    }

    @Test
    public void test_CorrectTypeForArray()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = List.createFixedLengthList(m.ontologyModel, 3);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Array(m.ontologyModel, new Int(m.ontologyModel), 3));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_CorrectTypeForChar()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new Char(m.ontologyModel);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new com.example.demo.controller.ontology.language.types.Char(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_NumberTypeForChar()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new Char(m.ontologyModel);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NUMBER_TYPE_FOR_CHAR));
    }

    @Test
    public void test_CorrectTypeForInteger()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_InputParameterByPointer()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Pointer(m.ontologyModel, new Int(m.ontologyModel)));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.INPUT_PARAMETER_BY_POINTER));
    }

    @Test
    public void test_OutputParameterByPointer()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Pointer(m.ontologyModel, new Int(m.ontologyModel)));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_CorrectTypeForReturnValue()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_NotEnoughType()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20000000000L);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NOT_ENOUGH_TYPE));
    }

    @Test
    public void test_NotEnoughTypeForTwoBorders()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, -20000000000L,20000000000L);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NOT_ENOUGH_TYPE));
    }

    @Test
    public void test_RealTypeForIntegerNumber()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, -20000000000L,20000000000L);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Float(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.REAL_TYPE_FOR_INTEGER));
    }

    @Test
    public void test_ExcessType()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Long(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.EXCESS_TYPE));
    }

    @Test
    public void test_IntegerTypeForRealNumber()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new FloatNumber(m.ontologyModel, 10,20);
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Long(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.INTEGER_TYPE_FOR_REAL));
    }

    @Test
    public void test_OutputParameterByValue()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        System.out.println(e2.ontClass);
        System.out.println(OntologyClasses.Error.OUTPUT_PARAMETER_BY_VALUE);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.OUTPUT_PARAMETER_BY_VALUE));
    }
}
