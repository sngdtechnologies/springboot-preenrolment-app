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
 * A Passport.
 */
@Entity
@Table(name = "passport")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Passport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(max = 50)
    @Column(name = "nom", length = 50)
    private String nom;

    @OneToMany(mappedBy = "passport")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "passport", "client" }, allowSetters = true)
    private Set<ModePaiement> modePaiements = new HashSet<>();

    @OneToMany(mappedBy = "passport")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "passport" }, allowSetters = true)
    private Set<TypePassport> typePassports = new HashSet<>();

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

    public Passport id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Passport nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<ModePaiement> getModePaiements() {
        return this.modePaiements;
    }

    public void setModePaiements(Set<ModePaiement> modePaiements) {
        if (this.modePaiements != null) {
            this.modePaiements.forEach(i -> i.setPassport(null));
        }
        if (modePaiements != null) {
            modePaiements.forEach(i -> i.setPassport(this));
        }
        this.modePaiements = modePaiements;
    }

    public Passport modePaiements(Set<ModePaiement> modePaiements) {
        this.setModePaiements(modePaiements);
        return this;
    }

    public Passport addModePaiement(ModePaiement modePaiement) {
        this.modePaiements.add(modePaiement);
        modePaiement.setPassport(this);
        return this;
    }

    public Passport removeModePaiement(ModePaiement modePaiement) {
        this.modePaiements.remove(modePaiement);
        modePaiement.setPassport(null);
        return this;
    }

    public Set<TypePassport> getTypePassports() {
        return this.typePassports;
    }

    public void setTypePassports(Set<TypePassport> typePassports) {
        if (this.typePassports != null) {
            this.typePassports.forEach(i -> i.setPassport(null));
        }
        if (typePassports != null) {
            typePassports.forEach(i -> i.setPassport(this));
        }
        this.typePassports = typePassports;
    }

    public Passport typePassports(Set<TypePassport> typePassports) {
        this.setTypePassports(typePassports);
        return this;
    }

    public Passport addTypePassport(TypePassport typePassport) {
        this.typePassports.add(typePassport);
        typePassport.setPassport(this);
        return this;
    }

    public Passport removeTypePassport(TypePassport typePassport) {
        this.typePassports.remove(typePassport);
        typePassport.setPassport(null);
        return this;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public Passport annee(Annee annee) {
        this.setAnnee(annee);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Passport)) {
            return false;
        }
        return id != null && id.equals(((Passport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Passport{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}
