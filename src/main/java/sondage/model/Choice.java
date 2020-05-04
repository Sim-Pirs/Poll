package sondage.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Représente la réponse à un sondage par un sondé.
 */
@Entity
public class Choice {

    /**
     * Idendifiant unique de la réponse du sondé.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Sondage auquel appartient la réponse.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "survey")
    private Survey survey;

    /**
     * Sondé en question.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "respondent")
    private Respondent respondent;

    /**
     * Liste des réponses du sondé. (doit y avoir autant que réponse que de choix possible
     * car il s'agit d'un classement de ces réponses).
     */
    @OneToMany(cascade = {CascadeType.REMOVE},
            fetch = FetchType.LAZY, mappedBy = "survey")
    private Collection<Answer> answers;
}
