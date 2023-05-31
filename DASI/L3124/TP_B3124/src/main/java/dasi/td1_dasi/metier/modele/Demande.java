/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cchabal
 */
@Entity
public class Demande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    private Date jourDemande;
    private boolean priseEnCharge;
    private Integer note; 
    private boolean etatVisio;
    
    @ManyToOne
    private Matiere matiere;
    
    @ManyToOne
    private Eleve eleve;
    
    @ManyToOne
    private Intervenant intervenant;

    /*public Demande(String description, Date jourDemande, Matiere matiere, Eleve eleve, Intervenant intervenant) {
        this.description = description;
        this.jourDemande = jourDemande;
        this.eleve = eleve;
        this.matiere = matiere;
        this.intervenant = intervenant;
    }*/

    public Demande(String description, Date jourDemande, boolean priseEnCharge, Matiere matiere, Eleve eleve) {
        this.description = description;
        this.jourDemande = jourDemande;
        this.priseEnCharge = priseEnCharge;
        this.matiere = matiere;
        this.eleve = eleve;
        this.etatVisio = false;
    }
    
    public Demande() {
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getJourDemande() {
        return jourDemande;
    }

    public void setJourDemande(Date jourDemande) {
        this.jourDemande = jourDemande;
    }

    public boolean isPriseEnCharge() {
        return priseEnCharge; 
    }

    public void setPriseEnCharge(boolean priseEnCharge) {
        this.priseEnCharge = priseEnCharge;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Intervenant getIntervenant() {
        return intervenant;
    }

    public void setIntervenant(Intervenant intervenant) {
        this.intervenant = intervenant;
    }

    public boolean isEtatVisio() {
        return etatVisio;
    }

    public void setEtatVisio(boolean etatVisio) {
        this.etatVisio = etatVisio;
    }
   
    @Override
    public String toString() {
        return "Demande{" + "id=" + id + ", description=" + description + ", jourDemande=" + jourDemande + ", priseEnCharge=" + priseEnCharge + ", note=" + note + ", etatVisio=" + etatVisio + ", matiere=" + matiere + ", eleve=" + eleve + ", intervenant=" + intervenant + '}';
    }

    
    
        
    
}
