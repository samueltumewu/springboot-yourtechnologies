package com.yourtechnologies.yourtechnologies.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.yourtechnologies.yourtechnologies.entity.Form;
import com.yourtechnologies.yourtechnologies.entity.Question;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class QuestionResponseDTO extends BaseResponseDTO {

    private Question question;
    public QuestionResponseDTO(String message) {
        super(message);
    }
}
