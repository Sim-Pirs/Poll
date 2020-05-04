package sondage.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Représente un tag
 */
@Entity
public class Tag {

    /**
     * Idendifiant unique du sondé.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Nom. La taille max est arbitraire. Ce nom est unique.
     */
    @Column(name = "name", length = 20, nullable = false, unique = true)
    private String name;

    /**
     * Description. La taille max est arbitraire.
     */
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    /**
     * Liste de sondé possédant le tag.
     */
    @ManyToMany(cascade = {CascadeType.REMOVE},
            fetch = FetchType.LAZY, mappedBy = "tags")
    private Collection<Respondent> respondents;

    /**
     * Liste de sondage possédant le tag.
     */
    @ManyToMany(cascade = {CascadeType.REMOVE},
            fetch = FetchType.LAZY, mappedBy = "tags")
    private Collection<Survey> surveys;
}
