package sondage.entity.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Pattern(regexp = "^[A-Za-z0-9éè]+([ ]?[A-Za-z0-9éè]+)+", message = "{survey.name.invalid}")
    @Size(min = 1, max = 50, message = "{survey.name.badSize}")
    private String name;

    /**
     * Description.
     */
    @Column(name = "description", length =  500, nullable = false)
    @Pattern(regexp = "^(([ ]?[A-Za-z,éèà0-9]+)+[.?!]{0,1}[ ]?)+", message = "{survey.description.badFormat}")
    @Size(min = 1, max = 500, message = "{survey.description.badSize}")
    private String description;

    /**
     * Date de fin du sondage. Passé cette date, on ne peu plus y répondre.
     */
    @Column(name = "end_date", nullable = false)
    //@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date endDate;

    /**
     * Indique si les résultat ont été obtenue ou non
     */
    @Column(name = "is_result_obtained", nullable = false)
    private boolean isResultObtained = false;








    /**
     * Proprio du sondage.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pollster_id")
    private Pollster pollster;

    /**
     * Liste des options du sondage
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parent")
    private List<@Valid SurveyItem> items;

    /**
     * Liste des personnes devant y répondre.
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionForSize(int size) {
        return size > description.length() ? description : description.substring(0, size);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getStringEndDate(String format){
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(endDate);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isTerminated(){
        return endDate.compareTo(new Date()) <= 0;
    }

    public Pollster getPollster() {
        return pollster;
    }

    public void setPollster(Pollster pollster) {
        this.pollster = pollster;
    }

    public List<SurveyItem> getItems() {
        return items;
    }

    public void addItem(SurveyItem item){
        if(this.items == null)
            this.items = new ArrayList<>();

        this.items.add(item);
        item.setParent(this);
    }

    public void setItems(List<SurveyItem> items) {
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

    public boolean isResultObtained() {
        return isResultObtained;
    }

    public void setResultObtained(boolean resultObtained) {
        isResultObtained = resultObtained;
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", endDate=" + endDate +
                //", pollster=" + pollster +
                ", items=" + items +
                //", respondents=" + respondents +
                '}';
    }
}
