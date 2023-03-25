package com.mailsender.example.service;

import com.mailsender.example.dto.MailToListDto;
import com.mailsender.example.dto.MailToAddressDto;
import com.mailsender.example.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final TemplateEngine templateEngine;

    public EmailSenderService(JavaMailSender mailSender, UserRepository userRepository, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.userRepository = userRepository;
        this.templateEngine = templateEngine;
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

        userRepository.findAll().stream().forEach(user -> {
                mailMessage.setTo(user.getMail());
                mailMessage.setSubject(mailToListDto.getSubject());
                mailMessage.setText(mailToListDto.getMessage());
                mailSender.send(mailMessage);
            }
        );
    }

    public void sendEmailToRegisteredUserWithHTMLTemplate(String name, String mail) {
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(mail);
            helper.setSubject("Thank you for registering!");

            Context context = new Context();
            context.setVariable("username", name);
            String htmlBody = templateEngine.process("register_template", context);
            helper.setText(htmlBody, true);

            mailSender.send(message);
            logger.info("Email sent to: " + mail);
        } catch (MessagingException e) {
            logger.error("Error while sending email: ", e);
        }
    }

}
