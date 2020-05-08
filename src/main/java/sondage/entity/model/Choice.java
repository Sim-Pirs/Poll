package sondage.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Représente une réponse à un sondage par un sondé.
 */
@Entity
@Table(name = "CHOICES")
public class Choice {

    /**
     * Idendifiant unique de la réponse du sondé.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Score attribué à cette réponse. 1 est mieux que 5.
     */
    @Column(name = "score", nullable = false)
    @Min(1)
    private int score;







    /**
     * Sondé ayant fais ce choix.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "respondent_id")
    private Respondent respondent;

    /**
     * Option en question.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private SurveyItem item;


    public long getId() {
        return id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Respondent getRespondent() {
        return respondent;
    }

    public void setRespondent(Respondent respondent) {
        this.respondent = respondent;
    }

    public SurveyItem getItem() {
        return item;
    }

    public void setItem(SurveyItem item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", score=" + score +
                ", respondent=" + respondent +
                ", item=" + item +
                '}';
    }
}
