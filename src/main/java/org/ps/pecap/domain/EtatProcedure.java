package org.ps.pecap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EtatProcedure.
 */
@Entity
@Table(name = "etat_procedure")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EtatProcedure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "etat_pre_enrole", length = 50)
    private String etatPreEnrole;

    @Size(max = 50)
    @Column(name = "etat_enrole", length = 50)
    private String etatEnrole;

    @Size(max = 150)
    @Column(name = "etat_retrait", length = 150)
    private String etatRetrait;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "acces", "etatProcedures", "passports", "profils", "modePaiements", "typePassports" },
        allowSetters = true
    )
    private Annee annee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "modePaiements", "etatProcedures" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EtatProcedure id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEtatPreEnrole() {
        return this.etatPreEnrole;
    }

    public EtatProcedure etatPreEnrole(String etatPreEnrole) {
        this.setEtatPreEnrole(etatPreEnrole);
        return this;
    }

    public void setEtatPreEnrole(String etatPreEnrole) {
        this.etatPreEnrole = etatPreEnrole;
    }

    public String getEtatEnrole() {
        return this.etatEnrole;
    }

    public EtatProcedure etatEnrole(String etatEnrole) {
        this.setEtatEnrole(etatEnrole);
        return this;
    }

    public void setEtatEnrole(String etatEnrole) {
        this.etatEnrole = etatEnrole;
    }

    public String getEtatRetrait() {
        return this.etatRetrait;
    }

    public EtatProcedure etatRetrait(String etatRetrait) {
        this.setEtatRetrait(etatRetrait);
        return this;
    }

    public void setEtatRetrait(String etatRetrait) {
        this.etatRetrait = etatRetrait;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public EtatProcedure annee(Annee annee) {
        this.setAnnee(annee);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public EtatProcedure client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EtatProcedure)) {
            return false;
        }
        return id != null && id.equals(((EtatProcedure) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EtatProcedure{" +
            "id=" + getId() +
            ", etatPreEnrole='" + getEtatPreEnrole() + "'" +
            ", etatEnrole='" + getEtatEnrole() + "'" +
            ", etatRetrait='" + getEtatRetrait() + "'" +
            "}";
    }
}
