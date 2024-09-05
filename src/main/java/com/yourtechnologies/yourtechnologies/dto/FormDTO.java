package com.yourtechnologies.yourtechnologies.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourtechnologies.yourtechnologies.annotations.AlphanumericDotDash;
import com.yourtechnologies.yourtechnologies.annotations.MustArrayField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FormDTO {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @AlphanumericDotDash
    private String slug;
    @NotNull
    @MustArrayField
    private List<String> allowedDomains;
    private String description;
    private Boolean limitOneResponse;
    private String creatorId;
}
