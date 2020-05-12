package sondage.entity.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

/**
 * Représente une réponse possible a un sondage.
 */
@Entity
@Table(name = "SURVEY_ITEMS")
public class SurveyItem {

    /**
     * Idendifiant unique de la réponse.
     */
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    /**
     * Nombre de personne min pouvant choisir cette option
     */
    @Column(name = "nb_pers_min", nullable = false)
    private int nbPersMin;

    /**
     * Nombre de personne max pouvant choisir cette option
     */
    @Column(name = "nb_pers_max", nullable = false)
    private int nbPersMax;

    /**
     * Description. La taille max est arbitraire.
     */
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @ElementCollection
    @CollectionTable(name = "survey_tags", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "tag")
    private Collection<String> tags;





    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_id")
    private Survey parent;

    /**
     * Liste personnnes sélectionné pour cette option
     */
    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Respondent> finalRespondents;

    public long getId() {
        return id;
    }

    public int getNbPersMin() {
        return nbPersMin;
    }

    public void setNbPersMin(int nbPersMin) {
        this.nbPersMin = nbPersMin;
    }

    public int getNbPersMax() {
        return nbPersMax;
    }

    public void setNbPersMax(int nbPersMax) {
        this.nbPersMax = nbPersMax;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    public Survey getParent() {
        return parent;
    }

    public void setParent(Survey survey) {
        this.parent = survey;
    }

    public Collection<Respondent> getRespondents() {
        return finalRespondents;
    }

    public void addFinalRespondant(Respondent respondent){
        if(this.finalRespondents == null)
            this.finalRespondents = new HashSet<>();

        this.finalRespondents.add(respondent);
    }

    public void setRespondents(Collection<Respondent> respondents) {
        this.finalRespondents = respondents;
    }

    @Override
    public String toString() {
        return "SurveyItem{" +
                "id=" + id +
                ", nbPersMin=" + nbPersMin +
                ", nbPersMax=" + nbPersMax +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", parentId=" + parent.getId() +
                ", finalRespondents=" + finalRespondents +
                '}';
    }
}
