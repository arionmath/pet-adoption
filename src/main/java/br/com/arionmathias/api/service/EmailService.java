package br.com.arionmathias.api.service;

public interface EmailService {

    void enviarEmail(String to, String subject, String message);

}
