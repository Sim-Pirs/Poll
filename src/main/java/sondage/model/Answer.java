package sondage.model;

import javax.persistence.*;

/**
 * Représente une réponse possible a un sondage.
 */
@Entity
public class Answer {

    /**
     * Idendifiant unique de la réponse.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Sondage auquel appartient ce choix.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "survey")
    private Survey survey;

    /**
     * Description. La taille max est arbitraire.
     */
    @Column(name = "description", length = 500, nullable = false)
    private String description;

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

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
