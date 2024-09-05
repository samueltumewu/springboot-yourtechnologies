package com.yourtechnologies.yourtechnologies.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yourtechnologies.yourtechnologies.dto.FormDTO;
import com.yourtechnologies.yourtechnologies.dto.response.BaseResponseDTO;
import com.yourtechnologies.yourtechnologies.dto.response.FormListResponseDTO;
import com.yourtechnologies.yourtechnologies.dto.response.FormResponseDTO;
import com.yourtechnologies.yourtechnologies.dto.response.QuestionResponseDTO;
import com.yourtechnologies.yourtechnologies.service.app.FormService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
        String token = authorizationHeader.split("Bearer ")[1];
        FormResponseDTO returnBody = formService.createForm(formDTO, token);
        return ResponseEntity.ok(returnBody);
    }

    @GetMapping("")
    public ResponseEntity<FormListResponseDTO> getFormByCreatorId(
            @RequestHeader(value = "Authorization") String authorizationHeader
    ) {
        String token = authorizationHeader.split("Bearer ")[1];
        FormListResponseDTO returnBody = formService.getForm(token);
        return ResponseEntity.ok(returnBody);
    }

    @PostMapping("/{formSlug}/questions")
    public ResponseEntity<QuestionResponseDTO> addQuestion(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody String jsonString,
            @PathVariable String formSlug
    ) throws Exception {
        QuestionResponseDTO returnBody = formService.addQuestion(jsonString, formSlug);
        return ResponseEntity.ok(returnBody);
    }

    @DeleteMapping("/{formSlug}/questions/{questionId}")
    public ResponseEntity<BaseResponseDTO> deleteQuestion(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String formSlug,
            @PathVariable Long questionId
    ) {
        BaseResponseDTO returnBody = formService.removeQuestion(formSlug, questionId);
        return ResponseEntity.ok(returnBody);
    }
}
