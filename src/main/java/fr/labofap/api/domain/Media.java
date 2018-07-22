package fr.labofap.api.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Media.
 */
@Entity
@Table(name = "media")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Media implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "mime_type")
    private String mimeType;

    @Size(min = 1, max = 50000000)
    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne
    private Comment comment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Media title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMimeType() {
        return mimeType;
    }

    public Media mimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public Media content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment getComment() {
        return comment;
    }

    public Media comment(Comment comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
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
        Media media = (Media) o;
        if (media.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), media.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Media{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", mimeType='" + getMimeType() + "'" +
            ", content='" + getContent() + "'" +
            "}";
    }
}
