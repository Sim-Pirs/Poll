package sondage.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @Column(name = "nb_pers_min", nullable = true)
    @Min(value = 1, message = "Le nombre minimum possible est 1.")
    private int nbPersMin;

    /**
     * Nombre de personne max pouvant choisir cette option
     */
    @Column(name = "nb_pers_max", nullable = true)
    @Min(value = 1, message = "Le nombre minimum de personne maximum est 1.")
    private int nbPersMax;

    /**
     * Description. La taille max est arbitraire.
     */
    @Column(name = "description", length = 500, nullable = true)
    @Pattern(regexp = "^(([ ]?[A-Za-z,]+)+[.?!]{0,1})+", message = "Format de la description invalide.")
    @Size(min = 1, max = 500, message = "La description doit avoir une taille comprise entre 1 et 500 caractères.")
    private String description;

    @ElementCollection
    @CollectionTable(name = "survey_tags", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "tag")
    private Collection<@Pattern(regexp = "[A-Za-z0-9]+([-]?[A-Za-z0-9]+)?", message = "Format du tag invalide.") String> tags;





    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_id")
    private Survey parent;


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


    @Override
    public String toString() {
        return "SurveyItem{" +
                "id=" + id +
                ", nbPersMin=" + nbPersMin +
                ", nbPersMax=" + nbPersMax +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                //", parentId=" + parent.getId() +
                '}';
    }
}
