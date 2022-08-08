package com.example.demo.controller.ontology;

public class OntologyClasses {

    public static class Problem
    {
        public static String PROBLEM = "http://www.semanticweb.org/problem-ontology#Problem";
        public static String DATA_ELEMENT = "http://www.semanticweb.org/problem-ontology#DataElement";
        public static String DOMAIN_TYPE = "http://www.semanticweb.org/problem-ontology#DomainType";
        public static String DOMAIN_TYPE_ENTITY = "http://www.semanticweb.org/problem-ontology#Entity";
        public static String DOMAIN_TYPE_SCALAR = "http://www.semanticweb.org/problem-ontology#Scalar";
        public static String DOMAIN_TYPE_LOGICAL = "http://www.semanticweb.org/problem-ontology#Logical";
        public static String DOMAIN_TYPE_NUMBER = "http://www.semanticweb.org/problem-ontology#Number";
        public static String DOMAIN_TYPE_FLOAT_NUMBER = "http://www.semanticweb.org/problem-ontology#FloatNumber";
        public static String DOMAIN_TYPE_INTEGER_NUMBER = "http://www.semanticweb.org/problem-ontology#IntegerNumber";
        public static String DOMAIN_TYPE_COLLECTION = "http://www.semanticweb.org/problem-ontology#Collection";
        public static String DOMAIN_TYPE_LIST = "http://www.semanticweb.org/problem-ontology#List";
        public static String DOMAIN_TYPE_STRING = "http://www.semanticweb.org/problem-ontology#String";
        public static String DOMAIN_TYPE_SET = "http://www.semanticweb.org/problem-ontology#Set";
        public static String PHRASE = "http://www.semanticweb.org/problem-ontology#Phrase";
        public static String DATA_ELEMENT_PRESENTATION = "http://www.semanticweb.org/problem-ontology#DataElementPresentation";
    }

    public static class Session
    {
        public static String STUDENT = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Student";
        public static String SESSION = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#Session";
        public static String ANSWER_DATA_ELEMENT_IDENTIFYING = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#DataElementIdentifying";
        public static String ANSWER_DATA_ELEMENT_DIRECTION_CHOICE = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#DataElementDirectionChoice";
    }

    public static class Error
    {
        public static String ELEMENT_ALREADY_DEFINED = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#ElementAlreadyDefined";
        public static String LONG_PHRASE = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#LongPhrase";
        public static String PHRASE_DESCRIBES_ELEMENT_PARTLY = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#PhrasePartlyDescribesElement";
        public static String PHRASE_DOESNT_DESCRIBE_ELEMENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#PhraseDoesntDescribeElement";
        public static String NO_ERROR = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#NoError";

        public static String CORRECT_INPUT_CHOSEN_OUTPUT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CorrectInputChosenOutput";
        public static String CORRECT_INPUT_CHOSEN_UPDATABLE = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CorrectInputChosenUpdatable";
        public static String CORRECT_OUTPUT_CHOSEN_INPUT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CorrectOutputChosenInput";
        public static String CORRECT_OUTPUT_CHOSEN_UPDATABLE = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CorrectOutputChosenUpdatable";
        public static String CORRECT_UPDATABLE_CHOSEN_INPUT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CorrectUpdatableChosenInput";
        public static String CORRECT_UPDATABLE_CHOSEN_OUTPUT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CorrectUpdatableChosenOutput";
    }
}
