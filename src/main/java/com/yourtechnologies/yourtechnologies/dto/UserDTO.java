package com.yourtechnologies.yourtechnologies.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String accessToken;
}
