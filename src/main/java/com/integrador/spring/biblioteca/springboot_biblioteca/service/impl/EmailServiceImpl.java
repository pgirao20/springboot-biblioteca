<<<<<<< HEAD
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;

=======
// src/main/java/com/integrador/spring/biblioteca/springboot_biblioteca/service/impl/EmailServiceImpl.java
package com.integrador.spring.biblioteca.springboot_biblioteca.service.impl;
>>>>>>> 8a693b5 (prestamos v2)
import com.integrador.spring.biblioteca.springboot_biblioteca.model.Estudiante;
import com.integrador.spring.biblioteca.springboot_biblioteca.service.EmailService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.from:no-reply@biblioteca.edu}")
    private String from;

    @Override
    public void enviarCorreoPrestamo(Estudiante estudiante,
                                     String tipo,
                                     LocalDate fechaPrestamo,
                                     LocalDate fechaDevolucion,
                                     List<String> items) {

        if (estudiante.getCorreo() == null || estudiante.getCorreo().isBlank()) {
            return;
        }

        String asunto = "Confirmación de préstamo de " + (tipo.equals("LIBRO") ? "libros" : "tablets");
        String destinatario = estudiante.getCorreo();

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        StringBuilder cuerpo = new StringBuilder();
        cuerpo.append("<p>Hola <b>").append(estudiante.getNombres()).append(" ").append(estudiante.getApellidos()).append("</b>,</p>");
        cuerpo.append("<p>Tu préstamo fue registrado correctamente.</p>");
        cuerpo.append("<p><b>Fecha de préstamo:</b> ").append(fechaPrestamo.format(fmt)).append("<br>");
        cuerpo.append("<b>Fecha de devolución:</b> ").append(fechaDevolucion.format(fmt)).append("</p>");
        cuerpo.append("<p><b>Ítems prestados:</b></p><ul>");

        for (String it : items) {
            cuerpo.append("<li>").append(it).append("</li>");
        }
        cuerpo.append("</ul>");
        cuerpo.append("<p>Por favor devuelve los materiales a tiempo.</p>");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(from);
            helper.setTo(destinatario);
            helper.setSubject(asunto);
            helper.setText(cuerpo.toString(), true);

            mailSender.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
