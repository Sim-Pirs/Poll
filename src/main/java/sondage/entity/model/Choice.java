package sondage.entity.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Représente la réponse à un sondage par un sondé.
 */
@Entity
@Table(name = "CHOICE")
public class Choice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Idendifiant unique de la réponse du sondé.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

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
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "survey")
    private Answer answers;

    /**
     * Score attribué à cette réponse. 1 est mieux que 5.
     */
    @Column(name = "score", nullable = false)
    private int score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Respondent getRespondent() {
        return respondent;
    }

    public void setRespondent(Respondent respondent) {
        this.respondent = respondent;
    }

    public Answer getAnswers() {
        return answers;
    }

    public void setAnswers(Answer answers) {
        this.answers = answers;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
