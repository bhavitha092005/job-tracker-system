package com.jobtracking.service;

import com.jobtracking.entity.enums.ApplicationStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    private void sendEmail(String toEmail, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendApplicationStatusUpdate(
            String toEmail,
            String candidateName,
            String jobTitle,
            ApplicationStatus status) {

        String subject = "Application Status Update";

        String message;

        switch (status) {

            case REVIEWED:
                message = "Your application has been reviewed by our HR team.";
                break;

            case INTERVIEW:
                message = "Congratulations! You have been shortlisted for the interview stage.";
                break;

            case HIRED:
                message = "Excellent news! You have been selected for the position.";
                break;

            case REJECTED:
                message = "Thank you for applying. We regret to inform you that you were not selected.";
                break;

            default:
                message = "Your application status has been updated.";
        }

        String body = """
                Dear %s,

                Your application for the position:

                %s

                %s

                Best regards,
                Recruitment Team
                """.formatted(candidateName, jobTitle, message);

        sendEmail(toEmail, subject, body);
    }
}
