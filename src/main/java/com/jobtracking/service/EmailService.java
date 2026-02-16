package com.jobtracking.service;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    private void sendEmail(String toEmail, String subject, String body) {

        try {
            Email from = new Email(fromEmail);
            Email to = new Email(toEmail);

            Content content = new Content("text/plain", body);

            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(apiKey);
            Request request = new Request();

            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            Response response = sg.api(request);

            System.out.println("SENDGRID STATUS: " + response.getStatusCode());

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Failed to send email");
        }
    }

    public void sendApplicationStatusUpdate(
            String toEmail,
            String candidateName,
            String jobTitle,
            Object status) {

        String subject = "Application Status Update";

        String body = """
                Dear %s,

                Your application for:

                %s

                Current Status: %s

                Best regards,
                Recruitment Team
                """.formatted(candidateName, jobTitle, status);

        sendEmail(toEmail, subject, body);
    }
}
