package com.example.demo.controller.ontology.error;

import com.example.demo.controller.Language;
import com.example.demo.controller.ontology.AbstractOntologyObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.jena.atlas.json.JSON;
import org.apache.jena.atlas.json.io.parserjavacc.javacc.JSON_Parser;
import org.apache.jena.base.Sys;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Resource;
import org.json.JSONObject;

import java.io.*;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Error extends AbstractOntologyObject {

    public Error(OntModel ontModel, String ontClass, Resource resource)
    {
        super(ontModel, ontClass, resource);
    }

    public Error(OntModel ontModel, String ontClass) {
        super(ontModel, ontClass);
        //this.ontResource = ontResource;
    }

    public String getErrorMessage(Language language)
    {
        HashMap<Language, String> languageStringHashMap = new HashMap<>();
        languageStringHashMap.put(Language.RU,"ru");
        languageStringHashMap.put(Language.EN,"en");

        try
        {
            System.out.println(getClass().getResource("/").getPath()+"../classes/error_messages.json");
            InputStream is = new FileInputStream(getClass().getResource("/").getPath()+"../classes/error_messages.json");
            String jsonTxt = IOUtils.toString(is, "UTF-8");
            JSONObject object = new JSONObject(jsonTxt);
            JSONObject classMessages = object.getJSONObject(this.ontClass);
            String messagePattern = classMessages.get(languageStringHashMap.get(language)).toString();

            Pattern p = Pattern.compile("<(.*?)>");
            Matcher m = p.matcher(messagePattern);
            while (m.find())
            {
                System.out.println("Group 1: "+m.group(1));
                String replace = m.group(1);
                System.out.println(replace);
                String[] parts = replace.split("\\:\\:");
                System.out.println("Part 1 :"+parts[0]+" Part 2 :"+parts[1]);
                if (parts.length == 2)
                {
                    ObjectProperty property = ontologyModel.getObjectProperty(parts[0]);
                    Resource obj = ontResource.getProperty(property).getResource();
                    String value = obj.getProperty(ontologyModel.getDatatypeProperty(parts[1])).getLiteral().toString();
                    return m.replaceFirst(value);
                }

            }

            return messagePattern;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }
}
