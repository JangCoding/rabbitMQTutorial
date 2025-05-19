package org.example.rabbitmqtutorial.rabbitMQ;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rabbitmqtutorial.global.exception.EmailSendException;
import org.example.rabbitmqtutorial.rabbitMQ.dto.EmailPayload;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private JavaMailSender javaMailSender;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void receiveMessage(EmailPayload payload){
        String to = payload.getTo();
        String subject = payload.getSubject();
        String text = payload.getText();

        SimpleMailMessage message = new SimpleMailMessage(); // Email 객체 생성
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        try{
            javaMailSender.send(message);
            log.info("이메일 발송 성공 : {}", to);
        } catch(Exception e){
            throw new EmailSendException("이메일 발송 실패"+e.getMessage());
        }
    }
}
