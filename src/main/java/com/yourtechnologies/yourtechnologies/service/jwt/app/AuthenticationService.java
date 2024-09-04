package com.yourtechnologies.yourtechnologies.service.jwt.app;

import com.yourtechnologies.yourtechnologies.dto.request.UserLoginRequestDTO;
import com.yourtechnologies.yourtechnologies.dto.response.UserLoginResponseDTO;
import com.yourtechnologies.yourtechnologies.entity.User;
import com.yourtechnologies.yourtechnologies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    public User authenticate(UserLoginRequestDTO userLogin) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLogin.getEmail(),
                        userLogin.getPassword()
                )
        );

        return userRepository.findByEmail(userLogin.getEmail())
                .orElseThrow();

    }
}
