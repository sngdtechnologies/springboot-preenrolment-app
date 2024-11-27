package org.ps.pecap.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A LogSystem.
 */
@Entity
@Table(name = "log_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LogSystem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "event_date", nullable = false)
    private Instant eventDate;

    @NotNull
    @Size(max = 50)
    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @NotNull
    @Column(name = "message", nullable = false)
    private String message;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public LogSystem id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEventDate() {
        return this.eventDate;
    }

    public LogSystem eventDate(Instant eventDate) {
        this.setEventDate(eventDate);
        return this;
    }

    public void setEventDate(Instant eventDate) {
        this.eventDate = eventDate;
    }

    public String getLogin() {
        return this.login;
    }

    public LogSystem login(String login) {
        this.setLogin(login);
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMessage() {
        return this.message;
    }

    public LogSystem message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LogSystem)) {
            return false;
        }
        return id != null && id.equals(((LogSystem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LogSystem{" +
            "id=" + getId() +
            ", eventDate='" + getEventDate() + "'" +
            ", login='" + getLogin() + "'" +
            ", message='" + getMessage() + "'" +
            "}";
    }
}
