package com.yourtechnologies.yourtechnologies.util;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Component
public class UtilGeneral {
    public static boolean[] checkJsonChoicesAndChoiceType(Iterator<Map.Entry<String, JsonNode>> fields) {
        boolean mustHaveChoices = false;
        boolean choicesExist = false;
        boolean isChoiceTypeCorrect = false;

        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> field = fields.next();
            if (field.getKey().equalsIgnoreCase("choices")) {
                choicesExist = true;
            } else if (field.getKey().equalsIgnoreCase("choice_type")) {
                String[] restrictedChoicesType = {
                        "multiple choice"
                        ,"dropdown"
                        ,"checkboxes"
                        ,"short answer"
                        ,"paragraph"
                        ,"date"
                };
                if (Arrays.stream(restrictedChoicesType)
                        .anyMatch((v)->
                                v.equalsIgnoreCase(
                                        field.getValue().textValue()
                                                .replaceAll("\"", ""))))
                {
                    isChoiceTypeCorrect = true;
                }
                String[] mustHaveChoicesType = {
                        "multiple choice"
                        ,"dropdown"
                        ,"checkboxes"
                };
                if (Arrays.stream(mustHaveChoicesType)
                        .anyMatch((v)->
                                v.equalsIgnoreCase(
                                        field.getValue().textValue()
                                                .replaceAll("\"", ""))))
                {
                    mustHaveChoices = true;
                }

            }
        }

        boolean[] fieldBooleans = {mustHaveChoices, choicesExist, isChoiceTypeCorrect};

        return fieldBooleans;
    }
}
