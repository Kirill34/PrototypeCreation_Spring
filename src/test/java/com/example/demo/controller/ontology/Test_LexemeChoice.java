package com.example.demo.controller.ontology;

import com.example.demo.controller.ontology.domaintypes.DomainType;
import com.example.demo.controller.ontology.domaintypes.IntegerNumber;
import com.example.demo.controller.ontology.language.lexemes.*;
import com.example.demo.controller.ontology.language.types.Int;
import com.example.demo.controller.ontology.language.types.Pointer;
import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.DataElementImplementation;
import com.example.demo.controller.ontology.problem.Problem;
import com.example.demo.controller.ontology.session.Session;
import com.example.demo.controller.ontology.session.SessionComponent;
import com.example.demo.controller.ontology.session.Student;
import com.example.demo.controller.ontology.session.answer.*;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.ArrayList;
import java.util.Objects;

@Testable
public class Test_LexemeChoice {

    @Test
    public void test_CorrectBeginForVoidFunction()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_CorrectBeginForNoVoidFunction()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_CorrectNameForNoVoidFunction()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel, "date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_OpenBracketAfterNameForFunctionWithoutParameters()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel, "date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceOpenBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session, lexemeChoiceOpenBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_IncorrectBeginForNoVoidFunction()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.INCORRECT_START_OF_RETURN_TYPE));
    }

    @Test
    public void test_CorrectBeginForVoidFunctionAndCorrectFunctionName()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_CorrectBeginForVoidFunctionAndIncorrectFunctionName()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"dat_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.EXPECTED_FUNCTION_NAME));
    }

    @Test
    public void test_IncorrectBeginForVoidFunction()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.INCORRECT_LEXEM_OF_RETURN_TYPE));
    }


    @Test
    public void test_CorrectBracketAfterFunctionName()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));
    }



    @Test
    public void test_BeginOfParameterTypeAfterOpenBracket()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_СorrectParameterNameAfterParameterType()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "test_t"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_СorrectCloseBracketAfterOneParameter()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "test_t"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceCloseBracket = new LexemeChoice(m.ontologyModel, new CloseBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e8 = m.checkAnswer(session, lexemeChoiceCloseBracket);
        assert (Objects.equals(e8.ontClass, OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_СommaAfterAllParameters()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "test_t"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceCloseBracket = new LexemeChoice(m.ontologyModel, new CommaLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e8 = m.checkAnswer(session, lexemeChoiceCloseBracket);
        assert (Objects.equals(e8.ontClass, OntologyClasses.Error.COMMA_AFTER_ALL_PARAMS));
    }


    @Test
    public void test_СommaBetweenParameters()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);

        for (int i=0; i<2; i++) {

            DataElement dataElement = new DataElement(m.ontologyModel, "test"+String.valueOf(i), "test", domainType);
            p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA, 2, 3);

            DataElement component = new DataElement(m.ontologyModel, "t", "t", domainType);
            ArrayList<DataElement> components = new ArrayList<>();
            components.add(component);
            DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
            Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
            com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        }

        assert (m.sessionComponentList.size()==2);

        for (int i=0; i<2; i++) {
            SessionComponent sessionComponent = m.sessionComponentList.get(i);

            TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
            com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
            assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));

            LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
            com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
            assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
        }

        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "test0_t"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceCloseBracket = new LexemeChoice(m.ontologyModel, new CommaLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e8 = m.checkAnswer(session, lexemeChoiceCloseBracket);
        assert (Objects.equals(e8.ontClass, OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_CloseBracketAfterNotAllParameters()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);

        for (int i=0; i<2; i++) {

            DataElement dataElement = new DataElement(m.ontologyModel, "test"+String.valueOf(i), "test", domainType);
            p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA, 2, 3);

            DataElement component = new DataElement(m.ontologyModel, "t", "t", domainType);
            ArrayList<DataElement> components = new ArrayList<>();
            components.add(component);
            DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
            Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
            com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        }

        assert (m.sessionComponentList.size()==2);

        for (int i=0; i<2; i++) {
            SessionComponent sessionComponent = m.sessionComponentList.get(i);

            TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
            com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
            assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));

            LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
            com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
            assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
        }

        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "test0_t"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceCloseBracket = new LexemeChoice(m.ontologyModel, new CloseBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e8 = m.checkAnswer(session, lexemeChoiceCloseBracket);
        assert (Objects.equals(e8.ontClass, OntologyClasses.Error.NOT_ALL_PARAMETERS));
    }



    @Test
    public void test_StartOfSecondParameterType()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);

        for (int i=0; i<2; i++) {

            DataElement dataElement = new DataElement(m.ontologyModel, "test"+String.valueOf(i), "test", domainType);
            p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA, 2, 3);

            DataElement component = new DataElement(m.ontologyModel, "t", "t", domainType);
            ArrayList<DataElement> components = new ArrayList<>();
            components.add(component);
            DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
            Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
            com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        }

        assert (m.sessionComponentList.size()==2);

        for (int i=0; i<2; i++) {
            SessionComponent sessionComponent = m.sessionComponentList.get(i);

            TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
            com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
            assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));

            LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
            com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
            assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
        }

        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "test0_t"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceCloseBracket = new LexemeChoice(m.ontologyModel, new CommaLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e8 = m.checkAnswer(session, lexemeChoiceCloseBracket);
        assert (Objects.equals(e8.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt2 = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e9 = m.checkAnswer(session, lexemeChoiceInt2);
        assert (Objects.equals(e9.ontClass,OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_CorrectNameOfSecondParameter()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DomainType domainType = new IntegerNumber(m.ontologyModel, 10,20);

        for (int i=0; i<2; i++) {

            DataElement dataElement = new DataElement(m.ontologyModel, "test"+String.valueOf(i), "test", domainType);
            p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA, 2, 3);

            DataElement component = new DataElement(m.ontologyModel, "t", "t", domainType);
            ArrayList<DataElement> components = new ArrayList<>();
            components.add(component);
            DataElementImplementation implementation = new DataElementImplementation(m.ontologyModel, components);
            Answer answer = new DataElementImplementationChoice(m.ontologyModel, dataElement, implementation);
            com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session, answer);
        }

        assert (m.sessionComponentList.size()==2);

        for (int i=0; i<2; i++) {
            SessionComponent sessionComponent = m.sessionComponentList.get(i);

            TransferMethodChoice transferMethodChoice = new TransferMethodChoice(m.ontologyModel, sessionComponent, TransferMethodChoice.TransferMethod.INPUT_PARAMETER);
            com.example.demo.controller.ontology.error.Error e1 = m.checkAnswer(session, transferMethodChoice);
            assert (Objects.equals(e1.ontClass, OntologyClasses.Error.NO_ERROR));

            LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Int(m.ontologyModel));
            com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
            assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));
        }

        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "test0_t"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceCloseBracket = new LexemeChoice(m.ontologyModel, new CommaLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e8 = m.checkAnswer(session, lexemeChoiceCloseBracket);
        assert (Objects.equals(e8.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt2 = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e9 = m.checkAnswer(session, lexemeChoiceInt2);
        assert (Objects.equals(e9.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName2 = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel,"test1_t"));
        com.example.demo.controller.ontology.error.Error e10 = m.checkAnswer(session, lexemeChoiceParamName2);
        assert (Objects.equals(e10.ontClass,OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_IncorrectParameterNameAfterParameterType()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel, "testt"));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.INCORRECT_NAME_OF_PARAM));
    }


    @Test
    public void test_CorrectTypeOfPointerParameter()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Pointer(m.ontologyModel,new Int(m.ontologyModel)));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new PointerLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));
    }


    @Test
    public void test_IncorrectLexemOfParameterType()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Pointer(m.ontologyModel,new Int(m.ontologyModel)));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParamName = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoiceParamName);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.INCORRECT_LEXEM_OF_PARAM_TYPE));
    }

    @Test
    public void test_CorrectTypeAndNameOfPointerParameter()
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

        LanguageTypeChoice languageTypeChoice = new LanguageTypeChoice(m.ontologyModel, sessionComponent, new Pointer(m.ontologyModel,new Int(m.ontologyModel)));
        com.example.demo.controller.ontology.error.Error e2 = m.checkAnswer(session, languageTypeChoice);
        assert (Objects.equals(e2.ontClass, OntologyClasses.Error.NO_ERROR));


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new IntLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoicePointer = new LexemeChoice(m.ontologyModel, new PointerLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e7 = m.checkAnswer(session, lexemeChoicePointer);
        assert (Objects.equals(e7.ontClass,OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceParameterName = new LexemeChoice(m.ontologyModel, new ParameterNameLexeme(m.ontologyModel,"test_t"));
        com.example.demo.controller.ontology.error.Error e8 = m.checkAnswer(session, lexemeChoiceParameterName);
        assert (Objects.equals(e8.ontClass,OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_IncorrectBeginOfParameterTypeAfterOpenBracket()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new OpenBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceInt = new LexemeChoice(m.ontologyModel, new CharLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e6 = m.checkAnswer(session, lexemeChoiceInt);
        assert (Objects.equals(e6.ontClass,OntologyClasses.Error.INCORRECT_START_OF_PARAM_TYPE));
    }


    @Test
    public void test_IncorrectStartOfParamList()
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


        LexemeChoice lexemeChoice = new LexemeChoice(m.ontologyModel, new VoidLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e3 = m.checkAnswer(session,lexemeChoice);
        assert (Objects.equals(e3.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceName = new LexemeChoice(m.ontologyModel, new FunctionNameLexeme(m.ontologyModel,"date_distance"));
        com.example.demo.controller.ontology.error.Error e4 = m.checkAnswer(session,lexemeChoiceName);
        assert (Objects.equals(e4.ontClass, OntologyClasses.Error.NO_ERROR));

        LexemeChoice lexemeChoiceBracket = new LexemeChoice(m.ontologyModel, new CloseBracketLexeme(m.ontologyModel));
        com.example.demo.controller.ontology.error.Error e5 = m.checkAnswer(session,lexemeChoiceBracket);
        assert (Objects.equals(e5.ontClass, OntologyClasses.Error.INCORRECT_START_OF_PARAM_LIST));
    }


}
