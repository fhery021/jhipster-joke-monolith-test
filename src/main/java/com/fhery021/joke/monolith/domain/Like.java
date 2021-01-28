package com.fhery021.joke.monolith.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Like.
 */
@Entity
@Table(name = "jhi_like")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Like implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "liked", nullable = false)
    private Boolean liked;

    @NotNull
    @Column(name = "account_id", nullable = false)
    private String accountId;

    @ManyToOne
    @JsonIgnoreProperties(value = "likes", allowSetters = true)
    private Joke joke;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isLiked() {
        return liked;
    }

    public Like liked(Boolean liked) {
        this.liked = liked;
        return this;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public String getAccountId() {
        return accountId;
    }

    public Like accountId(String accountId) {
        this.accountId = accountId;
        return this;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Joke getJoke() {
        return joke;
    }

    public Like joke(Joke joke) {
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
        if (!(o instanceof Like)) {
            return false;
        }
        return id != null && id.equals(((Like) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Like{" +
            "id=" + getId() +
            ", liked='" + isLiked() + "'" +
            ", accountId='" + getAccountId() + "'" +
            "}";
    }
}
