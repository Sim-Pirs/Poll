package sondage.entity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import sondage.entity.model.Choice;
import sondage.entity.model.Respondent;

import java.util.Collection;

@Component
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender sender;


    /**
     * Envoi un mail contenant un lien pour accéder à un sondage.
     * @param emailTo Email du destinataire.
     * @param token Token nécessaire pour l'accés au sondage.
     * @param surveyName Nom du sondage.
     */
    public void sendAccessMail(String emailTo, String token, String surveyName) {
        String recoveryLink = "http://localhost:8081/sondage/repondre?token=" + token;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailTo);
        mail.setFrom("POLL");
        mail.setSubject("Votre accès au sondage \"" + surveyName + "\"");
        mail.setText("Veillez cliquez sur le lien pour accéder au sondage:\n" + recoveryLink);

        sender.send(mail);
    }

    /**
     * Envoi un mail contenant un récapitulatif des choix d'un sondage.
     * @param emailTo Email du destinataire.
     * @param choices Liste des choix.
     */
    public void sendRecapMail(String emailTo, Collection<Choice> choices) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailTo);
        mail.setFrom("POLL");
        mail.setSubject("Récapitulatif de vos réponse au sondage \"" + choices.iterator().next().getItem().getParent().getName() + "\"");

        StringBuilder emailBody = new StringBuilder();
        for(Choice c : choices){
            emailBody.append(c.getScore()).append(": \n");
            emailBody.append(c.getItem().getDescription()).append("\n\n");
        }

        mail.setText(emailBody.toString());
        sender.send(mail);
    }

    /**
     * Envoi un mail contenant l'affectation final pour un sondage.
     * @param respondent Personne sondé.
     */
    public void sendFinalMail(Respondent respondent){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(respondent.getEmail());
        mail.setFrom("POLL");
        mail.setSubject("Affectation final du sondage \"" + respondent.getFinalItem().getParent().getName() + "\"");

        String emailBody = "L'option retenue est la suivant: \n" + respondent.getFinalItem().getDescription();
        mail.setText(emailBody);

        sender.send(mail);
    }
}