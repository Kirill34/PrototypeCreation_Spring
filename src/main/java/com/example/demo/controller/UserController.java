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

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private AnswerComponentTransferMethodRepository answerComponentTransferMethodRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private DomainTypeRepository domainTypeRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private AnswerDatatypeRepository answerDatatypeRepository;

    @org.springframework.beans.factory.annotation.Autowired(required=true)
    private AnswerPrototypeCodeRepository answerPrototypeCodeRepository;

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

    private void addProblem3()
    {
        Problem p = new Problem("Изменить заряд батарейки в соответствии с запрашиваемым количеством заряда. Дополнительно вычислить количество реально отданного заряда","get_charge",": Заряд батарейки не более 100 единиц. Запрашиваться может до 1000 единиц.");
        problemRepository.save(p);

        DomainType domainTypeCharge = DomainType.createIntegerDomainType("charge", "Заряд батареи", 0, 100);
        domainTypeRepository.save(domainTypeCharge);

        DomainType domainTypeEnquiredCharge = DomainType.createIntegerDomainType("enquired_charge","ЗАпрашиваемый заряд батареи",0,1000);
        domainTypeRepository.save(domainTypeEnquiredCharge);

        DataElement dataElementCurrentCharge = new DataElement(p, "current_charge", "Количество заряда батарейки", domainTypeCharge, DataElement.DataElementDirection.CHANGED_DATA);
        dataElementRepositiory.save(dataElementCurrentCharge);

        Phrase phraseCurrentCharge = new Phrase(dataElementCurrentCharge, 2,3);
        phraseRepository.save(phraseCurrentCharge);

        DataElement dataElementEnquiredCharge = new DataElement(p, "enquired_charge", "Количество запрашиваемого заряда", domainTypeEnquiredCharge, DataElement.DataElementDirection.INPUT_DATA);
        dataElementRepositiory.save(dataElementEnquiredCharge);

        Phrase phraseEnquiredCharge = new Phrase(dataElementEnquiredCharge, 7,9);
        phraseRepository.save(phraseEnquiredCharge);

        DataElement dataElementGivenCharge = new DataElement(p, "given_charge", "Количество отданного заряда",domainTypeCharge, DataElement.DataElementDirection.OUTPUT_DATA);
        dataElementRepositiory.save(dataElementGivenCharge);

        Phrase phraseGivenCharge = new Phrase(dataElementGivenCharge, 12,15);
        phraseRepository.save(phraseGivenCharge);

        DataElementImplementation dataElementImplementationCurrentCharge = new DataElementImplementation("battery_charge_number","Заряд батарейки - целое число [0;100]", dataElementCurrentCharge);
        dataElementImplementationRepository.save(dataElementImplementationCurrentCharge);

        DataElementImplementation dataElementImplementationEnquiredCharge = new DataElementImplementation("equired_charge_number","Запрашиваемый заряд батарейки - целое число [0;1000]",dataElementEnquiredCharge);
        dataElementImplementationRepository.save(dataElementImplementationEnquiredCharge);

        DataElementImplementation dataElementImplementationGivenCharge = new DataElementImplementation("given_charge_number","Отданный заряд - целое число [0;100]",dataElementGivenCharge);
        dataElementImplementationRepository.save(dataElementImplementationGivenCharge);

        DataComponent dataComponentCurrentCharge = new DataComponent("battery_charge","Заряд батарейки - целое число [0;100]",dataElementImplementationCurrentCharge,domainTypeCharge);
        dataComponentRepository.save(dataComponentCurrentCharge);

        DataComponent dataComponentEnquiredCharge =new DataComponent("enquired_battery_charge","Запрашиваемый заряд батарейки - целое число [0;1000]",dataElementImplementationEnquiredCharge,domainTypeEnquiredCharge);
        dataComponentRepository.save(dataComponentEnquiredCharge);

        DataComponent dataComponentGivenCharge = new DataComponent("given_battery_charge","Отданный заряд - целое число [0;100]",dataElementImplementationGivenCharge,domainTypeCharge);
        dataComponentRepository.save(dataComponentGivenCharge);
    }

    private void addProblem2()
    {
        Problem p =new Problem("Дан курс студента, на котором он учится. Перевести студента на следующий курс","upgrade_course","");
        problemRepository.save(p);

        DomainType domainTypeCourse = DomainType.createIntegerDomainType("course","Курс студента",1,6);
        domainTypeRepository.save(domainTypeCourse);

        DataElement dataElementCourse = new DataElement(p,"course","Курс студента",domainTypeCourse, DataElement.DataElementDirection.CHANGED_DATA);
        dataElementRepositiory.save(dataElementCourse);

        Phrase phraseCourse = new Phrase(dataElementCourse,2,3);
        phraseRepository.save(phraseCourse);

        DataElementImplementation implementation = new DataElementImplementation("coursenumber","Номер курса - целое число [1;6]",dataElementCourse);
        dataElementImplementationRepository.save(implementation);

        DataComponent component = new DataComponent("courseNumber","Номер курса",implementation,domainTypeCourse);
        dataComponentRepository.save(component);
    }

    private void addProblem5()
    {
        Problem p = new Problem("Увеличить дату на заданное количество дней ","increase_date","Дата увеличивается не более, чем на 1 год. Дата принадлежит текущему столетию.");
        problemRepository.save(p);

        DomainType domainTypeDate = DomainType.createEntityDomainType("date","Дата");
        domainTypeRepository.save(domainTypeDate);

        DataElement dataElementDate = new DataElement(p,"date","Дата",domainTypeDate, DataElement.DataElementDirection.CHANGED_DATA);
        dataElementRepositiory.save(dataElementDate);

        DomainType domainTypeInterval = DomainType.createIntegerDomainType("daysInterval","Интервал в днях", 1,365);
        domainTypeRepository.save(domainTypeInterval);

        DataElement dataElementInterval = new DataElement(p,"interval_days","Количество дней",domainTypeInterval, DataElement.DataElementDirection.INPUT_DATA);
        dataElementRepositiory.save(dataElementInterval);

        DomainType domainTypeYearNumber = DomainType.createIntegerDomainType("year","Номер года",2000,2100);
        domainTypeRepository.save(domainTypeYearNumber);

        DomainType domainTypeMonthNumber = DomainType.createIntegerDomainType("month","Номер месяца",1,12);
        domainTypeRepository.save(domainTypeMonthNumber);

        DomainType domainTypeDayNumber = DomainType.createIntegerDomainType("day","Номер дня",1,31);
        domainTypeRepository.save(domainTypeDayNumber);

        EntityField entityFieldDay = new EntityField("day","Номер дня",domainTypeDate,domainTypeDayNumber);
        entityFieldRepository.save(entityFieldDay);

        EntityField entityFieldMonth = new EntityField("month","Номер месяца",domainTypeDate,domainTypeMonthNumber);
        entityFieldRepository.save(entityFieldMonth);

        EntityField entityFieldYear = new EntityField("year","Номер года", domainTypeDate,domainTypeYearNumber);
        entityFieldRepository.save(entityFieldYear);
    }

    @GetMapping("/initDB")
    private void initDB()
    {
        if (problemRepository.count() == 0)
        {
            addProblem2();
            addProblem3();
            addProblem5();
        }
    }



    @PostMapping("/initStudent")
    public Long initStudent(String firstName, String lastName, Long problemID)
    {
        Problem p = getProblem(problemID);
        Student student = new Student(firstName, lastName, p );
        Long id = studentRepository.save(student).getId();

        if (!problem.problemExistsById(String.valueOf(problemID))) {

            problem.addNewProblem(p.getId().intValue(), p.getText(), p.getNotice(), p.getFuncName());
            List<DataElement> elements = dataElementRepositiory.findAllByProblem(p);
            for (DataElement el : elements) {
                System.out.println("Data element: " + el.getId());
                String elName = el.getName();
                String elMission = el.getMission();
                DataElement.DataElementDirection direction = el.getDirection();
                DomainType domainType = el.getDomainType();


                Phrase phrase = phraseRepository.findByDataElement(el);
                problem.addProblemDataElement(Math.toIntExact(el.getProblem().getId()), el.getId().intValue(), elName, elMission, direction, phrase.getLeftBorder(), phrase.getRightBorder());

                problem.addDomainType(domainType.getId().intValue(), domainType.getName(), domainType.getMission(), domainType.getType());

                if (domainType.getType() == DomainType.HighlyLevelTypes.ENTITY) {

                    problem.addEntityFields(domainType.getId().intValue(), entityFieldRepository.findAllByDomainType(domainType));
                }

                if (domainType.getType() == DomainType.HighlyLevelTypes.INTEGER_NUMBER) {
                    //Set min and max of domain type
                    problem.setDomainTypeMinAndMax(domainType.getId().intValue(), (int) domainType.getMinValue(), (int) domainType.getMaxValue());
                }

                problem.setDomainTypeForDataElement(el.getId().intValue(), domainType.getId().intValue());

                List<DataElementImplementation> implementations = dataElementImplementationRepository.findAllByDataElement(el);
                for (DataElementImplementation implementation : implementations) {
                    problem.addPresentationOfDataElement(el.getId().intValue(), implementation.getId().intValue(), implementation.getName(), implementation.getMission());
                    List<DataComponent> components = dataComponentRepository.findAllByImplementation(implementation);
                    problem.setComponentsForPresentation(implementation.getId().intValue(), components);
                }
            }
            problem.setPhraseOrder(problemID.intValue());
        }
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
    public HashMap<String, String> chooseTransferMethod(Long student, String elementName, String componentName, String transferMethod)
    {
        //problem.addStudent(student);

        HashMap<String,String> transferMethodAnswer = problem.chooseParameterOrReturnValue(String.valueOf(student), elementName, componentName, transferMethod);
        AnswerComponentTransferMethod answer = new AnswerComponentTransferMethod(studentRepository.findById(student).get(), elementName, componentName, transferMethodAnswer.get("correct").equals("true"), transferMethodAnswer.get("message"), transferMethod);
        answerComponentTransferMethodRepository.save(answer);

        return transferMethodAnswer;
    }

    @PostMapping("/5")
    public HashMap<String, String> chooseDataType(Long student, String parameterName, String datatype)
    {
        //problem.addStudent(student);
        HashMap<String,String> answer = new HashMap<>();
        if (parameterName.equals("return"))
            answer = problem.chooseReturnValuetype(String.valueOf(student), datatype);
        else
            answer = problem.chooseParameterType(String.valueOf(student), parameterName, datatype);
        AnswerDatatype answerDatatype = new AnswerDatatype(studentRepository.findById(student).get(), datatype, parameterName, answer.get("correct").equals("true"), answer.get("message"));
        answerDatatypeRepository.save(answerDatatype);
        return answer;
    }

    @PostMapping("/6/addLexem")
    public HashMap<String, String> addLexemToPrototype(Long student, String lexemType, String lexemValue)
    {
        //problem.addStudent(student);
        HashMap<String,String> answer = problem.addLexemToPrototypeCode(String.valueOf(student), lexemType, lexemValue);
        AnswerPrototypeCode answerPrototypeCode = new AnswerPrototypeCode(studentRepository.findById(student).get(), answer.get("code"), answer.get("correct").equals("true"), answer.get("message"), answer.containsKey("completed"));
        answerPrototypeCodeRepository.save(answerPrototypeCode);
        return answer;
    }

    @PostMapping("/6/removeLexem")
    public HashMap<String, String> removeLexem(Long student)
    {
        //problem.addStudent(student);
        HashMap<String,String> answer = problem.removeLastLexemFromPrototypeCode(String.valueOf(student));
        AnswerPrototypeCode answerPrototypeCode = new AnswerPrototypeCode(studentRepository.findById(student).get(), answer.get("code"), true, "",answer.containsKey("completed"));
        answerPrototypeCodeRepository.save(answerPrototypeCode);
        return answer;
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

    @GetMapping("/returnWithDescription")
    public HashMap<String,String> getStudentReturnValue(String student)
    {
        return problem.getReturnValue(student);
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