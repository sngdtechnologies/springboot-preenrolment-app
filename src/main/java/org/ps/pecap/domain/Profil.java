package org.ps.pecap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Profil.
 */
@Entity
@Table(name = "profil")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Profil implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom", length = 150, nullable = false)
    private String nom;

    @ManyToMany
    @JoinTable(
        name = "rel_profil__acces",
        joinColumns = @JoinColumn(name = "profil_id"),
        inverseJoinColumns = @JoinColumn(name = "acces_id")
    )
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "profils" }, allowSetters = true)
    private Set<Acces> acces = new HashSet<>();

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "acces", "etatProcedures", "passports", "profils", "modePaiements", "typePassports" },
        allowSetters = true
    )
    private Annee annee;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Profil id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Profil nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Acces> getAcces() {
        return this.acces;
    }

    public void setAcces(Set<Acces> acces) {
        this.acces = acces;
    }

    public Profil acces(Set<Acces> acces) {
        this.setAcces(acces);
        return this;
    }

    public Profil addAcces(Acces acces) {
        this.acces.add(acces);
        acces.getProfils().add(this);
        return this;
    }

    public Profil removeAcces(Acces acces) {
        this.acces.remove(acces);
        acces.getProfils().remove(this);
        return this;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Profil annee(Annee annee) {
        this.setAnnee(annee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profil)) {
            return false;
        }
        return id != null && id.equals(((Profil) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Profil{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
