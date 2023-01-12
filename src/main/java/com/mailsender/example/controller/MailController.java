package com.mailsender.example.controller;

import com.mailsender.example.dto.MailToListDto;
import com.mailsender.example.dto.MailToAddressDto;
import com.mailsender.example.service.EmailSenderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final EmailSenderService emailSenderService;

    public MailController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/send")
    public void sendMail(@RequestBody MailToAddressDto mailToAddressDto) {
        emailSenderService.sendEmail(mailToAddressDto);
    }

    @PostMapping("/send/all")
    public void sendMail(@RequestBody MailToListDto mailToListDto) {
        emailSenderService.sendEmailList(mailToListDto);
    }
}
