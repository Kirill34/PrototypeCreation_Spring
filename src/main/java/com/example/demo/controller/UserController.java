package com.example.demo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/answer")
public class UserController {

    private static ProblemClass problem = new ProblemClass();

    @GetMapping
    public String getAnswer(String p)
    {
        return "string "+p;
    }

    @GetMapping("/fullText")
    public String getFullText(String studentID)
    {
        return problem.getFullText(studentID);
    }

    @GetMapping("/components")
    public HashMap<String,List<HashMap<String, String>>> getStudentComponents(String student)
    {
        return problem.getStudentComponents(student);
    }

    @GetMapping("/element_components")
    public List<HashMap<String, String>> getStudentComponentsOfElement(String student, String elementName)
    {
        return problem.getStudentComponentsOfDataElement(student, elementName);
    }



    @PostMapping("/1")
    public HashMap<String, String> chooseDataElement(String student, int leftBorder, int rightBorder)
    {
        problem.addStudent(student);
        return  problem.chooseDataElementBorders(student, String.valueOf(leftBorder), String.valueOf(rightBorder)); // "left "+leftBorder+" right "+rightBorder;
    }

    @PostMapping("/2")
    public HashMap<String, String> chooseDataElementDirection(String student, String elementName, String elementDirection)
    {
        problem.addStudent(student);
        return problem.chooseElementDirection(student, elementName, elementDirection);
        //return elementDirection;
    }

    @PostMapping("/3")
    public HashMap<String, String> chooseDataElementPresentation(String student, String elementName, String elementPresentation)
    {
        problem.addStudent(student);
        return problem.choosePresentationForDataElement(student, elementName, elementPresentation);
    }

    @PostMapping("/4")
    public HashMap<String, String> chooseTransferMethod(String student, String elementName, String componentName, String transferMethod)
    {
        problem.addStudent(student);
        return problem.chooseParameterOrReturnValue(student, elementName, componentName, transferMethod);
        //return "";
    }

    @PostMapping("/5")
    public HashMap<String, String> chooseDataType(String student, String parameterName, String datatype)
    {
        problem.addStudent(student);
        if (parameterName.equals("return"))
            return problem.chooseReturnValuetype(student, datatype);
        else
            return problem.chooseParameterType(student, parameterName, datatype);
        //return "";
    }

    @PostMapping("/6/addLexem")
    public HashMap<String, String> addLexemToPrototype(String student, String lexemType, String lexemValue)
    {
        problem.addStudent(student);
        return problem.addLexemToPrototypeCode(student, lexemType, lexemValue);
        //return "";
    }

    @PostMapping("/6/removeLexem")
    public HashMap<String, String> removeLexem(String student)
    {
        problem.addStudent(student);
        return problem.removeLastLexemFromPrototypeCode(student);
        //return "";
    }

    @GetMapping("/problemLexems")
    public String[] getProblemLexems(String student)
    {
        problem.addStudent(student);
        String text = problem.getFullText(student);
        return text.split(" ");
    }

    @GetMapping("/parameters")
    public List<String> getStudentParameters(String student)
    {
        return problem.getStudentParameters(student);
    }

    @GetMapping("/parametersWithDescription")
    public List<HashMap<String,String>> getStudentParameterList(String student)
    {
        return problem.getParamList(student);
    }

    @GetMapping("/presentations")
    public HashMap<String,String> getElementPresentations(String student, String elementName)
    {
        return problem.getPresentationsForDataElement(student, elementName);
    }

    @GetMapping("/lexemsForPrototype")
    public List<HashMap<String,String>> getLexemesForPrototype(String student)
    {
        return problem.getLexemesForPrototype(student);
    }



}