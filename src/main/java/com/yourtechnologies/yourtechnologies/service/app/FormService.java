package com.yourtechnologies.yourtechnologies.service.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yourtechnologies.yourtechnologies.dto.FormDTO;
import com.yourtechnologies.yourtechnologies.dto.response.FormResponseDTO;
import com.yourtechnologies.yourtechnologies.entity.User;
import com.yourtechnologies.yourtechnologies.entity.Form;
import com.yourtechnologies.yourtechnologies.middleware.JWTAuthFilter;
import com.yourtechnologies.yourtechnologies.repository.FormRepository;
import com.yourtechnologies.yourtechnologies.repository.UserRepository;
import com.yourtechnologies.yourtechnologies.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FormService {
    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FormRepository formRepository;

    @Transactional
    public FormResponseDTO createForm(FormDTO formDTO, String token) {
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

        Form saveForm = formRepository.save(form);

        formResponseDTO.setForm(saveForm);
        formResponseDTO.setMessage("Create form success");
        return formResponseDTO;
    }
}
