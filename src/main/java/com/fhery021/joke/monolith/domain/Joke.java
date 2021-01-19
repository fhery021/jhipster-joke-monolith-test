package com.fhery021.joke.monolith.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Joke.
 */
@Entity
@Table(name = "joke")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Joke implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "text", nullable = false)
    private String text;

    @OneToMany(mappedBy = "joke")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Like> likes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Joke text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Like> getLikes() {
        return likes;
    }

    public Joke likes(Set<Like> likes) {
        this.likes = likes;
        return this;
    }

    public Joke addLike(Like like) {
        this.likes.add(like);
        like.setJoke(this);
        return this;
    }

    public Joke removeLike(Like like) {
        this.likes.remove(like);
        like.setJoke(null);
        return this;
    }

    public void setLikes(Set<Like> likes) {
        this.likes = likes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Joke)) {
            return false;
        }
        return id != null && id.equals(((Joke) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Joke{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            "}";
    }
}
