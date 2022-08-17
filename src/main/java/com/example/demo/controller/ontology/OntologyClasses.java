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
        public static String ANSWER_DATA_ELEMENT_IMPLEMENTATION = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#DataElementImplementation";
        public static String ANSWER_TRANSFER_METHOD_CHOICE = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#TransferMethodChoice";
        public static String ANSWER_LANGUAGE_TYPE_CHOICE = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#LanguageTypeChoice";
        public static String SESSIONS_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#SessionComponent";

        public static String TRANSFER_METHOD_RETURN_VALUE = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#ReturnValue";
        public static String TRANSFER_METHOD_INPUT_PARAMETER = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#InputParameter";
        public static String TRANSFER_METHOD_OUTPUT_PARAMETER = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#OutputParameter";
        public static String TRANSFER_METHOD_CHANGED_PARAMETER = "http://www.semanticweb.org/dns/ontologies/2021/10/session-ontology#ChangedParameter";
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

        public static String IMPLEMENTATION_COLLECTION_FOR_SCALAR = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#ImplementationCollectionForScalar";
        public static String UNDEFINED_COLLECTION_SIZE = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#UndefinedCollectionSize";

        public static String INPUT_PARAMETER_FOR_OUTPUT_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#InputParameterForOutputComponent";
        public static String INPUT_PARAMETER_FOR_CHANGED_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#InputParameterForUpdatableComponent";
        public static String OUTPUT_PARAMETER_FOR_INPUT_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#OutputParameterForInputComponent";
        public static String OUTPUT_PARAMETER_FOR_UPDATABLE_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#OutputParameterForUpdatableComponent";
        public static String UPDATABLE_PARAMETER_FOR_INPUT_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#UpdatableParameterForInputComponent";
        public static String UPDATABLE_PARAMETER_FOR_OUTPUT_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#UpdatableParameterForOutputComponent";
        public static String FEW_RETURN_VALUES = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#FewReturnValues";
        public static String RETURN_VALUE_FOR_INPUT_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CantReturnInputComponent";
        public static String RETURN_VALUE_FOR_CHANGED_COMPONENT = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CantReturnUpdatableComponent";
        public static String CANT_RETURN_COLLECTION = "http://www.semanticweb.org/dns/ontologies/2022/1/error-ontology#CantReturnCollection";
    }

    public static class Language {
        public static String LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Lexem";
        public static String BEGIN_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#BeginLexem";
        public static String BRACKET_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#BracketLexem";
        public static String CLOSE_BRACKET_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#CloseBracketLexem";
        public static String OPEN_BRACKET_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#OpenBracketLexem";
        public static String COMMA_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#CommaLexem";
        public static String END_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#EndLexem";
        public static String ID_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#IDLexem";
        public static String FUNCTION_NAME_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#FunctionNameLexem";
        public static String PARAMETER_NAME_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#ParameterNameLexem";
        public static String STRUCT_NAME_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#StructNameLexem";
        public static String SEMICOLON_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#SemicolonLexem";
        public static String TYPE_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#TypeLexem";
        public static String ARRAY_LENGTH_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#ArrayLengthLexem";
        public static String POINTER_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#PointerLexem";
        public static String SCALAR_TYPE_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#ScalarTypeLexem";
        public static String SQUARE_BRACKET_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#SquareBracketLexem";
        public static String STRUCT_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#StructLexem";
        public static String BOOL_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#BoolLexem";
        public static String CHAR_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#CharLexem";
        public static String DOUBLE_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#DoubleLexem";
        public static String FLOAT_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#FloatLexem";
        public static String INT_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#IntLexem";
        public static String LONG_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#LongLexem";
        public static String SHORT_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#ShortLexem";
        public static String UNSIGNED_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#UnsignedLexem";
        public static String VOID_LEXEM = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#VoidLexem";
        public static String TYPE = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Type";
        public static String ARRAY = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Array";
        public static String POINTER = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Pointer";
        public static String SCALAR = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Scalar";
        public static String NUMBER = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Number";
        public static String BOOL = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Bool";
        public static String INTEGER_NUMBER = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#IntegerNumber";
        public static String CHAR = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Char";
        public static String INT = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Int";
        public static String LONG = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Long";
        public static String SHORT = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Short";
        public static String REAL_NUMBER = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#RealNumber";
        public static String DOUBLE = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Double";
        public static String FLOAT = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Float";
        public static String STRUCT = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Struct";
        public static String TYPE_MODIFIER = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#TypeModifier";
        public static String CONST = "http://www.semanticweb.org/dns/ontologies/2022/0/language-ontology#Const";
    }
}
