package sondage.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Représente un sondage
 */
@Entity
public class Survey {

    /**
     * Idendifiant unique du sondage.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Sondeur auquel appartient ce sondage.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "pollster")
    private Pollster pollster;

    /**
     * Date de fin du sondage.
     */
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * Description. La taille max est arbitraire.
     */
    @Column(name = "description", length =  500, nullable = false)
    private String description;

    /**
     * Liste des réponses possibles.
     */
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "survey")
    private Collection<Answer> possibleAnswers;

    /**
     * Liste de tag du sondage.
     */
    @ManyToMany( fetch = FetchType.LAZY)
    private Collection<Tag> tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pollster getPollster() {
        return pollster;
    }

    public void setPollster(Pollster pollster) {
        this.pollster = pollster;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Answer> getPossibleAnswers() {
        return possibleAnswers;
    }

    public void addPossibleAnswer(Answer answer){
        if(this.possibleAnswers == null)
            this.possibleAnswers = new HashSet<>();

        this.possibleAnswers.add(answer);
        answer.setSurvey(this);
    }

    public void setPossibleAnswers(Collection<Answer> possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag){
        if(this.tags == null)
            this.tags = new HashSet<>();

        this.tags.add(tag);
        //tag.addSurvey(this);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", pollster=" + pollster +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", possibleAnswers=" + possibleAnswers +
                ", tags=" + tags +
                '}';
    }
}
