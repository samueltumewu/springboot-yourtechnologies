package com.yourtechnologies.yourtechnologies.service.app;

import com.yourtechnologies.yourtechnologies.dto.request.AnswerReqDTO;
import com.yourtechnologies.yourtechnologies.dto.request.AnswerReqDetailDTO;
import com.yourtechnologies.yourtechnologies.entity.*;
import com.yourtechnologies.yourtechnologies.exceptionshandler.YourTechnologiesCustomException;
import com.yourtechnologies.yourtechnologies.repository.*;
import com.yourtechnologies.yourtechnologies.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ResponseService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    FormRepository formRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    ResponseRepository responseRepository;
    @Autowired
    JwtService jwtService;

    public void addResponse(String token, String formSlug, AnswerReqDTO answerReqDTO) {
        // retrieve user id based on token username
        User user = userRepository.findByEmail(jwtService.extractUsername(token))
                .orElseThrow();
        Long userId = user.getId();
        // retrive form
        Form form = formRepository.findBySlugAndCreatorId(formSlug, userId);
        // retrive questions
        List<Question> questionList = questionRepository.findByFormId(form.getId());

        //validate answer if required
        Map<Long, Boolean> questionListMap = new HashMap<>();
        for (Question question : questionList) {
            questionListMap.put(question.getId(), question.getIsRequired()==1);
        }
        Map<String, String> errorDetailsAnswerValueValidation = new HashMap<>();
        answerReqDTO.getAnswers().stream().filter(Objects::nonNull)
                .forEach(answerDetail -> {
                    boolean valueIsNullOrBlank = answerDetail.getValue().equalsIgnoreCase("");
                    boolean isRequired = false;
                    if (questionListMap.get(answerDetail.getQuestionId()) != null)
                        isRequired = questionListMap.get(answerDetail.getQuestionId());
                    if (isRequired && valueIsNullOrBlank){
                        errorDetailsAnswerValueValidation.put("question_id: "+answerDetail.getQuestionId(), "value is required");
                    } else {
                    }
                });
        if (!errorDetailsAnswerValueValidation.isEmpty())
            throw new YourTechnologiesCustomException("Invalid field!", errorDetailsAnswerValueValidation, HttpStatus.UNPROCESSABLE_ENTITY);

        Response response = new Response();
        response.setDate(LocalDateTime.now());
        response.setUser(user);
        response.setForm(form);
        Response savedResp = responseRepository.save(response);

        List<Answer> answerList = new ArrayList<>();
        //TODO: still not working
        for (AnswerReqDetailDTO answerReqDetailDTO : answerReqDTO.getAnswers()) {
            Answer answer = new Answer();
            answer.setResponse(savedResp);
            answer.setQuestion(questionList.stream()
                    .filter(q -> q.getId() == answerReqDetailDTO.getQuestionId()).findFirst().orElse(null)
            );
            answer.setValue(answerReqDetailDTO.getValue());
            answerList.add(answer);

        }
        answerRepository.saveAll(answerList);
    }
}
