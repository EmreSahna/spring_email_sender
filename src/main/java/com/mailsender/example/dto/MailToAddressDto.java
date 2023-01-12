package com.mailsender.example.dto;

import lombok.Data;

@Data
public class MailToAddressDto {
    private String toAddress;
    private String subject;
    private String message;
}
