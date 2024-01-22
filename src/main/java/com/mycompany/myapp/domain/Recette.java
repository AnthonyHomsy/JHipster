package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Recette.
 */
@Entity
@Table(name = "recette")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Recette implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(min = 5, max = 150)
    @Column(name = "title", length = 150, nullable = false)
    private String title;

    @NotNull
    @Size(min = 5, max = 500)
    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recette")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recette", "user" }, allowSetters = true)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recette")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "recette", "user" }, allowSetters = true)
    private Set<Like> likes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Recette id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Recette title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Recette description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Recette user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public void setComments(Set<Comment> comments) {
        if (this.comments != null) {
            this.comments.forEach(i -> i.setRecette(null));
        }
        if (comments != null) {
            comments.forEach(i -> i.setRecette(this));
        }
        this.comments = comments;
    }

    public Recette comments(Set<Comment> comments) {
        this.setComments(comments);
        return this;
    }

    public Recette addComment(Comment comment) {
        this.comments.add(comment);
        comment.setRecette(this);
        return this;
    }

    public Recette removeComment(Comment comment) {
        this.comments.remove(comment);
        comment.setRecette(null);
        return this;
    }

    public Set<Like> getLikes() {
        return this.likes;
    }

    public void setLikes(Set<Like> likes) {
        if (this.likes != null) {
            this.likes.forEach(i -> i.setRecette(null));
        }
        if (likes != null) {
            likes.forEach(i -> i.setRecette(this));
        }
        this.likes = likes;
    }

    public Recette likes(Set<Like> likes) {
        this.setLikes(likes);
        return this;
    }

    public Recette addLike(Like like) {
        this.likes.add(like);
        like.setRecette(this);
        return this;
    }

    public Recette removeLike(Like like) {
        this.likes.remove(like);
        like.setRecette(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recette)) {
            return false;
        }
        return getId() != null && getId().equals(((Recette) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recette{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
