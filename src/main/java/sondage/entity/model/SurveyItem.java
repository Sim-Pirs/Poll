package sondage.entity.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

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
    @Min(value = 1, message = "{surveyItem.nbPersMin.badValue}")
    private int nbPersMin;

    /**
     * Nombre de personne max pouvant choisir cette option
     */
    @Column(name = "nb_pers_max", nullable = true)
    @Min(value = 1, message = "{surveyItem.nbPersMax.badValue}")
    private int nbPersMax;

    /**
     * Description. La taille max est arbitraire.
     */
    @Column(name = "description", length = 500, nullable = true)
    @Pattern(regexp = "^(([ ]?[A-Za-z,éèà]+)+[.?!]{0,1})+", message = "{surveyItem.description.invalid}")
    @Size(min = 1, max = 500, message = "{surveyItem.description.badSize}")
    private String description;

    @ElementCollection
    @CollectionTable(name = "survey_tags", joinColumns = @JoinColumn(name = "survey_id"))
    @Column(name = "tag")
    private Collection<@Pattern(regexp = "[A-Za-z0-9éè]+([-]?[A-Za-z0-9éè]+)?", message = "{surveyItem.tag.invalid}") String> tags;





    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "survey_id")
    private Survey parent;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                '}';
    }
}
