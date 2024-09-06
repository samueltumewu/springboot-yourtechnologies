package com.yourtechnologies.yourtechnologies.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourtechnologies.yourtechnologies.dto.FormDTO;
import com.yourtechnologies.yourtechnologies.entity.Form;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FormListResponseDTO extends BaseResponseDTO {

    private List<FormDTO> forms;
    public FormListResponseDTO(String message) {
        super(message);
    }
}
