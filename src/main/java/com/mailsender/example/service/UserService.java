package com.mailsender.example.service;

import com.mailsender.example.dto.RegisterRequestDto;
import com.mailsender.example.dto.RegisterResponseDto;
import com.mailsender.example.entity.User;
import com.mailsender.example.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final EmailSenderService emailSenderService;

    public UserService(UserRepository userRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public RegisterResponseDto registerUser(RegisterRequestDto userInfo) {
        User user = User.builder()
            .name(userInfo.getName())
            .mail(userInfo.getMail())
            .build();
        userRepository.save(user);
        logger.info("User registered: " + user);

        emailSenderService.sendEmailToRegisteredUserWithHTMLTemplate(userInfo.getName(), userInfo.getMail());
        return new RegisterResponseDto("You have successfully registered!");
    }
}
