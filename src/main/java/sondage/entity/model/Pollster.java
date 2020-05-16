package sondage.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Représente un sondeur.
 */
@Entity
@Table(name = "POLLSTERS")
public class Pollster {

    /**
     * Identifiant unique du sondeur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Prénom du sondeur.
     */
    @Column(name = "first_name", length = 50, nullable = false)
    @Pattern(regexp = "^[A-Za-z]+([ ]?[A-Za-z]+)?", message = "Format du prénom invalide. Seule les lettres sont autorisées.")
    @Size(min = 2, max = 50, message = "Le prénom doit avoir une taille comprise entre 2 et 50 caractères.")
    private String firstName;

    /**
     * Nom de famille du sondeur.
     */
    @Column(name = "last_name", length = 100, nullable = false)
    @Pattern(regexp = "^[A-Za-z]+([ ]?[A-Za-z]+)+", message = "Format du nom invalide. Seule les lettres sont autorisées.")
    @Size(min = 2, max = 100, message = "Le nom doit avoir une taille comprise entre 2 et 100 caractères.")
    private String lastName;

    /**
     * Email du sondeur. Elle est unique (pas possible d'avoir deux sondeurs avec la même adresse).
     */
    @Column(name = "email", length = 254, nullable = false, unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+", message = "Format de l'aresse mail invalide.")
    @Size(min = 5, max = 254, message = "L'adresse mail doit avoir une taille comprise en 5 et 254 caractères.")
    private String email;

    /**
     * Mot de passe.
     */
    @Column(name = "password", length = 50, nullable = false) //TODO remettre taille min du mot de passe a 8
    @Size(min = 1, max = 254, message = "Le mot de passe doit avoir une taille comprise en 8 et 50 caractères.")
    private String password;



    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Pollster{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
