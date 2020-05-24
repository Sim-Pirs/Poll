package sondage.entity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sondage.entity.model.Choice;

import java.util.Collection;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender sender;


    public void sendAccessMail(String emailTo, String token, String surveyName) {
        String recoveryLink = "http://localhost:8081/sondage/repondre?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailTo);
        mail.setFrom("POLL");
        mail.setSubject("Votre accès au sondage \"" + surveyName + "\"");
        mail.setText("Veillez cliquez sur le lien pour accéder au sondage:\n" + recoveryLink);

        sender.send(mail);
    }

    public void sendRecapMail(String emailTo, Collection<Choice> choices) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailTo);
        mail.setFrom("POLL");
        mail.setSubject("Récapitulation de vos réponse à \"" + choices.iterator().next().getItem().getParent().getName() + "\"");

        StringBuilder emailBody = new StringBuilder();
        for(Choice c : choices){
            emailBody.append(c.getScore()).append(": \n");
            emailBody.append(c.getItem().getDescription()).append("\n\n");
        }

        mail.setText(emailBody.toString());
        sender.send(mail);
    }
}