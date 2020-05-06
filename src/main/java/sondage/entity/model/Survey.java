package sondage.entity.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.io.Serializable;

/**
 * Représente un sondage
 */
@Entity
@Table(name = "SURVEY")
public class Survey implements Serializable {
	
	private static final long serialVersionUID = 1L;

    /**
     * id du sondage.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Nom du sondage.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Date de fin du sondage. Passé cette date, on ne peu plus y répondre.
     */
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * Description.
     */
    @Column(name = "description", length =  280, nullable = false)
    private String description;
    
    /**
     * Sondeur auquel appartient ce sondage.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "currentPollster")
    /* c'est la classe contenant l'annotation JoinTable qui abrite (owning) la relation.
     * Toute modification de cette relation doit donc passer par cette classe.
     * En d'autres termes, si vous souhaitez ajouter un film à une personne,
     * vous devez charger la personne, puis le film et ajouter le film à la personne.*/
    private Pollster currentPollster;

    /**
     * Liste des réponses possibles.
     */
    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "survey")
    private Collection<Answer> possibleAnswers;

    /**
     * Liste des personnes visées par le sondage.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Respondent> respondents;


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
    	this.name =  name;
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
    
    public Pollster getCurrentPollster() {
    	return currentPollster;
    }
    
    public void setCurrentPollster(Pollster currentPollster) {
    	this.currentPollster = currentPollster;
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

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", pollster=" + currentPollster +
                ", endDate=" + endDate +
                ", description='" + description + '\'' +
                ", possibleAnswers=" + possibleAnswers +
                '}';
    }
}
