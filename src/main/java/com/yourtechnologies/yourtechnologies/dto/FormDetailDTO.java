package com.yourtechnologies.yourtechnologies.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourtechnologies.yourtechnologies.annotations.AlphanumericDotDash;
import com.yourtechnologies.yourtechnologies.annotations.MustArrayField;
import com.yourtechnologies.yourtechnologies.annotations.UniqueSlug;
import com.yourtechnologies.yourtechnologies.dto.response.BaseResponseDTO;
import com.yourtechnologies.yourtechnologies.entity.Question;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter @Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FormDetailDTO {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    @AlphanumericDotDash
    @UniqueSlug
    private String slug;
    @NotNull
    @MustArrayField
    private List<String> allowedDomains;
    private String description;
    private Boolean limitOneResponse;
    private String creatorId;
    private List<QuestionWithChoicesDTO> questions;
}
