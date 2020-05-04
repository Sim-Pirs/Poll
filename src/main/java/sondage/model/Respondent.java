package sondage.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Représente un sondé.
 */
@Entity
public class Respondent {

    /**
     * Idendifiant unique du sondé.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Email.
     */
    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    /**
     * Liste de tag du sondé.
     */
    @ManyToMany(cascade = {CascadeType.REMOVE},
            fetch = FetchType.LAZY, mappedBy = "respondents")
    private Collection<Tag> tags;
}
