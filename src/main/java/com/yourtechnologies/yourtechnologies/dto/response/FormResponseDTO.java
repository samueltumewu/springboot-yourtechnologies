package com.yourtechnologies.yourtechnologies.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourtechnologies.yourtechnologies.dto.FormDTO;
import com.yourtechnologies.yourtechnologies.entity.Form;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FormResponseDTO extends BaseResponseDTO {

    private Form form;
    public FormResponseDTO(String message) {
        super(message);
    }
}
