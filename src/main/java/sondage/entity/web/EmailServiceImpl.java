package sondage.entity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender sender;


    public void sendAccessMail(String emailTo, String token, String surveyName) {
        String recoveryLink = "http://localhost:8081/sondage/repondre?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailTo);
        mail.setFrom("POLL");
        mail.setSubject("Mise à jour de l'acces au sondage \"" + surveyName + "\"");
        mail.setText("Veillez cliquez sur le lien pour accéder au sondage:  \n \n" + recoveryLink);

        sender.send(mail);
    }
}