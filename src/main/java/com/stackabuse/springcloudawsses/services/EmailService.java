package com.stackabuse.springcloudawsses.services;

import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class EmailService {

    @Autowired
    private MailSender mailSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AmazonSimpleEmailService simpleEmailService;

    @Autowired
    private Configuration config;

    public void sendMessage(SimpleMailMessage simpleMailMessage) {
        this.mailSender.send(simpleMailMessage);
    }

    public void sendMessageWithAttachment(SimpleMailMessage simpleMailMessage) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            // add attachment
            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            helper.setTo(Objects.requireNonNull(simpleMailMessage.getTo()));
            helper.setText(Objects.requireNonNull(simpleMailMessage.getText()));
            helper.setSubject(Objects.requireNonNull(simpleMailMessage.getSubject()));
            helper.setFrom(Objects.requireNonNull(simpleMailMessage.getFrom()));
            javaMailSender.send(message);

        } catch (MessagingException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void sendTemplateMessageWithAttachment(SimpleMailMessage simpleMailMessage) {

        try {
            MimeMessage message = javaMailSender.createMimeMessage();

            // set mediaType
            MimeMessageHelper helper = new MimeMessageHelper(
                    message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Template t = config.getTemplate("email-template.ftl");
            Map<String, Object> model = new HashMap<>();
            model.put("Name", "StackAbuse Admin");
            model.put("location", "Bangalore, India");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);

            // add attachment
            helper.addAttachment("logo.png", new ClassPathResource("logo.png"));
            helper.setTo(Objects.requireNonNull(simpleMailMessage.getTo()));
            helper.setText(html, true);
            helper.setSubject(Objects.requireNonNull(simpleMailMessage.getSubject()));
            helper.setFrom(Objects.requireNonNull(simpleMailMessage.getFrom()));
            javaMailSender.send(message);

        } catch (MessagingException | IOException | TemplateException e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }

    public void sendTemplatedMessage(SimpleMailMessage simpleMailMessage) {

        Destination destination = new Destination();
        List<String> toAddresses = new ArrayList<>();
        String[] emails = simpleMailMessage.getTo();
        Collections.addAll(toAddresses, Objects.requireNonNull(emails));
        destination.setToAddresses(toAddresses);

        SendTemplatedEmailRequest templatedEmailRequest = new SendTemplatedEmailRequest();
        templatedEmailRequest.withDestination(destination);
        templatedEmailRequest.withTemplate("MyTemplate");
        templatedEmailRequest.withTemplateData("{ \"name\":\"StackAbuse Admin\", \"location\": \"Bangalore, India\"}");
        templatedEmailRequest.withSource(simpleMailMessage.getFrom());
        simpleEmailService.sendTemplatedEmail(templatedEmailRequest);
    }
}
