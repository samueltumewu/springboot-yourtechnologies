package com.yourtechnologies.yourtechnologies.controller;

import com.yourtechnologies.yourtechnologies.dto.request.UserLoginRequestDTO;
import com.yourtechnologies.yourtechnologies.dto.response.UserLoginResponseDTO;
import com.yourtechnologies.yourtechnologies.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/forms")
@RestController
public class FormController {
    @PostMapping("/test")
    public ResponseEntity<User> authenticate(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }
}
