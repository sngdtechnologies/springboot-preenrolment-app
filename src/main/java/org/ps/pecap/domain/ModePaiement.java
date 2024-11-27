package org.ps.pecap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ModePaiement.
 */
@Entity
@Table(name = "mode_paiement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ModePaiement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom", length = 150, nullable = false)
    private String nom;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "acces", "etatProcedures", "passports", "profils", "modePaiements", "typePassports" },
        allowSetters = true
    )
    private Annee annee;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "modePaiements", "typePassports", "annee" }, allowSetters = true)
    private Passport passport;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "modePaiements", "etatProcedures" }, allowSetters = true)
    private Client client;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ModePaiement id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public ModePaiement nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public ModePaiement annee(Annee annee) {
        this.setAnnee(annee);
        return this;
    }

    public Passport getPassport() {
        return this.passport;
    }

    public void setPassport(Passport passport) {
        this.passport = passport;
    }

    public ModePaiement passport(Passport passport) {
        this.setPassport(passport);
        return this;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ModePaiement client(Client client) {
        this.setClient(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ModePaiement)) {
            return false;
        }
        return id != null && id.equals(((ModePaiement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ModePaiement{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
