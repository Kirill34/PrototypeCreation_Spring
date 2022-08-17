package com.example.demo.controller.ontology;

import com.example.demo.controller.ontology.domaintypes.DomainType;
import com.example.demo.controller.ontology.domaintypes.IntegerNumber;
import com.example.demo.controller.ontology.language.Int;
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
    public void test1()
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
    }
}
