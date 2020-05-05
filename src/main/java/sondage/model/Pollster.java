package sondage.model;

import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Représente un sondeur.
 */
@Entity
@Table(name = "POLLSTERS")
public class Pollster implements Serializable{

	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // à voir si nous remplacons par strategy = GenerationType.IDENTITY (SBGD)
    private Long id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 100, nullable = false)
    private String lastName;

    /**
     * Email. Elle est unique (pas possible d'avoir deux sondeurs avec la même adresse).
     * La taille max vient de la rfc 2821 qui limite les adresses à cette taille.
     */
    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 50, nullable = false)
    private String password;

    /**
     * Liste de tout les sondages proposés par le sondeur.
     */
    @OneToMany(mappedBy = "currentPollster", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Survey> surveys;
    
    public Pollster() {
    	super();
    }
    
    public Pollster(String firstName, String lastName, String email, String password) {
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.email = email;
    	this.password = password;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}
