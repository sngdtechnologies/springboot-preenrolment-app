package org.ps.pecap.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Annee.
 */
@Entity
@Table(name = "annee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Annee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom", length = 150, nullable = false)
    private String nom;

    @Column(name = "date_debut")
    private LocalDate dateDebut;

    @Column(name = "date_fin")
    private LocalDate dateFin;

    @NotNull
    @Column(name = "is_verrouiller", nullable = false)
    private Boolean isVerrouiller;

    @NotNull
    @Column(name = "is_cloturer", nullable = false)
    private Boolean isCloturer;

    @OneToMany(mappedBy = "annee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "profils" }, allowSetters = true)
    private Set<Acces> acces = new HashSet<>();

    @OneToMany(mappedBy = "annee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "client" }, allowSetters = true)
    private Set<EtatProcedure> etatProcedures = new HashSet<>();

    @OneToMany(mappedBy = "annee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "modePaiements", "typePassports", "annee" }, allowSetters = true)
    private Set<Passport> passports = new HashSet<>();

    @OneToMany(mappedBy = "annee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "acces", "annee" }, allowSetters = true)
    private Set<Profil> profils = new HashSet<>();

    @OneToMany(mappedBy = "annee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "passport", "client" }, allowSetters = true)
    private Set<ModePaiement> modePaiements = new HashSet<>();

    @OneToMany(mappedBy = "annee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "passport" }, allowSetters = true)
    private Set<TypePassport> typePassports = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Annee id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Annee nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDateDebut() {
        return this.dateDebut;
    }

    public Annee dateDebut(LocalDate dateDebut) {
        this.setDateDebut(dateDebut);
        return this;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return this.dateFin;
    }

    public Annee dateFin(LocalDate dateFin) {
        this.setDateFin(dateFin);
        return this;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Boolean getIsVerrouiller() {
        return this.isVerrouiller;
    }

    public Annee isVerrouiller(Boolean isVerrouiller) {
        this.setIsVerrouiller(isVerrouiller);
        return this;
    }

    public void setIsVerrouiller(Boolean isVerrouiller) {
        this.isVerrouiller = isVerrouiller;
    }

    public Boolean getIsCloturer() {
        return this.isCloturer;
    }

    public Annee isCloturer(Boolean isCloturer) {
        this.setIsCloturer(isCloturer);
        return this;
    }

    public void setIsCloturer(Boolean isCloturer) {
        this.isCloturer = isCloturer;
    }

    public Set<Acces> getAcces() {
        return this.acces;
    }

    public void setAcces(Set<Acces> acces) {
        if (this.acces != null) {
            this.acces.forEach(i -> i.setAnnee(null));
        }
        if (acces != null) {
            acces.forEach(i -> i.setAnnee(this));
        }
        this.acces = acces;
    }

    public Annee acces(Set<Acces> acces) {
        this.setAcces(acces);
        return this;
    }

    public Annee addAcces(Acces acces) {
        this.acces.add(acces);
        acces.setAnnee(this);
        return this;
    }

    public Annee removeAcces(Acces acces) {
        this.acces.remove(acces);
        acces.setAnnee(null);
        return this;
    }

    public Set<EtatProcedure> getEtatProcedures() {
        return this.etatProcedures;
    }

    public void setEtatProcedures(Set<EtatProcedure> etatProcedures) {
        if (this.etatProcedures != null) {
            this.etatProcedures.forEach(i -> i.setAnnee(null));
        }
        if (etatProcedures != null) {
            etatProcedures.forEach(i -> i.setAnnee(this));
        }
        this.etatProcedures = etatProcedures;
    }

    public Annee etatProcedures(Set<EtatProcedure> etatProcedures) {
        this.setEtatProcedures(etatProcedures);
        return this;
    }

    public Annee addEtatProcedure(EtatProcedure etatProcedure) {
        this.etatProcedures.add(etatProcedure);
        etatProcedure.setAnnee(this);
        return this;
    }

    public Annee removeEtatProcedure(EtatProcedure etatProcedure) {
        this.etatProcedures.remove(etatProcedure);
        etatProcedure.setAnnee(null);
        return this;
    }

    public Set<Passport> getPassports() {
        return this.passports;
    }

    public void setPassports(Set<Passport> passports) {
        if (this.passports != null) {
            this.passports.forEach(i -> i.setAnnee(null));
        }
        if (passports != null) {
            passports.forEach(i -> i.setAnnee(this));
        }
        this.passports = passports;
    }

    public Annee passports(Set<Passport> passports) {
        this.setPassports(passports);
        return this;
    }

    public Annee addPassport(Passport passport) {
        this.passports.add(passport);
        passport.setAnnee(this);
        return this;
    }

    public Annee removePassport(Passport passport) {
        this.passports.remove(passport);
        passport.setAnnee(null);
        return this;
    }

    public Set<Profil> getProfils() {
        return this.profils;
    }

    public void setProfils(Set<Profil> profils) {
        if (this.profils != null) {
            this.profils.forEach(i -> i.setAnnee(null));
        }
        if (profils != null) {
            profils.forEach(i -> i.setAnnee(this));
        }
        this.profils = profils;
    }

    public Annee profils(Set<Profil> profils) {
        this.setProfils(profils);
        return this;
    }

    public Annee addProfil(Profil profil) {
        this.profils.add(profil);
        profil.setAnnee(this);
        return this;
    }

    public Annee removeProfil(Profil profil) {
        this.profils.remove(profil);
        profil.setAnnee(null);
        return this;
    }

    public Set<ModePaiement> getModePaiements() {
        return this.modePaiements;
    }

    public void setModePaiements(Set<ModePaiement> modePaiements) {
        if (this.modePaiements != null) {
            this.modePaiements.forEach(i -> i.setAnnee(null));
        }
        if (modePaiements != null) {
            modePaiements.forEach(i -> i.setAnnee(this));
        }
        this.modePaiements = modePaiements;
    }

    public Annee modePaiements(Set<ModePaiement> modePaiements) {
        this.setModePaiements(modePaiements);
        return this;
    }

    public Annee addModePaiement(ModePaiement modePaiement) {
        this.modePaiements.add(modePaiement);
        modePaiement.setAnnee(this);
        return this;
    }

    public Annee removeModePaiement(ModePaiement modePaiement) {
        this.modePaiements.remove(modePaiement);
        modePaiement.setAnnee(null);
        return this;
    }

    public Set<TypePassport> getTypePassports() {
        return this.typePassports;
    }

    public void setTypePassports(Set<TypePassport> typePassports) {
        if (this.typePassports != null) {
            this.typePassports.forEach(i -> i.setAnnee(null));
        }
        if (typePassports != null) {
            typePassports.forEach(i -> i.setAnnee(this));
        }
        this.typePassports = typePassports;
    }

    public Annee typePassports(Set<TypePassport> typePassports) {
        this.setTypePassports(typePassports);
        return this;
    }

    public Annee addTypePassport(TypePassport typePassport) {
        this.typePassports.add(typePassport);
        typePassport.setAnnee(this);
        return this;
    }

    public Annee removeTypePassport(TypePassport typePassport) {
        this.typePassports.remove(typePassport);
        typePassport.setAnnee(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Annee)) {
            return false;
        }
        return id != null && id.equals(((Annee) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Annee{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", dateDebut='" + getDateDebut() + "'" +
            ", dateFin='" + getDateFin() + "'" +
            ", isVerrouiller='" + getIsVerrouiller() + "'" +
            ", isCloturer='" + getIsCloturer() + "'" +
            "}";
    }
}
