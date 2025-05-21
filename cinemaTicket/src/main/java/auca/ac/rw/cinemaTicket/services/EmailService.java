package auca.ac.rw.cinemaTicket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

   public void sendOtpEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp + "\nIt expires in 10 minutes.");
        message.setFrom("your_email@gmail.com");

        try {
            mailSender.send(message);
        } catch (MailAuthenticationException ex) {
            throw new RuntimeException("Authentication failed while sending email");
        } catch (MailException ex) {
            throw new RuntimeException("Failed to send email: " + ex.getMessage());
        }
    }
}