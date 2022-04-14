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

    private static HashMap<Long,ProblemClass> studentProblems = new HashMap<>();

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

    private void addProblem1()
    {
        Problem p = new Problem("Определить признак пересечения первого отрезка и второго отрезка на числовой оси","has_intersection","Отрезки задаются целыми числами в диапазоне [-10^10; 10^10]. \n" +
                "Отрезки пересекаются, если имеют общие значения на числовой оси");
        problemRepository.save(p);

        DomainType domainTypeHasIntersection = DomainType.createLogicalDomainType("hasIntersection","Признак пересечения");
        domainTypeRepository.save(domainTypeHasIntersection);

        DataElement dataElementHasIntersection = new DataElement(p,"intersection_exists","Признак пересечения",domainTypeHasIntersection, DataElement.DataElementDirection.OUTPUT_DATA);
        dataElementRepositiory.save(dataElementHasIntersection);

        Phrase phraseHasIntersection = new Phrase(dataElementHasIntersection,2,3);
        phraseRepository.save(phraseHasIntersection);

        DomainType domainTypeLineSegment = DomainType.createEntityDomainType("line_segment","Отрезок на числовой оси");
        domainTypeRepository.save(domainTypeLineSegment);

        DomainType domainTypeLineSegmentBorder = DomainType.createIntegerDomainType("segmentBorder","Граница отрезка [-10^10;10^10]",-10000000000L,10000000000L);
        domainTypeRepository.save(domainTypeLineSegmentBorder);

        EntityField entityFieldLeftBorder = new EntityField("left_border","Левая граница",domainTypeLineSegment,domainTypeLineSegmentBorder);
        entityFieldRepository.save(entityFieldLeftBorder);

        EntityField entityFieldRightBorder = new EntityField("right_border","Правая граница",domainTypeLineSegment,domainTypeLineSegmentBorder);
        entityFieldRepository.save(entityFieldRightBorder);

        DataElement dataElementFirstLineSegment = new DataElement(p,"first_segment","Первый отрезок",domainTypeLineSegment, DataElement.DataElementDirection.INPUT_DATA);
        dataElementRepositiory.save(dataElementFirstLineSegment);

        DataElement dataElementSecondLineSegment = new DataElement(p,"second_segment","Второй отрезок",domainTypeLineSegment, DataElement.DataElementDirection.INPUT_DATA);
        dataElementRepositiory.save(dataElementSecondLineSegment);

        Phrase phraseFirstLineSegment = new Phrase(dataElementFirstLineSegment,4,5);
        phraseRepository.save(phraseFirstLineSegment);

        Phrase phraseSecondLineSegment = new Phrase(dataElementSecondLineSegment,7,8);
        phraseRepository.save(phraseSecondLineSegment);

        DataElementImplementation implementationHasIntersection = new DataElementImplementation("hasIntersectionLogical", "Признак пересечения - логический тип", dataElementHasIntersection);
        dataElementImplementationRepository.save(implementationHasIntersection);

        DataComponent dataComponentHasIntersection = new DataComponent("intersection_exists_c","Признак пересечения",implementationHasIntersection,domainTypeHasIntersection);
        dataComponentRepository.save(dataComponentHasIntersection);

        DataElementImplementation implementationFirstLineSegment = new DataElementImplementation("firstLineSegment2Numbers","2 числа: левая граница и правая граница",dataElementFirstLineSegment);
        dataElementImplementationRepository.save(implementationFirstLineSegment);

        DataComponent dataComponentFirstLeftBorder = new DataComponent("left_border","Левая граница",implementationFirstLineSegment,domainTypeLineSegmentBorder);
        dataComponentRepository.save(dataComponentFirstLeftBorder);

        DataComponent dataComponentFirstRightBorder = new DataComponent("right_border","Правая граница",implementationFirstLineSegment,domainTypeLineSegmentBorder);
        dataComponentRepository.save(dataComponentFirstRightBorder);

        DataElementImplementation implementationSecondLineSegment = new DataElementImplementation("secondLineSegment2Numbers","2 числа: левая граница и правая граница",dataElementSecondLineSegment);
        dataElementImplementationRepository.save(implementationSecondLineSegment);

        DataComponent dataComponentSecondLeftBorder = new DataComponent("left_border","Левая граница",implementationSecondLineSegment,domainTypeLineSegmentBorder);
        dataComponentRepository.save(dataComponentSecondLeftBorder);

        DataComponent dataComponentSecondRightBorder = new DataComponent("right_border","Правая граница",implementationSecondLineSegment,domainTypeLineSegmentBorder);
        dataComponentRepository.save(dataComponentSecondRightBorder);
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

        Phrase phraseDate = new Phrase(dataElementDate,2,2);
        phraseRepository.save(phraseDate);

        Phrase phraseInterval = new Phrase(dataElementInterval, 4,6);
        phraseRepository.save(phraseInterval);

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

        DataElementImplementation implementationDate = new DataElementImplementation("date3numbers","3 целых числа: день, месяц, год",dataElementDate);
        dataElementImplementationRepository.save(implementationDate);

        DataComponent dataComponentDay = new DataComponent("day","Номер дня",implementationDate,domainTypeDayNumber);
        dataComponentRepository.save(dataComponentDay);

        DataComponent dataComponentMonth = new DataComponent("month","Номер месяца",implementationDate,domainTypeMonthNumber);
        dataComponentRepository.save(dataComponentMonth);

        DataComponent dataComponentYear = new DataComponent("year","Номер года",implementationDate,domainTypeYearNumber);
        dataComponentRepository.save(dataComponentYear);

        DataElementImplementation implementationInterval = new DataElementImplementation("days_count","Количество дней - целое число [1;365]",dataElementInterval);
        dataElementImplementationRepository.save(implementationInterval);

        DataComponent dataComponentDaysCount = new DataComponent("days_count","Количество дней",implementationInterval,domainTypeInterval);
        dataComponentRepository.save(dataComponentDaysCount);
    }

    private void addProblem4()
    {
        Problem p = new Problem("Привести значение к заданному интервалу значений","value_to_interval","Если значение лежит вне интервала, то оно приводится к ближайшей границе интервала.\n" +
                " Заданное значение и интервал представлены дробными числами с точностью до 2 знаков после запятой и лежат в диапазоне [-1000;1000] \n");
        problemRepository.save(p);

        DomainType domainTypeValue = DomainType.createRealDomainType("settedValue","Заданное значение",-1000,1000);
        domainTypeRepository.save(domainTypeValue);

        DomainType domainTypeIntervalBorder = DomainType.createRealDomainType("intervalBorder","Граница интервала",-1000,1000);
        domainTypeRepository.save(domainTypeIntervalBorder);

        DataElement dataElementValue = new DataElement(p,"value","Значение",domainTypeValue, DataElement.DataElementDirection.CHANGED_DATA);
        dataElementRepositiory.save(dataElementValue);

        Phrase phraseValue = new Phrase(dataElementValue,2,2);
        phraseRepository.save(phraseValue);

        DomainType domainTypeInterval = DomainType.createEntityDomainType("intervalEntity","Интервал значений");
        domainTypeRepository.save(domainTypeInterval);

        EntityField entityFieldLeftBorder = new EntityField("left_border","Левая граница интервала",domainTypeInterval,domainTypeIntervalBorder);
        entityFieldRepository.save(entityFieldLeftBorder);

        EntityField entityFieldRightBorder = new EntityField("right_border","",domainTypeInterval,domainTypeIntervalBorder);
        entityFieldRepository.save(entityFieldRightBorder);

        DataElementImplementation implementationValue = new DataElementImplementation("valueNumber","Значение - вещественное число [-1000;1000]",dataElementValue);
        dataElementImplementationRepository.save(implementationValue);

        DataComponent dataComponentValue = new DataComponent("valueComponent","Значение",implementationValue,domainTypeValue);
        dataComponentRepository.save(dataComponentValue);

        DataElement dataElementInterval = new DataElement(p,"interval","Интервал",domainTypeInterval, DataElement.DataElementDirection.INPUT_DATA);
        dataElementRepositiory.save(dataElementInterval);

        DataElementImplementation implementationInterval = new DataElementImplementation("interval2Numbers","2 вещественных числа: левая граница и правая граница",dataElementInterval);
        dataElementImplementationRepository.save(implementationInterval);

        DataComponent dataComponentIntervalLeft = new DataComponent("left_border","Левая граница интервала",implementationInterval,domainTypeIntervalBorder);
        dataComponentRepository.save(dataComponentIntervalLeft);

        DataComponent dataComponentIntervalRight = new DataComponent("right_border","Правая граница интервала",implementationInterval,domainTypeIntervalBorder);
        dataComponentRepository.save(dataComponentIntervalRight);

        Phrase phraseInterval = new Phrase(dataElementInterval,4,6);
        phraseRepository.save(phraseInterval);
    }

    @GetMapping("/initDB")
    private void initDB()
    {
        if (problemRepository.count() == 0)
        {
            addProblem1();
            addProblem2();
            addProblem3();
            addProblem4();
            addProblem5();
        }
    }



    @PostMapping("/initStudent")
    public Long initStudent(String firstName, String lastName, Long problemID)
    {
        Problem p = getProblem(problemID);
        Student student = new Student(firstName, lastName, p );
        Long id = studentRepository.save(student).getId();

        studentProblems.put(id, new ProblemClass());

        studentProblems.get(id);

        if (!studentProblems.get(id).problemExistsById(String.valueOf(problemID))) {

            studentProblems.get(id).addNewProblem(p.getId().intValue(), p.getText(), p.getNotice(), p.getFuncName());
            List<DataElement> elements = dataElementRepositiory.findAllByProblem(p);
            for (DataElement el : elements) {
                System.out.println("Data element: " + el.getId());
                String elName = el.getName();
                String elMission = el.getMission();
                DataElement.DataElementDirection direction = el.getDirection();
                DomainType domainType = el.getDomainType();


                Phrase phrase = phraseRepository.findByDataElement(el);
                studentProblems.get(id).addProblemDataElement(Math.toIntExact(el.getProblem().getId()), el.getId().intValue(), elName, elMission, direction, phrase.getLeftBorder(), phrase.getRightBorder());

                studentProblems.get(id).addDomainType(domainType.getId().intValue(), domainType.getName(), domainType.getMission(), domainType.getType());

                if (domainType.getType() == DomainType.HighlyLevelTypes.ENTITY) {

                    studentProblems.get(id).addEntityFields(domainType.getId().intValue(), entityFieldRepository.findAllByDomainType(domainType));
                }

                if (domainType.getType() == DomainType.HighlyLevelTypes.INTEGER_NUMBER) {
                    //Set min and max of domain type
                    studentProblems.get(id).setDomainTypeMinAndMax(domainType.getId().intValue(), domainType.getLongMinValue(), domainType.getLongMaxValue());
                }

                if (domainType.getType() == DomainType.HighlyLevelTypes.REAL_NUMBER) {
                    studentProblems.get(id).setDomainTypeMinAndMax(domainType.getId().intValue(), domainType.getMinValue(), domainType.getMaxValue());
                }

                studentProblems.get(id).setDomainTypeForDataElement(el.getId().intValue(), domainType.getId().intValue());

                List<DataElementImplementation> implementations = dataElementImplementationRepository.findAllByDataElement(el);
                for (DataElementImplementation implementation : implementations) {
                    studentProblems.get(id).addPresentationOfDataElement(el.getId().intValue(), implementation.getId().intValue(), implementation.getName(), implementation.getMission());
                    List<DataComponent> components = dataComponentRepository.findAllByImplementation(implementation);
                    studentProblems.get(id).setComponentsForPresentation(implementation.getId().intValue(), components);
                }
            }
            studentProblems.get(id).setPhraseOrder(problemID.intValue());
        }
        studentProblems.get(id).addStudent(String.valueOf(id), String.valueOf(problemID));
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
    public HashMap<String,List<HashMap<String, String>>> getStudentComponents(Long student)
    {
        return studentProblems.get(student).getStudentComponents(String.valueOf(student));
    }

    @GetMapping("/element_components")
    public List<HashMap<String, String>> getStudentComponentsOfElement(Long student, String elementName)
    {
        return studentProblems.get(student).getStudentComponentsOfDataElement(String.valueOf(student), elementName);
    }



    @PostMapping("/1")
    public HashMap<String, String> chooseDataElement(Long student, int leftBorder, int rightBorder)
    {
        HashMap<String,String> answer = new HashMap<>();

        Student student1 = studentRepository.findById(student).get();
        //problem.addStudent(String.valueOf(student));
        answer = studentProblems.get(student).chooseDataElementBorders(String.valueOf(student), String.valueOf(leftBorder), String.valueOf(rightBorder));
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
        return studentProblems.get(student).chooseElementDirection(String.valueOf(student), elementName, elementDirection);
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
        HashMap<String,String> answerResult = studentProblems.get(student).choosePresentationForDataElement(String.valueOf(student), elementName, elementPresentation);

        AnswerElementPresentation answer = new AnswerElementPresentation(student1,dataElement,implementation,answerResult.get("correct").equals("true"),answerResult.get("message"));
        answerElementPresentationRepository.save(answer);
        return answerResult;
    }

    @PostMapping("/4")
    public HashMap<String, String> chooseTransferMethod(Long student, String elementName, String componentName, String transferMethod)
    {
        //problem.addStudent(student);

        HashMap<String,String> transferMethodAnswer = studentProblems.get(student).chooseParameterOrReturnValue(String.valueOf(student), elementName, componentName, transferMethod);
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
            answer = studentProblems.get(student).chooseReturnValuetype(String.valueOf(student), datatype);
        else
            answer = studentProblems.get(student).chooseParameterType(String.valueOf(student), parameterName, datatype);
        AnswerDatatype answerDatatype = new AnswerDatatype(studentRepository.findById(student).get(), datatype, parameterName, answer.get("correct").equals("true"), answer.get("message"));
        answerDatatypeRepository.save(answerDatatype);
        return answer;
    }

    @PostMapping("/6/addLexem")
    public HashMap<String, String> addLexemToPrototype(Long student, String lexemType, String lexemValue)
    {
        //problem.addStudent(student);
        HashMap<String,String> answer = studentProblems.get(student).addLexemToPrototypeCode(String.valueOf(student), lexemType, lexemValue);
        AnswerPrototypeCode answerPrototypeCode = new AnswerPrototypeCode(studentRepository.findById(student).get(), answer.get("code"), answer.get("correct").equals("true"), answer.get("message"), answer.containsKey("completed"));
        answerPrototypeCodeRepository.save(answerPrototypeCode);
        return answer;
    }

    @PostMapping("/6/removeLexem")
    public HashMap<String, String> removeLexem(Long student)
    {
        //problem.addStudent(student);
        HashMap<String,String> answer = studentProblems.get(student).removeLastLexemFromPrototypeCode(String.valueOf(student));
        AnswerPrototypeCode answerPrototypeCode = new AnswerPrototypeCode(studentRepository.findById(student).get(), answer.get("code"), true, "",answer.containsKey("completed"));
        answerPrototypeCodeRepository.save(answerPrototypeCode);
        return answer;
    }

    @GetMapping("/problemLexems")
    public String[] getProblemLexems(Long student)
    {
        //problem.addStudent(student);
        String text = studentProblems.get(student).getFullText(String.valueOf(student));
        return text.split(" ");
    }

    @GetMapping("/parameters")
    public List<String> getStudentParameters(Long student)
    {
        return studentProblems.get(student).getStudentParameters(String.valueOf(student));
    }

    @GetMapping("/parametersWithDescription")
    public List<HashMap<String,String>> getStudentParameterList(Long student)
    {
        return studentProblems.get(student).getParamList(String.valueOf(student));
    }

    @GetMapping("/returnWithDescription")
    public HashMap<String,String> getStudentReturnValue(Long student)
    {
        return studentProblems.get(student).getReturnValue(String.valueOf(student));
    }

    @GetMapping("/presentations")
    public HashMap<String,String> getElementPresentations(Long student, String elementName)
    {
        return studentProblems.get(student).getPresentationsForDataElement(String.valueOf(student), elementName);
    }

    @GetMapping("/lexemsForPrototype")
    public List<HashMap<String,String>> getLexemesForPrototype(Long student)
    {
        return studentProblems.get(student).getLexemesForPrototype(String.valueOf(student));
    }

    @GetMapping("/model")
    public String getModel()
    {
        return problem.getAllModel();
    }



}