package sondage.model;

import javax.persistence.*;
import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.HashSet;

/**
 * Représente un sondé.
 */
@Entity
@Table(name = "RESPONDENTS")
public class Respondent implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "email", length = 254, nullable = false, unique = true)
    private String email;

    /**
     * Liste de tag du sondé.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Tag> tags;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Tag> getTags() {
        return tags;
    }

    public void addTag(Tag tag){
        if(this.tags == null)
            this.tags = new HashSet<>();

        this.tags.add(tag);
        //tag.addRespondent(this);
    }

    public void setTags(Collection<Tag> tags) {
        this.tags = tags;
    }
}
