package com.yourtechnologies.yourtechnologies.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserLoginRequestDTO {
    private String email;
    private String password;
}
