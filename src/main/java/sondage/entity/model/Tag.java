package sondage.entity.model;

import javax.persistence.*;

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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
