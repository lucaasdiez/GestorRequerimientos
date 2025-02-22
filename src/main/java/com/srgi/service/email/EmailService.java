package com.srgi.service.email;

public interface EmailService {
    void enviarCorreo(String destinatario, String asunto, String cuerpo);
}
