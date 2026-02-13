package com.pramodvaddiraju.taskpulse_backend.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
