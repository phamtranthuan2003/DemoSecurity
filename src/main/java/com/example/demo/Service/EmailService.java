package com.example.demo.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.ContactForm; 

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendContactEmail(String toEmail, ContactForm form) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Liên hệ từ website: " + form.getSubject());
        message.setText(
            "Họ tên: " + form.getFullName() + "\n" +
            "Email: " + form.getEmail() + "\n" +
            "Nội dung: \n" + form.getMessage()
        );
        mailSender.send(message);
    }
}
