package com.yourtechnologies.yourtechnologies.controller;

import com.yourtechnologies.yourtechnologies.dto.request.UserLoginRequestDTO;
import com.yourtechnologies.yourtechnologies.dto.response.BaseResponseDTO;
import com.yourtechnologies.yourtechnologies.dto.response.UserLoginResponseDTO;
import com.yourtechnologies.yourtechnologies.entity.User;
import com.yourtechnologies.yourtechnologies.service.jwt.JwtService;
import com.yourtechnologies.yourtechnologies.service.app.AuthenticationService;
import com.yourtechnologies.yourtechnologies.service.jwt.TokenBlacklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    @Autowired
    JwtService jwtService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    TokenBlacklistService tokenBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> authenticate(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        User authenticatedUser = authenticationService.authenticate(userLoginRequestDTO);
        String jwtToken = jwtService.generateToken(authenticatedUser);

        UserLoginResponseDTO userLoginResponseDTO = new UserLoginResponseDTO("Login success", authenticatedUser.getName(), authenticatedUser.getEmail(), jwtToken);
        return ResponseEntity.ok(userLoginResponseDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponseDTO> logout(@RequestHeader("Authorization") String bearerToken) {
        tokenBlacklistService.blacklistToken(bearerToken.split("Bearer ")[1]);
        BaseResponseDTO baseResponseDTO = new BaseResponseDTO("logout success");
        return ResponseEntity.ok(baseResponseDTO);
    }
}
