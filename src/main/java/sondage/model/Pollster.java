package sondage.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Représente un sondeur.
 */
@Entity
public class Pollster {

    /**
     * Identifiant unique du sondeur.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Prénom. La taille max est arbitraire.
     */
    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    /**
     * Nom de famille. La taille max est arbitraire.
     */
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    /**
     * Email. Elle est unique (pas possible d'avoir deux sondeur avec la même adresse).
     * La taille max viens de la rfc 2821 qui limite les adresses à cette taille.
     */
    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    /**
     * Mot de passe. La taille max est arbitraire.
     */
    @Column(name = "password", length = 50, nullable = false)
    private String password;

    /**
     * Liste de tout les sondages proposés par le sondeur.
     */
    @OneToMany(cascade = {CascadeType.REMOVE},
            fetch = FetchType.LAZY, mappedBy = "pollster")
    private Collection<Survey> surveys;
}
