package com.stackabuse.springcloudawsses.controller;

import com.stackabuse.springcloudawsses.dto.EmailDetails;
import com.stackabuse.springcloudawsses.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public String sendMessage(@RequestBody EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailDetails.getFromEmail());
        simpleMailMessage.setTo(emailDetails.getToEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getBody());
        emailService.sendMessage(simpleMailMessage);

        return "Email sent successfully";
    }

    @PostMapping("/sendEmailWithAttachment")
    public String sendMessageWithAttachment(@RequestBody EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailDetails.getFromEmail());
        simpleMailMessage.setTo(emailDetails.getToEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getBody());
        emailService.sendMessageWithAttachment(simpleMailMessage);

        return "Email sent successfully";
    }

    @PostMapping("/sendTemplateEmailWithAttachment")
    public String sendTemplateMessageWithAttachment(@RequestBody EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailDetails.getFromEmail());
        simpleMailMessage.setTo(emailDetails.getToEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getBody());
        emailService.sendTemplateMessageWithAttachment(simpleMailMessage);

        return "Email sent successfully";
    }

    @PostMapping("/sendAWSTemplatedEmail")
    public String sendTemplatedMessage(@RequestBody EmailDetails emailDetails) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(emailDetails.getFromEmail());
        simpleMailMessage.setTo(emailDetails.getToEmail());
        simpleMailMessage.setSubject(emailDetails.getSubject());
        simpleMailMessage.setText(emailDetails.getBody());
        emailService.sendTemplatedMessage(simpleMailMessage);

        return "Email sent successfully";
    }
}
