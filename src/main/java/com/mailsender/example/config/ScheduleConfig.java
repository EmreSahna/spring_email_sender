package com.mailsender.example.config;

import com.mailsender.example.dto.MailToListDto;
import com.mailsender.example.service.EmailSenderService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    private final EmailSenderService emailSenderService;

    public ScheduleConfig(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @Scheduled(cron = "0 0 9 ? * MON") // 9:00 AM every Monday
    public void sendEmails() {
        MailToListDto mailToListDto = new MailToListDto();
        mailToListDto.setSubject("Test");
        mailToListDto.setMessage("Test message " + new Date());
        emailSenderService.sendEmailList(mailToListDto);
    }
}
