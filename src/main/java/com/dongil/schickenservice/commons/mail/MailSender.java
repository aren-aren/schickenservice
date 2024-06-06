package com.dongil.schickenservice.commons.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailSender {
    private final JavaMailSender javaMailSender;
    @Value("${email.sender}")
    private String MAIL_SENDER;

    public void sendMail(String to, String content) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(MAIL_SENDER);
        helper.setTo(to);
        helper.setSubject("schicken login 인증번호");
        helper.setText(content);

        javaMailSender.send(message);
    }
}
