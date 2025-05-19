package org.example.rabbitmqtutorial.global.exception;


public class EmailSendException extends RuntimeException {
    public EmailSendException(String message){
        super(message);
    }
}
