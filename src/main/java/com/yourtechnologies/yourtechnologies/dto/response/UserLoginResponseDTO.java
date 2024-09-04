package com.yourtechnologies.yourtechnologies.dto.response;

import com.yourtechnologies.yourtechnologies.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginResponseDTO extends baseResponseDTO{
    private String message;
    private UserDTO userDTO ;

    public UserLoginResponseDTO(String message, String name, String email, String accessToken) {
        super(message);
        this.userDTO = new UserDTO(name, email, accessToken);
    }
}
