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
    @Pattern(regexp = "[A-Z][a-z]+([-][A-Z]([a-z])+)?", message = "Le format du prénom n'est pas valable ! Format attendu : Jean ou Jean-Marie")
    @Size(min = 1, max = 50, message = "Le prénom doit avoir une taille comprise entre 1 et 50 caractères.")
    private String firstName;

    /**
     * Nom de famille du sondeur.
     */
    @Column(name = "last_name", length = 100, nullable = false)
    @Pattern(regexp = "[A-Z][a-z]+([-][A-Z]([a-z])+)?", message = "Le format du nom n'est pas valable ! Format attendu : Jean ou Jean-Marie")
    @Size(min = 1, max = 100, message = "Le nom doit avoir une taille comprise entre 1 et 100 caractères.")
    private String lastName;

    /**
     * Email du sondeur. Elle est unique (pas possible d'avoir deux sondeurs avec la même adresse).
     */
    @Column(name = "email", length = 254, nullable = false, unique = true)
    @Size(min = 5, max = 254, message = "L'email doit avoir une taille comprise en 5 et 254 caractères.")
    private String email;

    /**
     * Mot de passe.
     */
    @Column(name = "password", length = 50, nullable = false)
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*-_=+])[a-zA-Z0-9!@#$%^&*-_=+](?=\\\\S+$)?", message = "Le mot de passe n'es pas valable ! Format attendu : must include at least a lower case letter, a capital letter, a number and a special character among !@#$%^&*-_=+")
    @Size(min = 8, max = 254, message = "Le mot de passe doit avoir une taille comprise en 8 et 50 caractères.")
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
