package fr.labofap.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import fr.labofap.api.domain.enumeration.CommandStatus;
import org.springframework.data.annotation.CreatedBy;

/**
 * A Command.
 */
@Entity
@Table(name = "command")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Command extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CommandStatus status;

    @Column(name = "assigned_to", length = 50)
    @JsonIgnore
    private String assignedTo;

    @OneToMany(mappedBy = "command")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "amount")
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

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

    public Command title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CommandStatus getStatus() {
        return status;
    }

    public Command status(CommandStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(CommandStatus status) {
        this.status = status;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Command comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Command addComments(Comment comment) {
        this.comments.add(comment);
        comment.setCommand(this);
        return this;
    }

    public Command removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setCommand(null);
        return this;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
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
        Command command = (Command) o;
        if (command.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), command.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Command{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", status='" + getStatus() + "'" +
            ", assignedTo='" + getAssignedTo() + "'" +
            "}";
    }
}
