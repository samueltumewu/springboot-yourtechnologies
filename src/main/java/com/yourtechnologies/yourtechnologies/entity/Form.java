package com.yourtechnologies.yourtechnologies.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "forms")
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String slug;
    private String description;
    private Integer limitOneResponse;
    private Long creatorId;
    private String allowDomain;

    public void setAllowDomainListToString(List<String> domains) throws JsonProcessingException {
        this.allowDomain = allowDomainListToString(domains);
    }

    public List<String> getAllowDomainStringToList() throws JsonProcessingException {
        return allowDomainStringToList(this.allowDomain);
    }

    private String allowDomainListToString(List<String> list) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(list);
    }

    private List<String> allowDomainStringToList(String listField) throws JsonProcessingException {
        if (listField == null || listField.equalsIgnoreCase("")) {
            return new ArrayList<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(listField, TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, String.class));
    }
}
