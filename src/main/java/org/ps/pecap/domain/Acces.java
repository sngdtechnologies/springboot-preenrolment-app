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
 * A Acces.
 */
@Entity
@Table(name = "acces")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Acces implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "nom", length = 50, nullable = false, unique = true)
    private String nom;

    @NotNull
    @Size(max = 10)
    @Column(name = "code", length = 10, nullable = false, unique = true)
    private String code;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "acces", "etatProcedures", "passports", "profils", "modePaiements", "typePassports" },
        allowSetters = true
    )
    private Annee annee;

    @ManyToMany(mappedBy = "acces")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "acces", "annee" }, allowSetters = true)
    private Set<Profil> profils = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Acces id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Acces nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return this.code;
    }

    public Acces code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Acces annee(Annee annee) {
        this.setAnnee(annee);
        return this;
    }

    public Set<Profil> getProfils() {
        return this.profils;
    }

    public void setProfils(Set<Profil> profils) {
        if (this.profils != null) {
            this.profils.forEach(i -> i.removeAcces(this));
        }
        if (profils != null) {
            profils.forEach(i -> i.addAcces(this));
        }
        this.profils = profils;
    }

    public Acces profils(Set<Profil> profils) {
        this.setProfils(profils);
        return this;
    }

    public Acces addProfil(Profil profil) {
        this.profils.add(profil);
        profil.getAcces().add(this);
        return this;
    }

    public Acces removeProfil(Profil profil) {
        this.profils.remove(profil);
        profil.getAcces().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Acces)) {
            return false;
        }
        return id != null && id.equals(((Acces) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Acces{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", code='" + getCode() + "'" +
            "}";
    }
}
