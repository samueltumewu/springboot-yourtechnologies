package com.yourtechnologies.yourtechnologies.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourtechnologies.yourtechnologies.dto.FormDetailDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class FormDetailResponseDTO extends BaseResponseDTO{
    private FormDetailDTO form;

    public FormDetailResponseDTO(String message) {
        super(message);
    }
}
