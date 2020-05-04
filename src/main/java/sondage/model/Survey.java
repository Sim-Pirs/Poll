package sondage.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Représente un sondage
 */
@Entity
public class Survey {

    /**
     * Idendifiant unique du sondage.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Sondeur auquel appartient ce sondage.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "pollster")
    private Pollster pollster;

    /**
     * Date de fin du sondage.
     */
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * Description. La taille max est arbitraire.
     */
    @Column(name = "description", length =  500, nullable = false)
    private String description;

    /**
     * Liste des réponses possibles.
     */
    @OneToMany(cascade = {CascadeType.REMOVE},
            fetch = FetchType.LAZY, mappedBy = "survey")
    private Collection<Answer> possibleAnswers;

    /**
     * Liste de tag du sondage.
     */
    @ManyToMany(cascade = {CascadeType.REMOVE},
            fetch = FetchType.LAZY, mappedBy = "surveys")
    private Collection<Tag> tags;
}
