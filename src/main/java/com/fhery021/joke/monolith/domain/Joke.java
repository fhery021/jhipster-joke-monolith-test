package com.fhery021.joke.monolith.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CascadeType;

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
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "joke")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @Cascade({ CascadeType.DELETE })
    private Set<Like> likes = new HashSet<>();

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

    public Set<Comment> getComments() {
        return comments;
    }

    public Joke comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Joke addComment(Comment comment) {
        this.comments.add(comment);
        comment.setJoke(this);
        return this;
    }

    public Joke removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setJoke(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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
            ", question='" + getQuestion() + "'" +
            ", answer='" + getAnswer() + "'" +
            "}";
    }
}
