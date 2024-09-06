package com.yourtechnologies.yourtechnologies.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "form_questions")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String choiceType;

    private String choices;

    private Integer isRequired;

    @ManyToOne
    @JoinColumn(
            name = "form_id",
            referencedColumnName = "id"
    )
    private Form form;

    public void setChoicesListToString(List<String> choices) throws JsonProcessingException {
        this.choices = choicesListToString(choices);
    }

    public List<String> getChoicesStringToList() throws JsonProcessingException {
        return choicesStringToList(this.choices);
    }

    private String choicesListToString(List<String> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }

    private List<String> choicesStringToList(String listField) throws JsonProcessingException {
        if (listField == null || listField.equalsIgnoreCase("")) {
            return new ArrayList<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(listField, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, String.class));
    }
}
