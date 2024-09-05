package com.yourtechnologies.yourtechnologies.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponseDTO extends BaseResponseDTO{
    private Object errors;
    public ErrorResponseDTO(String message) {
        super(message);
    }
}
