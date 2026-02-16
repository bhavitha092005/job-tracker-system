package com.jobtracking.service;

import com.jobtracking.entity.enums.ApplicationStatus;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // ================= CORE EMAIL METHOD =================

    private void sendEmail(String toEmail, String subject, String body) {

        System.out.println("\n====== EMAIL SENT ======");
        System.out.println("TO: " + toEmail);
        System.out.println("SUBJECT: " + subject);
        System.out.println("BODY:");
        System.out.println(body);
        System.out.println("========================\n");
    }

    // ================= APPLICATION STATUS EMAIL =================

    public void sendApplicationStatusUpdate(
            String toEmail,
            String candidateName,
            String jobTitle,
            ApplicationStatus status) {

        String subject = "Application Status Update";

        String message;

        switch (status) {

            case REVIEWED:
                message = "Your application has been successfully reviewed by our HR team.";
                break;

            case INTERVIEW:
                message = "Congratulations! You have been shortlisted for the interview stage.";
                break;

            case HIRED:
                message = "Excellent news! We are pleased to inform you that you have been selected for the position.";
                break;

            case REJECTED:
                message = "Thank you for your interest. We regret to inform you that you were not selected for this position.";
                break;

            default:
                message = "Your application status has been updated.";
        }

        String body = """
                Dear %s,

                Your application for the position:

                %s

                %s

                Thank you for using Job Tracker System.

                Best regards,
                Recruitment Team
                """.formatted(candidateName, jobTitle, message);

        sendEmail(toEmail, subject, body);
    }
}
