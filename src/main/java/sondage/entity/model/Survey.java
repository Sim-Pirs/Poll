package sondage.entity.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * Représente un sondage
 */
@Entity
@Table(name = "SURVEYS")
public class Survey {

    /**
     * id du sondage.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Nom du sondage.
     */
    @Column(name = "name", length = 50, nullable = false)
    private String name;

    /**
     * Description.
     */
    @Column(name = "description", length =  500, nullable = false)
    private String description;

    /**
     * Date de fin du sondage. Passé cette date, on ne peu plus y répondre.
     */
    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;








    /**
     * Proprio du sondage.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pollster_id")
    private Pollster pollster;

    /**
     * Liste des options du sondage
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "surveyParent")
    private Collection<SurveyItem> items;

    /**
     * Liste des personnes devant y répondre.
     */
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Respondent> respondents;

    public long getId() {
        return id;
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Pollster getPollster() {
        return pollster;
    }

    public void setPollster(Pollster pollster) {
        this.pollster = pollster;
    }

    public Collection<SurveyItem> getItems() {
        return items;
    }

    public void addItem(SurveyItem item){
        if(this.items == null)
            this.items = new HashSet<>();

        this.items.add(item);
        item.setSurvey(this);
    }

    public void setItems(Collection<SurveyItem> items) {
        this.items = items;
    }

    public Collection<Respondent> getRespondents() {
        return respondents;
    }

    public void addRespondent(Respondent respondent){
        if(this.respondents == null)
            this.respondents = new HashSet<>();

        this.respondents.add(respondent);
        respondent.setSurvey(this);
    }

    public void setRespondents(Collection<Respondent> respondents) {
        this.respondents = respondents;
    }
}
