package com.mailsender.example.service;

import com.mailsender.example.dto.MailToListDto;
import com.mailsender.example.dto.MailToAddressDto;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    private final JavaMailSender mailSender;
    private final UserService userService;

    public EmailSenderService(JavaMailSender mailSender, UserService userService) {
        this.mailSender = mailSender;
        this.userService = userService;
    }

    public void sendEmail(MailToAddressDto mailToAddressDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailToAddressDto.getToAddress());
        mailMessage.setSubject(mailToAddressDto.getSubject());
        mailMessage.setText(mailToAddressDto.getMessage());
        mailSender.send(mailMessage);
    }

    public void sendEmailList(MailToListDto mailToListDto) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        userService.getAllUsers().stream().forEach(user -> {
                mailMessage.setTo(user.getMail());
                mailMessage.setSubject(mailToListDto.getSubject());
                mailMessage.setText(mailToListDto.getMessage());
                mailSender.send(mailMessage);
            }
        );
    }

}
