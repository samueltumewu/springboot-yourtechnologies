package com.yourtechnologies.yourtechnologies.controller;

import com.yourtechnologies.yourtechnologies.dto.FormDTO;
import com.yourtechnologies.yourtechnologies.dto.request.UserLoginRequestDTO;
import com.yourtechnologies.yourtechnologies.dto.response.FormResponseDTO;
import com.yourtechnologies.yourtechnologies.dto.response.UserLoginResponseDTO;
import com.yourtechnologies.yourtechnologies.entity.User;
import com.yourtechnologies.yourtechnologies.service.app.FormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/forms")
@RestController
public class FormController {
    @Autowired
    FormService formService;

    @PostMapping("")
    public ResponseEntity<FormResponseDTO> createForm(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @Valid @RequestBody FormDTO formDTO
    ) {
        String token = authorizationHeader.split("Bearer ")[1].trim();
        FormResponseDTO returnBody = formService.createForm(formDTO, token);
//        FormResponseDTO formResponseDTO = new FormResponseDTO("success");
//        formResponseDTO.setForm(formDTO);
        return ResponseEntity.ok(returnBody);
    }
}
