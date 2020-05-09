package sondage.entity.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * Représente un sondé.
 */
@Entity
@Table(name = "RESPONDENTS")
public class Respondent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "email", length = 254, nullable = false)
    private String email;

    @ElementCollection
    @CollectionTable(name = "respondent_tags", joinColumns = @JoinColumn(name = "respondent_id"))
    @Column(name = "tag")
    private Collection<String> tags;





    /**
     * Sondage assigné au sondé.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    public long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void addTag(String tag){
        if(this.tags == null)
            this.tags = new HashSet<>();

        this.tags.add(tag);
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
}