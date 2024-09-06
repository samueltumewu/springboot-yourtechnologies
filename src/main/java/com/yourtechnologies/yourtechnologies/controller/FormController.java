package com.yourtechnologies.yourtechnologies.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yourtechnologies.yourtechnologies.dto.FormDTO;
import com.yourtechnologies.yourtechnologies.dto.request.AnswerReqDTO;
import com.yourtechnologies.yourtechnologies.dto.response.*;
import com.yourtechnologies.yourtechnologies.service.app.FormService;
import com.yourtechnologies.yourtechnologies.service.app.ResponseService;
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
    @Autowired
    ResponseService responseService;

    @PostMapping("")
    public ResponseEntity<FormResponseDTO> createForm(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @Valid @RequestBody FormDTO formDTO
    ) throws JsonProcessingException {
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

    @GetMapping("/{formSlug}")
    public ResponseEntity<FormDetailResponseDTO> getDetailForms(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String formSlug
    ) {
        String token = authorizationHeader.split("Bearer ")[1];
        FormDetailResponseDTO returnBody = formService.getFormDetail(token, formSlug);
        return ResponseEntity.ok(returnBody);
    }

    @PostMapping("/{formSlug}/questions")
    public ResponseEntity<QuestionResponseDTO> addQuestion(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody String jsonString,
            @PathVariable String formSlug
    ) throws Exception {
        String token = authorizationHeader.split("Bearer ")[1];
        QuestionResponseDTO returnBody = formService.addQuestion(jsonString, formSlug, token);
        return ResponseEntity.ok(returnBody);
    }

    @DeleteMapping("/{formSlug}/questions/{questionId}")
    public ResponseEntity<BaseResponseDTO> deleteQuestion(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @PathVariable String formSlug,
            @PathVariable Long questionId
    ) {
        String token = authorizationHeader.split("Bearer ")[1];
        BaseResponseDTO returnBody = formService.removeQuestion(formSlug, questionId, token);
        return ResponseEntity.ok(returnBody);
    }

    @PostMapping("/{formSlug}/responses")
    public ResponseEntity<BaseResponseDTO> addResponses(
            @RequestHeader(value = "Authorization") String authorizationHeader,
            @RequestBody AnswerReqDTO answerReqDto,
            @PathVariable String formSlug
    ) throws Exception {
        String token = authorizationHeader.split("Bearer ")[1];
        responseService.addResponse(token, formSlug, answerReqDto);
        return ResponseEntity.ok(new BaseResponseDTO("ok"));
    }
}
