package com.example.demo.controller.ontology;

import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.Problem;
import com.example.demo.controller.ontology.session.Session;
import com.example.demo.controller.ontology.session.Student;
import com.example.demo.controller.ontology.session.answer.DataElementDirectionChoice;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Objects;

@Testable
public class Test_DataElementDirection {

    @Test
    public void test_InputDataStudentHasChosenInput()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.INPUT_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_InputDataStudentHasChosenOutput()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.OUTPUT_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.CORRECT_INPUT_CHOSEN_OUTPUT));
    }

    @Test
    public void test_InputDataStudentHasChosenUpdatable()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.CHANGED_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.CORRECT_INPUT_CHOSEN_UPDATABLE));
    }

    @Test
    public void test_OutputDataStudentHasChosenInput()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.INPUT_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.CORRECT_OUTPUT_CHOSEN_INPUT));
    }

    @Test
    public void test_OutputDataStudentHasChosenUpdatable()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.CHANGED_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.CORRECT_OUTPUT_CHOSEN_UPDATABLE));
    }

    @Test
    public void test_OutputDataStudentHasChosenOutput()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.OUTPUT_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.OUTPUT_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.NO_ERROR));
    }

    @Test
    public void test_UpdatableDataStudentHasChosenInput()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.CHANGED_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.INPUT_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.CORRECT_UPDATABLE_CHOSEN_INPUT));
    }

    @Test
    public void test_UpdatableDataStudentHasChosenOutput()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.CHANGED_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.OUTPUT_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.CORRECT_UPDATABLE_CHOSEN_OUTPUT));
    }

    @Test
    public void test_UpdatableDataStudentHasChosenUpdatable()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.CHANGED_DATA,2,3);
        com.example.demo.controller.ontology.error.Error e = m.checkAnswer(session,new DataElementDirectionChoice(m.ontologyModel, dataElement, DataElement.Direction.CHANGED_DATA) );
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.NO_ERROR));
    }


}
