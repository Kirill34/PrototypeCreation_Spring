package com.example.demo.controller;



import model.*;
import repo.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;


@RestController
@RequestMapping("/answer")
public class UserController {

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private AnswerRepository answerRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private StudentRepository studentRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private ProblemRepository problemRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private DataElementRepositiory dataElementRepositiory;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private PhraseRepository phraseRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private AnswerElementDirectionRepository answerElementDirectionRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private AnswerElementBordersRepository answerElementBordersRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private DataElementImplementationRepository dataElementImplementationRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private DataComponentRepository dataComponentRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private EntityFieldRepository entityFieldRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private AnswerElementPresentationRepository answerElementPresentationRepository;

    private static ProblemClass problem = new ProblemClass();

    @GetMapping
    public String getAnswer(String p)
    {
        return "string "+p;
    }

    private Problem getProblem(long id)
    {
        /*session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Problem problem = (Problem) session.get(Problem .class, id);
        session.getTransaction().commit();
        session.close();*/
        Problem problem = problemRepository.findById(id);
        return  problem;
    }

    @PostMapping("/initStudent")
    public Long initStudent(String firstName, String lastName, Long problemID)
    {
        Problem p = getProblem(problemID);
        Student student = new Student(firstName, lastName, p );
        Long id = studentRepository.save(student).getId();

        problem.addNewProblem(p.getId().intValue(), p.getText(), p.getNotice(), p.getFuncName());
        List<DataElement> elements = dataElementRepositiory.findAllByProblem(p);
        for (DataElement el: elements)
        {
            System.out.println("Data element: "+el.getId());
            String elName = el.getName();
            String elMission = el.getMission();
            DataElement.DataElementDirection direction = el.getDirection();
            DomainType domainType = el.getDomainType();



            Phrase phrase = phraseRepository.findByDataElement(el);
            problem.addProblemDataElement(Math.toIntExact(el.getProblem().getId()), el.getId().intValue(), elName, elMission, direction, phrase.getLeftBorder(), phrase.getRightBorder());

            problem.addDomainType(domainType.getId().intValue(), domainType.getName(), domainType.getMission(), domainType.getType());

            if (domainType.getType() == DomainType.HighlyLevelTypes.ENTITY)
            {

                problem.addEntityFields(domainType.getId().intValue(), entityFieldRepository.findAllByDomainType(domainType));
            }

            if (domainType.getType() == DomainType.HighlyLevelTypes.INTEGER_NUMBER)
            {
                //Set min and max of domain type
                problem.setDomainTypeMinAndMax(domainType.getId().intValue(), (int)domainType.getMinValue(), (int)domainType.getMaxValue());
            }

            problem.setDomainTypeForDataElement(el.getId().intValue(), domainType.getId().intValue());

            List<DataElementImplementation> implementations = dataElementImplementationRepository.findAllByDataElement(el);
            for (DataElementImplementation implementation: implementations)
            {
                problem.addPresentationOfDataElement(el.getId().intValue(), implementation.getId().intValue(), implementation.getName(), implementation.getMission());
                List<DataComponent> components = dataComponentRepository.findAllByImplementation(implementation);
                problem.setComponentsForPresentation(implementation.getId().intValue(), components);
            }
        }
        problem.setPhraseOrder(problemID.intValue());
        problem.addStudent(String.valueOf(id), String.valueOf(problemID));
        return id;
    }

    @GetMapping("/problems")
    public List<Problem> getAllProblems()
    {
        return problemRepository.findAll();
    }

    @GetMapping("/fullText")
    public String getFullText(Long studentID)
    {
        return studentRepository.findById(studentID).get().getProblem().getText();//problem.getFullText(studentID);
    }

    @GetMapping("/funcName")
    public String getFuncName(Long studentID)
    {
        return studentRepository.findById(studentID).get().getProblem().getFuncName();
    }

    @GetMapping("/notice")
    public String getNotice(Long studentID)
    {
        return studentRepository.findById(studentID).get().getProblem().getNotice();
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
    public HashMap<String, String> chooseDataElement(Long student, int leftBorder, int rightBorder)
    {
        HashMap<String,String> answer = new HashMap<>();

        Student student1 = studentRepository.findById(student).get();
        //problem.addStudent(String.valueOf(student));
        answer = problem.chooseDataElementBorders(String.valueOf(student), String.valueOf(leftBorder), String.valueOf(rightBorder));
        AnswerElementBorders answerElementBorders = new AnswerElementBorders(student1, leftBorder, rightBorder, answer.get("correct").equals("true"), answer.get("message"));
        answerElementBordersRepository.save(answerElementBorders);
        return  answer;
    }

    @PostMapping("/2")
    public HashMap<String, String> chooseDataElementDirection(Long student, String elementName, String elementDirection)
    {
        Student student1 = studentRepository.findById(student).get();
        Problem problem1 = student1.getProblem();
        DataElement dataElement = dataElementRepositiory.findByProblemAndAndName(problem1,elementName);

        AnswerElementDirection.ElementDirection direction = AnswerElementDirection.ElementDirection.INPUT_DATA;

        switch (elementDirection)
        {
            case "input":
                direction = AnswerElementDirection.ElementDirection.INPUT_DATA;
                break;
            case "output":
                direction = AnswerElementDirection.ElementDirection.OUTPUT_DATA;
                break;
            case "updatable":
                direction = AnswerElementDirection.ElementDirection.CHANGED_DATA;
                break;
        }

        AnswerElementDirection answerElementDirection = new AnswerElementDirection(student1, dataElement, direction);
        //answerElementDirectionRepository.save();
        //problem.addStudent(String.valueOf(student));
        answerElementDirectionRepository.save(answerElementDirection);
        return problem.chooseElementDirection(String.valueOf(student), elementName, elementDirection);
        //return elementDirection;
    }

    @PostMapping("/3")
    public HashMap<String, String> chooseDataElementPresentation(Long student, String elementName, String elementPresentation)
    {
        Student student1 = studentRepository.findById(student).get();
        Problem p = student1.getProblem();
        DataElement dataElement = dataElementRepositiory.findByProblemAndAndName(p,elementName);
        DataElementImplementation implementation = dataElementImplementationRepository.findByDataElementAndName(dataElement,elementPresentation);

        //problem.addStudent(String.valueOf(student));
        HashMap<String,String> answerResult = problem.choosePresentationForDataElement(String.valueOf(student), elementName, elementPresentation);

        AnswerElementPresentation answer = new AnswerElementPresentation(student1,dataElement,implementation,answerResult.get("correct").equals("true"),answerResult.get("message"));
        answerElementPresentationRepository.save(answer);
        return answerResult;
    }

    @PostMapping("/4")
    public HashMap<String, String> chooseTransferMethod(String student, String elementName, String componentName, String transferMethod)
    {
        //problem.addStudent(student);
        return problem.chooseParameterOrReturnValue(student, elementName, componentName, transferMethod);
        //return "";
    }

    @PostMapping("/5")
    public HashMap<String, String> chooseDataType(String student, String parameterName, String datatype)
    {
        //problem.addStudent(student);
        if (parameterName.equals("return"))
            return problem.chooseReturnValuetype(student, datatype);
        else
            return problem.chooseParameterType(student, parameterName, datatype);
        //return "";
    }

    @PostMapping("/6/addLexem")
    public HashMap<String, String> addLexemToPrototype(String student, String lexemType, String lexemValue)
    {
        //problem.addStudent(student);
        return problem.addLexemToPrototypeCode(student, lexemType, lexemValue);
        //return "";
    }

    @PostMapping("/6/removeLexem")
    public HashMap<String, String> removeLexem(String student)
    {
        //problem.addStudent(student);
        return problem.removeLastLexemFromPrototypeCode(student);
        //return "";
    }

    @GetMapping("/problemLexems")
    public String[] getProblemLexems(String student)
    {
        //problem.addStudent(student);
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

    @GetMapping("/model")
    public String getModel()
    {
        return problem.getAllModel();
    }



}