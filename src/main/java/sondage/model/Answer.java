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
}
