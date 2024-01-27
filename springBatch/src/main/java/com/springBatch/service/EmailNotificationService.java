package com.springBatch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailNotificationService {

    // TODO: Enviar notificação mensal todo vez q estiver próximo do vencimento do financiamento, OBS, sugestões de melhorias

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void sendEmailNotification(String toAddress, String subject, String body, String attachmentPath) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toAddress);
        message.setSubject(subject);
        message.setText(body);

        // Adicionar anexo (se necessário)
        // ...

        try {
            javaMailSender.send(message);
            log.info("Email sent successfully to: " + toAddress);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("Error to send email to: " + toAddress);
        }
    }
}
