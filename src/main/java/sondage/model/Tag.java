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

    public Collection<Respondent> getRespondents() {
        return respondents;
    }

    public void addRespondent(Respondent respondent){
        this.respondents.add(respondent);
    }

    public void setRespondents(Collection<Respondent> respondents) {
        this.respondents = respondents;
    }

    public Collection<Survey> getSurveys() {
        return surveys;
    }

    public void addSurvey(Survey survey){
        this.surveys.add(survey);
    }

    public void setSurveys(Collection<Survey> surveys) {
        this.surveys = surveys;
    }
}
