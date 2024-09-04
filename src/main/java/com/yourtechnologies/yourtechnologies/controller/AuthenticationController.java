package com.yourtechnologies.yourtechnologies.controller;

import com.yourtechnologies.yourtechnologies.dto.request.UserLoginRequestDTO;
import com.yourtechnologies.yourtechnologies.dto.response.LoginResponseDTO;
import com.yourtechnologies.yourtechnologies.dto.response.UserLoginResponseDTO;
import com.yourtechnologies.yourtechnologies.entity.User;
import com.yourtechnologies.yourtechnologies.service.jwt.JwtService;
import com.yourtechnologies.yourtechnologies.service.jwt.app.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> authenticate(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        User authenticatedUser = authenticationService.authenticate(userLoginRequestDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO("Login success", authenticatedUser.getName(), authenticatedUser.getEmail(), jwtToken);
        return ResponseEntity.ok(userLoginResponseDTO);
    }
}
