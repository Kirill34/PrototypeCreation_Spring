package com.example.demo.controller.ontology.session;

import com.example.demo.controller.ontology.AbstractOntologyObject;
import com.example.demo.controller.ontology.ObjectProperties;
import com.example.demo.controller.ontology.OntologyClasses;
import com.example.demo.controller.ontology.language.lexemes.Lexeme;
import com.example.demo.controller.ontology.session.answer.Answer;
import model.Problem;
import org.apache.jena.ontology.OntModel;

import java.util.ArrayList;
import java.util.List;

public class Session extends AbstractOntologyObject {

    private Student student;

    private List<Answer> answerList = new ArrayList<>();

    private com.example.demo.controller.ontology.problem.Problem problem;

    private Lexeme firstPrototypeLexeme = null;

    public Session(OntModel ontModel, Student student, com.example.demo.controller.ontology.problem.Problem problem) {
        super(ontModel, OntologyClasses.Session.SESSION);
        this.student=student;
        this.problem=problem;
        this.setObjectProperty(ObjectProperties.Session.HAS_SOLVING_PROBLEM, problem);
        this.setObjectProperty(ObjectProperties.Session.OF_STUDENT,student);
    }

    public void addAnswer(Answer answer)
    {
        problem.sortPhrases();
        if (answerList.size()==0)
        {
            this.setObjectProperty(ObjectProperties.Session.HAS_FIRST_ANSWER,answer);
        }
        else
        {
            answerList.get(answerList.size()-1).setNextAnswer(answer);
        }
        this.setObjectProperty(ObjectProperties.Session.HAS_LAST_ANSWER,answer);
        answerList.add(answer);
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    private void setFirstPrototypeLexeme(Lexeme lexeme)
    {
        this.firstPrototypeLexeme=lexeme;
        this.setObjectProperty(ObjectProperties.Language.HAS_FIRST_LEXEME,lexeme);
    }

    public void addLexeme(Lexeme lexeme)
    {
        if (this.firstPrototypeLexeme == null)
        {
            setFirstPrototypeLexeme(lexeme);
        }
        else
        {
            Lexeme currLexeme = firstPrototypeLexeme;
            while (currLexeme.hasNextLexeme())
            {
                currLexeme = currLexeme.getNextLexeme();
            }
            currLexeme.setNextLexeme(lexeme);
        }
    }
}
