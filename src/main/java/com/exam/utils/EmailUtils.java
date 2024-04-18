//package com.exam.utils;
//
//import jakarta.mail.MessagingException;
//import jakarta.mail.internet.MimeMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@RequiredArgsConstructor
//@Service
//public class EmailUtils {
//    private final JavaMailSender javaMailSender;
//    public void sendPasswordToEmail(String toEmail, String subject, String password) throws MessagingException {
//        System.out.println("admin123");
//        String htmlMsg = "<p><b>Your Login details for Online Exam</b><br><b>Email: </b> " + toEmail + " <br><b>Password: </b> " + password + "<br><a href=\"http://localhost:4200/\">Click here to login</a></p>";
//
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true);
//        mimeMessageHelper.setFrom("tranhau.010120199@gmail.com");
//        mimeMessageHelper.setTo(toEmail);
//        mimeMessageHelper.setSubject(subject);
//        message.setContent(htmlMsg, "text/html");
//
//        System.out.println("messages: " + message);
//
//        javaMailSender.send(message);
//    }
//}
