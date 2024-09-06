package com.yourtechnologies.yourtechnologies.service.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourtechnologies.yourtechnologies.dto.FormDTO;
import com.yourtechnologies.yourtechnologies.dto.FormDetailDTO;
import com.yourtechnologies.yourtechnologies.dto.QuestionDTO;
import com.yourtechnologies.yourtechnologies.dto.QuestionWithChoicesDTO;
import com.yourtechnologies.yourtechnologies.dto.response.*;
import com.yourtechnologies.yourtechnologies.entity.Form;
import com.yourtechnologies.yourtechnologies.entity.Question;
import com.yourtechnologies.yourtechnologies.entity.User;
import com.yourtechnologies.yourtechnologies.exceptionshandler.YourTechnologiesCustomException;
import com.yourtechnologies.yourtechnologies.repository.FormRepository;
import com.yourtechnologies.yourtechnologies.repository.QuestionRepository;
import com.yourtechnologies.yourtechnologies.repository.UserRepository;
import com.yourtechnologies.yourtechnologies.service.jwt.JwtService;
import com.yourtechnologies.yourtechnologies.util.UtilGeneral;
import jakarta.transaction.Transactional;
import jakarta.validation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FormService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FormRepository formRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    ModelMapper modelMapper;

    @Transactional
    public FormResponseDTO createForm(FormDTO formDTO, String token) throws JsonProcessingException {
        FormResponseDTO formResponseDTO = new FormResponseDTO("");

        // retrieve user id based on token username
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
                .orElseThrow();
        Long userId = user.getId();

        //read dto value as a class
        Form form = Form.builder()
                .slug(formDTO.getSlug())
                .name(formDTO.getName())
                .description(formDTO.getDescription())
                .limitOneResponse(formDTO.getLimitOneResponse()?1:0)
                .creatorId(userId).build();
        form.setAllowDomainListToString(formDTO.getAllowedDomains());
        Form saveForm = formRepository.save(form);

        FormDTO formDtoMapped = modelMapper.map(saveForm, FormDTO.class);
        formDtoMapped.setAllowedDomains(formDTO.getAllowedDomains());
        formResponseDTO.setForm(formDtoMapped);
        formResponseDTO.setMessage("Create form success");
        return formResponseDTO;
    }

    public FormListResponseDTO getForm(String token) {
        FormListResponseDTO response = new FormListResponseDTO("");

        // retrieve user id based on token username
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
                .orElseThrow();
        Long userId = user.getId();

        //get all forms
        List<Form> formList = formRepository.findByCreatorId(userId);
        List<FormDTO> formDTOList = formList.stream()
                .map(formStream -> {
                    FormDTO formDto = modelMapper.map(formStream, FormDTO.class);
                    try {
                        formDto.setAllowedDomains(formStream.getAllowDomainStringToList());
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return formDto;
                })
                .toList();

        response.setMessage(!formList.isEmpty() ? "Get all forms success" : "forms not found");
        response.setForms(formDTOList);
        return response;
    }

    public FormDetailResponseDTO getFormDetail(String token, String formSlug) {
        // retrieve user id based on token username
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
                .orElseThrow();
        Long userId = user.getId();

        //get all forms
        Form form = formRepository.findBySlugAndCreatorId(formSlug, userId);
        if (form == null) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("form_slug", formSlug);
            errorDetails.put("user",user.getName());
            throw new YourTechnologiesCustomException("Form not found!", errorDetails, HttpStatus.NOT_FOUND);
        }

        //map to DTO
        FormDetailDTO formDetailDTO = modelMapper.map(form, FormDetailDTO.class);
        List<Question> questionList = questionRepository.findByFormId(form.getId());
        List<QuestionWithChoicesDTO> questionWithChoicesDTO = questionList.stream()
                .map(q -> {
                            QuestionWithChoicesDTO qDto = modelMapper.map(q, QuestionWithChoicesDTO.class);
                            try {
                                qDto.setChoices(q.getChoicesStringToList());
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                            return qDto;
                        }
                )
                .toList();

        formDetailDTO.setQuestions(questionWithChoicesDTO);

        FormDetailResponseDTO response = new FormDetailResponseDTO("Get form success");
        response.setForm(formDetailDTO);
        return response;
    }

    @Transactional
    public QuestionResponseDTO addQuestion(String jsonString, String formSlug, String token) throws Exception {
        // retrieve user id based on token username
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
                .orElseThrow();
        Long userId = user.getId();

        Form form = formRepository.findBySlugAndCreatorId(formSlug, userId);
        if (form == null) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("form_slug", formSlug);
            errorDetails.put("user",user.getName());
            throw new YourTechnologiesCustomException("Form not found!", errorDetails, HttpStatus.NOT_FOUND);
        }
        JsonNode jsonNode = objectMapper.readTree(jsonString);

        QuestionDTO questionDTO = null;
        QuestionWithChoicesDTO questionWithChoicesDTO = null;

        Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();


        boolean[] fieldBooleans = UtilGeneral.checkJsonChoicesAndChoiceType(fields);
        boolean mustHaveChoices = fieldBooleans[0];
        boolean choicesExist = fieldBooleans[1];
        boolean isChoiceTypeCorrect = fieldBooleans[2];
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Map<String, String> errorDetails = new HashMap<>();
        if (isChoiceTypeCorrect && !mustHaveChoices) {
            questionDTO = objectMapper.readValue(jsonString, QuestionDTO.class);
            System.out.println(questionDTO.getChoiceType()
                    + " " + questionDTO.getName());

            Set<ConstraintViolation<QuestionDTO>> violations = validator.validate(questionDTO);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        } else if (choicesExist && isChoiceTypeCorrect) {
            questionWithChoicesDTO = objectMapper.readValue(jsonString, QuestionWithChoicesDTO.class);
            System.out.println(questionWithChoicesDTO.getChoiceType()
                    + " " + questionWithChoicesDTO.getChoices());

            Set<ConstraintViolation<QuestionWithChoicesDTO>> violations = validator.validate(questionWithChoicesDTO);
            if (!violations.isEmpty()) {
                throw new ConstraintViolationException(violations);
            }
        } else {
            if (mustHaveChoices && !choicesExist)
                errorDetails.put("choices", "Required in array of string");
            if (!isChoiceTypeCorrect)
                errorDetails.put("choice_type", "Required with values['multiple choice'" +
                        ",'dropdown'" +
                        ",'checkboxes'" +
                        ",'short answer'" +
                        ",'paragraph'" +
                        ",'date']");
            throw new YourTechnologiesCustomException("Validation failed", errorDetails, HttpStatus.BAD_REQUEST);
        }

        QuestionResponseDTO response = new QuestionResponseDTO("");
        if (questionDTO != null) {
            Question questionInsert = Question.builder()
                    .name(questionDTO.getName())
                    .choiceType(questionDTO.getChoiceType())
                    .isRequired(questionDTO.getIsRequired()?1:0)
                    .form(form)
                    .build();
            Question question = questionRepository.save(questionInsert);
            response.setQuestion(question);
        } else if (questionWithChoicesDTO != null) {
            Question questionInsert = Question.builder()
                    .name(questionWithChoicesDTO.getName())
                    .choiceType(questionWithChoicesDTO.getChoiceType())
                    .isRequired(questionWithChoicesDTO.getIsRequired()?1:0)
                    .form(form)
                    .build();
            questionInsert.setChoicesListToString(questionWithChoicesDTO.getChoices());
            Question question = questionRepository.save(questionInsert);
            response.setQuestion(question);
        }

        return response;
    }

    @Transactional
    public BaseResponseDTO removeQuestion(String formSlug, Long questionId, String token) {

        //check form existence
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
                .orElseThrow();
        Long userId = user.getId();
        Form form = formRepository.findBySlugAndCreatorId(formSlug, userId);
        if (form == null) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("form_slug", formSlug);
            errorDetails.put("user",user.getName());
            throw new YourTechnologiesCustomException("Form not found!", errorDetails, HttpStatus.NOT_FOUND);
        }

        //delete by questionId
        if (questionRepository.findByFormIdAndId(form.getId(), questionId).isEmpty()) {
            Map<String, String> errorDetails = new HashMap<>();
            errorDetails.put("form_slug", formSlug);
            errorDetails.put("user",user.getName());
            throw new YourTechnologiesCustomException("Question not found!", errorDetails, HttpStatus.NOT_FOUND);
        }
        questionRepository.deleteByIdAndFormId(questionId, form.getId());

        //return response
        return new BaseResponseDTO("Remove question success");
    }
}
