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
    @Column(name = "question", nullable = false)
    private String question;

    @NotNull
    @Column(name = "answer", nullable = false)
    private String answer;

    @OneToMany(mappedBy = "joke")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Reaction> reactions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public Joke question(String question) {
        this.question = question;
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public Joke answer(String answer) {
        this.answer = answer;
        return this;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public Joke reactions(Set<Reaction> reactions) {
        this.reactions = reactions;
        return this;
    }

    public Joke addReaction(Reaction reaction) {
        this.reactions.add(reaction);
        reaction.setJoke(this);
        return this;
    }

    public Joke removeReaction(Reaction reaction) {
        this.reactions.remove(reaction);
        reaction.setJoke(null);
        return this;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
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
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
