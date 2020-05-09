package sondage.entity.model;

import javax.persistence.*;

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
    private String firstName;

    /**
     * Nom de famille du sondeur.
     */
    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    /**
     * Email du sondeur. Elle est unique (pas possible d'avoir deux sondeurs avec la même adresse).
     */
    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    /**
     * Mot de passe.
     */
    @Column(name = "password", length = 50, nullable = false)
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
