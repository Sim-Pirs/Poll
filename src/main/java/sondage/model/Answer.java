package sondage.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

/**
 * Représente une réponse possible a un sondage.
 */
@Entity
@Table(name = "ANSWER")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * Liste de tag du sondage.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Tag> tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag){
        if(this.tags == null)
            this.tags = new HashSet<>();

        this.tags.add(tag);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }
}
