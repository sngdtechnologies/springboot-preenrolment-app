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
 * A Client.
 */
@Entity
@Table(name = "client")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom", length = 150)
    private String nom;

    @NotNull
    @Size(max = 150)
    @Column(name = "prenom", length = 150)
    private String prenom;

    @Column(name = "photo_url")
    private String photoUrl;

    @NotNull
    @Column(name = "date_naiss")
    private LocalDate dateNaiss;

    @Size(max = 4)
    @Column(name = "annee_naiss", length = 4)
    private String anneeNaiss;

    @NotNull
    @Size(max = 150)
    @Column(name = "lieu_naiss", length = 150)
    private String lieuNaiss;

    @NotNull
    @Size(max = 50)
    @Column(name = "genre", length = 50)
    private String genre;

    @NotNull
    @Size(max = 254)
    @Column(name = "type_demande", length = 254)
    private String typeDemande;

    @NotNull
    @Size(max = 150)
    @Column(name = "email", length = 150)
    private String email;

    @NotNull
    @Size(max = 254)
    @Column(name = "dest_voyage_p", length = 254)
    private String destVoyageP;

    @NotNull
    @Size(max = 254)
    @Column(name = "motif_deplacement", length = 254)
    private String motifDeplacement;

    @NotNull
    @Size(max = 150)
    @Column(name = "pays_naissance", length = 150)
    private String paysNaissance;

    @NotNull
    @Size(max = 150)
    @Column(name = "region_naiss", length = 150)
    private String regionNaiss;

    @NotNull
    @Size(max = 150)
    @Column(name = "departe_naiss", length = 150)
    private String departeNaiss;

    @NotNull
    @Size(max = 20)
    @Column(name = "telephone", length = 20)
    private String telephone;

    @NotNull
    @Size(max = 100)
    @Column(name = "pays", length = 100)
    private String pays;

    @NotNull
    @Size(max = 150)
    @Column(name = "region", length = 150)
    private String region;

    @NotNull
    @Size(max = 150)
    @Column(name = "departement", length = 150)
    private String departement;

    @NotNull
    @Size(max = 150)
    @Column(name = "lieu", length = 150)
    private String lieu;

    @NotNull
    @Size(max = 150)
    @Column(name = "rue", length = 150)
    private String rue;

    @NotNull
    @Size(max = 150)
    @Column(name = "profession", length = 150)
    private String profession;

    @NotNull
    @Size(max = 150)
    @Column(name = "prenom_mere", length = 150)
    private String prenomMere;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom_mere", length = 150)
    private String nomMere;

    @NotNull
    @Size(max = 150)
    @Column(name = "prenom_pere", length = 150)
    private String prenomPere;

    @NotNull
    @Size(max = 150)
    @Column(name = "nom_pere", length = 150)
    private String nomPere;

    @NotNull
    @Size(max = 254)
    @Column(name = "format_cni", length = 254)
    private String formatCni;

    @NotNull
    @Column(name = "numero_cni")
    private Integer numeroCni;

    @NotNull
    @Column(name = "date_deliv_cni")
    private LocalDate dateDelivCni;

    @NotNull
    @Column(name = "date_exp_cni")
    private LocalDate dateExpCni;

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "passport", "client" }, allowSetters = true)
    private Set<ModePaiement> modePaiements = new HashSet<>();

    @OneToMany(mappedBy = "client")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "annee", "client" }, allowSetters = true)
    private Set<EtatProcedure> etatProcedures = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public Client nom(String nom) {
        this.setNom(nom);
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public Client prenom(String prenom) {
        this.setPrenom(prenom);
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public Client photoUrl(String photoUrl) {
        this.setPhotoUrl(photoUrl);
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDate getDateNaiss() {
        return this.dateNaiss;
    }

    public Client dateNaiss(LocalDate dateNaiss) {
        this.setDateNaiss(dateNaiss);
        return this;
    }

    public void setDateNaiss(LocalDate dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public String getAnneeNaiss() {
        return this.anneeNaiss;
    }

    public Client anneeNaiss(String anneeNaiss) {
        this.setAnneeNaiss(anneeNaiss);
        return this;
    }

    public void setAnneeNaiss(String anneeNaiss) {
        this.anneeNaiss = anneeNaiss;
    }

    public String getLieuNaiss() {
        return this.lieuNaiss;
    }

    public Client lieuNaiss(String lieuNaiss) {
        this.setLieuNaiss(lieuNaiss);
        return this;
    }

    public void setLieuNaiss(String lieuNaiss) {
        this.lieuNaiss = lieuNaiss;
    }

    public String getGenre() {
        return this.genre;
    }

    public Client genre(String genre) {
        this.setGenre(genre);
        return this;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTypeDemande() {
        return this.typeDemande;
    }

    public Client typeDemande(String typeDemande) {
        this.setTypeDemande(typeDemande);
        return this;
    }

    public void setTypeDemande(String typeDemande) {
        this.typeDemande = typeDemande;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDestVoyageP() {
        return this.destVoyageP;
    }

    public Client destVoyageP(String destVoyageP) {
        this.setDestVoyageP(destVoyageP);
        return this;
    }

    public void setDestVoyageP(String destVoyageP) {
        this.destVoyageP = destVoyageP;
    }

    public String getMotifDeplacement() {
        return this.motifDeplacement;
    }

    public Client motifDeplacement(String motifDeplacement) {
        this.setMotifDeplacement(motifDeplacement);
        return this;
    }

    public void setMotifDeplacement(String motifDeplacement) {
        this.motifDeplacement = motifDeplacement;
    }

    public String getPaysNaissance() {
        return this.paysNaissance;
    }

    public Client paysNaissance(String paysNaissance) {
        this.setPaysNaissance(paysNaissance);
        return this;
    }

    public void setPaysNaissance(String paysNaissance) {
        this.paysNaissance = paysNaissance;
    }

    public String getRegionNaiss() {
        return this.regionNaiss;
    }

    public Client regionNaiss(String regionNaiss) {
        this.setRegionNaiss(regionNaiss);
        return this;
    }

    public void setRegionNaiss(String regionNaiss) {
        this.regionNaiss = regionNaiss;
    }

    public String getDeparteNaiss() {
        return this.departeNaiss;
    }

    public Client departeNaiss(String departeNaiss) {
        this.setDeparteNaiss(departeNaiss);
        return this;
    }

    public void setDeparteNaiss(String departeNaiss) {
        this.departeNaiss = departeNaiss;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public Client telephone(String telephone) {
        this.setTelephone(telephone);
        return this;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPays() {
        return this.pays;
    }

    public Client pays(String pays) {
        this.setPays(pays);
        return this;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getRegion() {
        return this.region;
    }

    public Client region(String region) {
        this.setRegion(region);
        return this;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartement() {
        return this.departement;
    }

    public Client departement(String departement) {
        this.setDepartement(departement);
        return this;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getLieu() {
        return this.lieu;
    }

    public Client lieu(String lieu) {
        this.setLieu(lieu);
        return this;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getRue() {
        return this.rue;
    }

    public Client rue(String rue) {
        this.setRue(rue);
        return this;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getProfession() {
        return this.profession;
    }

    public Client profession(String profession) {
        this.setProfession(profession);
        return this;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPrenomMere() {
        return this.prenomMere;
    }

    public Client prenomMere(String prenomMere) {
        this.setPrenomMere(prenomMere);
        return this;
    }

    public void setPrenomMere(String prenomMere) {
        this.prenomMere = prenomMere;
    }

    public String getNomMere() {
        return this.nomMere;
    }

    public Client nomMere(String nomMere) {
        this.setNomMere(nomMere);
        return this;
    }

    public void setNomMere(String nomMere) {
        this.nomMere = nomMere;
    }

    public String getPrenomPere() {
        return this.prenomPere;
    }

    public Client prenomPere(String prenomPere) {
        this.setPrenomPere(prenomPere);
        return this;
    }

    public void setPrenomPere(String prenomPere) {
        this.prenomPere = prenomPere;
    }

    public String getNomPere() {
        return this.nomPere;
    }

    public Client nomPere(String nomPere) {
        this.setNomPere(nomPere);
        return this;
    }

    public void setNomPere(String nomPere) {
        this.nomPere = nomPere;
    }

    public String getFormatCni() {
        return this.formatCni;
    }

    public Client formatCni(String formatCni) {
        this.setFormatCni(formatCni);
        return this;
    }

    public void setFormatCni(String formatCni) {
        this.formatCni = formatCni;
    }

    public Integer getNumeroCni() {
        return this.numeroCni;
    }

    public Client numeroCni(Integer numeroCni) {
        this.setNumeroCni(numeroCni);
        return this;
    }

    public void setNumeroCni(Integer numeroCni) {
        this.numeroCni = numeroCni;
    }

    public LocalDate getDateDelivCni() {
        return this.dateDelivCni;
    }

    public Client dateDelivCni(LocalDate dateDelivCni) {
        this.setDateDelivCni(dateDelivCni);
        return this;
    }

    public void setDateDelivCni(LocalDate dateDelivCni) {
        this.dateDelivCni = dateDelivCni;
    }

    public LocalDate getDateExpCni() {
        return this.dateExpCni;
    }

    public Client dateExpCni(LocalDate dateExpCni) {
        this.setDateExpCni(dateExpCni);
        return this;
    }

    public void setDateExpCni(LocalDate dateExpCni) {
        this.dateExpCni = dateExpCni;
    }

    public Set<ModePaiement> getModePaiements() {
        return this.modePaiements;
    }

    public void setModePaiements(Set<ModePaiement> modePaiements) {
        if (this.modePaiements != null) {
            this.modePaiements.forEach(i -> i.setClient(null));
        }
        if (modePaiements != null) {
            modePaiements.forEach(i -> i.setClient(this));
        }
        this.modePaiements = modePaiements;
    }

    public Client modePaiements(Set<ModePaiement> modePaiements) {
        this.setModePaiements(modePaiements);
        return this;
    }

    public Client addModePaiement(ModePaiement modePaiement) {
        this.modePaiements.add(modePaiement);
        modePaiement.setClient(this);
        return this;
    }

    public Client removeModePaiement(ModePaiement modePaiement) {
        this.modePaiements.remove(modePaiement);
        modePaiement.setClient(null);
        return this;
    }

    public Set<EtatProcedure> getEtatProcedures() {
        return this.etatProcedures;
    }

    public void setEtatProcedures(Set<EtatProcedure> etatProcedures) {
        if (this.etatProcedures != null) {
            this.etatProcedures.forEach(i -> i.setClient(null));
        }
        if (etatProcedures != null) {
            etatProcedures.forEach(i -> i.setClient(this));
        }
        this.etatProcedures = etatProcedures;
    }

    public Client etatProcedures(Set<EtatProcedure> etatProcedures) {
        this.setEtatProcedures(etatProcedures);
        return this;
    }

    public Client addEtatProcedure(EtatProcedure etatProcedure) {
        this.etatProcedures.add(etatProcedure);
        etatProcedure.setClient(this);
        return this;
    }

    public Client removeEtatProcedure(EtatProcedure etatProcedure) {
        this.etatProcedures.remove(etatProcedure);
        etatProcedure.setClient(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", dateNaiss='" + getDateNaiss() + "'" +
            ", anneeNaiss='" + getAnneeNaiss() + "'" +
            ", lieuNaiss='" + getLieuNaiss() + "'" +
            ", genre='" + getGenre() + "'" +
            ", typeDemande='" + getTypeDemande() + "'" +
            ", email='" + getEmail() + "'" +
            ", destVoyageP='" + getDestVoyageP() + "'" +
            ", motifDeplacement='" + getMotifDeplacement() + "'" +
            ", paysNaissance='" + getPaysNaissance() + "'" +
            ", regionNaiss='" + getRegionNaiss() + "'" +
            ", departeNaiss='" + getDeparteNaiss() + "'" +
            ", telephone='" + getTelephone() + "'" +
            ", pays='" + getPays() + "'" +
            ", region='" + getRegion() + "'" +
            ", departement='" + getDepartement() + "'" +
            ", lieu='" + getLieu() + "'" +
            ", rue='" + getRue() + "'" +
            ", profession='" + getProfession() + "'" +
            ", prenomMere='" + getPrenomMere() + "'" +
            ", nomMere='" + getNomMere() + "'" +
            ", prenomPere='" + getPrenomPere() + "'" +
            ", nomPere='" + getNomPere() + "'" +
            ", formatCni='" + getFormatCni() + "'" +
            ", numeroCni=" + getNumeroCni() +
            ", dateDelivCni='" + getDateDelivCni() + "'" +
            ", dateExpCni='" + getDateExpCni() + "'" +
            "}";
    }
}
