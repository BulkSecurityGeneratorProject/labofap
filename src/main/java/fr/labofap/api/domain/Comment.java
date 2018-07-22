package fr.labofap.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "content")
    private String content;

    @ManyToOne
    private Command command;

    @OneToMany(mappedBy = "comment")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Media> medias = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Comment content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Command getCommand() {
        return command;
    }

    public Comment command(Command command) {
        this.command = command;
        return this;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public Set<Media> getMedias() {
        return medias;
    }

    public Comment medias(Set<Media> media) {
        this.medias = media;
        return this;
    }

    public Comment addMedias(Media media) {
        this.medias.add(media);
        media.setComment(this);
        return this;
    }

    public Comment removeMedias(Media media) {
        this.medias.remove(media);
        media.setComment(null);
        return this;
    }

    public void setMedias(Set<Media> media) {
        this.medias = media;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            "}";
    }
}
