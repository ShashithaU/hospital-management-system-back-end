package edu.icet.service.impl;

import edu.icet.dto.Appointment;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.email.appointment.subject}")
    private String appointmentSubject;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PdfGeneratorService pdfGeneratorService;

    public void sendAppointmentConfirmation(Appointment appointment) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            // Generate PDF
            byte[] pdfBytes = pdfGeneratorService.generateAppointmentPdf(appointment);

            // Set email details
            helper.setFrom(fromEmail);
            helper.setTo(appointment.getEmail());
            helper.setSubject(appointmentSubject);

            // HTML email content
            String htmlContent = buildEmailContent(appointment);
            helper.setText(htmlContent, true);

            // Attach PDF
            helper.addAttachment("Appointment_Confirmation.pdf", new ByteArrayResource(pdfBytes));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send appointment confirmation email", e);
        }
    }

    private String buildEmailContent(Appointment appointment) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <style>" +
                "        body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                "        .container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                "        .header { background-color: #4a90e2; color: white; padding: 20px; text-align: center; }" +
                "        .content { padding: 20px; }" +
                "        .footer { background-color: #f5f5f5; padding: 10px; text-align: center; font-size: 12px; }" +
                "        .details { margin: 20px 0; }" +
                "        .details table { width: 100%; border-collapse: collapse; }" +
                "        .details th { background-color: #f2f2f2; text-align: left; padding: 8px; }" +
                "        .details td { padding: 8px; border-bottom: 1px solid #ddd; }" +
                "        .button { background-color: #4a90e2; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; display: inline-block; }" +
                "    </style>" +
                "</head>" +
                "<body>" +
                "    <div class='container'>" +
                "        <div class='header'>" +
                "            <h1>Appointment Confirmation</h1>" +
                "        </div>" +
                "        <div class='content'>" +
                "            <p>Dear " + appointment.getPatientId() + ",</p>" +
                "            <p>Your appointment has been scheduled successfully. Please find the details below:</p>" +
                "            " +
                "            <div class='details'>" +
                "                <table>" +
                "                    <tr><th>Doctor:</th><td>" + appointment.getPatientId() + "</td></tr>" +
                "                    <tr><th>Date:</th><td>" + appointment.getPatientId() + "</td></tr>" +
                "                    <tr><th>Time:</th><td>" + appointment.getPatientId() + "</td></tr>" +
                "                    <tr><th>Department:</th><td>" + appointment.getPatientId() + "</td></tr>" +
                "                    <tr><th>Reference ID:</th><td>" + appointment.getPatientId() + "</td></tr>" +
                "                </table>" +
                "            </div>" +
                "            " +
                "            <p>We've attached a PDF copy of this confirmation for your records.</p>" +
                "            " +
                "            <p>Please arrive 15 minutes before your scheduled time.</p>" +
                "            " +
                "            <p>If you need to reschedule or cancel your appointment, please contact us at appointments@yourhospital.com or call (123) 456-7890.</p>" +
                "        </div>" +
                "        <div class='footer'>" +
                "            <p>© 2023 Your Hospital Name. All rights reserved.</p>" +
                "            <p>123 Hospital Street, City, State ZIP</p>" +
                "        </div>" +
                "    </div>" +
                "</body>" +
                "</html>";
    }
}


//ref : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/mail/javamail/MimeMessageHelper.html