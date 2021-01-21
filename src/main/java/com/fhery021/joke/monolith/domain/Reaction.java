package com.fhery021.joke.monolith.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Reaction.
 */
@Entity
@Table(name = "reaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "jhi_like", nullable = false)
    private Boolean like;

    @ManyToOne
    @JsonIgnoreProperties(value = "reactions", allowSetters = true)
    private Joke joke;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Reaction comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean isLike() {
        return like;
    }

    public Reaction like(Boolean like) {
        this.like = like;
        return this;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Joke getJoke() {
        return joke;
    }

    public Reaction joke(Joke joke) {
        this.joke = joke;
        return this;
    }

    public void setJoke(Joke joke) {
        this.joke = joke;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Reaction)) {
            return false;
        }
        return id != null && id.equals(((Reaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reaction{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", like='" + isLike() + "'" +
            "}";
    }
}
