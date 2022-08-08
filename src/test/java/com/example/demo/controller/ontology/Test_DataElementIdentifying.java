package com.example.demo.controller.ontology;

import com.example.demo.controller.Language;
import com.example.demo.controller.ontology.error.Error;
import com.example.demo.controller.ontology.problem.DataElement;
import com.example.demo.controller.ontology.problem.Problem;
import com.example.demo.controller.ontology.session.Session;
import com.example.demo.controller.ontology.session.Student;
import com.example.demo.controller.ontology.session.answer.DataElementIdentifying;
import org.apache.jena.base.Sys;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.util.Objects;

@Testable
public class Test_DataElementIdentifying {

    @Test
    public void test_correctIdentifying()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,2,3));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.NO_ERROR));
        //assert (session.ontResource.getProperty(session.ontologyModel.getObjectProperty(ObjectProperties.Session.HAS_DEFINED_ELEMENT)). == dataElement.ontResource);
    }

    @Test
    public void test_elementIsAlreadyDefined()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,2,3));
        Error e1 = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,2,3));
        System.out.println(e1.getErrorMessage(Language.RU));
        assert(Objects.equals(e1.ontClass, OntologyClasses.Error.ELEMENT_ALREADY_DEFINED));
    }

    @Test
    public void test_phraseDescribesElementPartly_rightBorderInAnswerLessThanExpected()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,2,2));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.PHRASE_DESCRIBES_ELEMENT_PARTLY));
    }

    @Test
    public void test_phraseDescribesElementPartly_leftBorderInAnswerGreaterThanExpected()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,3,3));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.PHRASE_DESCRIBES_ELEMENT_PARTLY));
    }

    @Test
    public void test_phraseDescribesElementPartly_leftBorderInAnswerGreaterThanExpected_and_rightBorderInAnswerLessThanExpected()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,1,4);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,2,3));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.PHRASE_DESCRIBES_ELEMENT_PARTLY));
    }

    @Test
    public void test_longPhrase_leftBorderIsLefterThanExpected()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,1,3));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.LONG_PHRASE));
    }

    @Test
    public void test_longPhrase_rightBorderIsRighterThanExpected()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,2,4));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.LONG_PHRASE));
    }

    @Test
    public void test_longPhrase_leftBorderIsLefterThanExpected_and_rightBorderIsRighterThanExpected()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,2,3);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,1,4));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.LONG_PHRASE));
    }

    @Test
    public void test_selectedPhraseIsLefterThanFirstPhrase()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,4,8);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,1,3));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.PHRASE_DOESNT_DESCRIBE_ELEMENT));
    }

    @Test
    public void test_selectedPhraseBetweenTwoPhrases()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,4,8);
        DataElement dataElement1 = new DataElement(m.ontologyModel, "t","t");
        p.addDataElement(dataElement1, DataElement.Direction.CHANGED_DATA, 12,13);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,9,11));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.PHRASE_DOESNT_DESCRIBE_ELEMENT));
    }

    @Test
    public void test_selectedPhraseRighterThanAllPhrases()
    {
        Model m = new Model();
        Problem p = m.addProblem(1,"Calculates days between dates","some notice","date_distance");
        Student s = m.addStudent(1);
        Session session = m.addSession(s,p);
        DataElement dataElement = new DataElement(m.ontologyModel, "test", "test");
        p.addDataElement(dataElement, DataElement.Direction.INPUT_DATA,4,8);
        DataElement dataElement1 = new DataElement(m.ontologyModel, "t","t");
        p.addDataElement(dataElement1, DataElement.Direction.CHANGED_DATA, 12,13);
        Error e = m.checkAnswer(session, new DataElementIdentifying(session.ontologyModel,14,15));
        assert (Objects.equals(e.ontClass, OntologyClasses.Error.PHRASE_DOESNT_DESCRIBE_ELEMENT));
    }


}
