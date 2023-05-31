package dasi.td1_dasi.metier.modele;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cchabal
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Intervenant implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String nom;
    protected String prenom;
    protected String telephone;
    protected String login;
    protected String motDePasse;
    protected Integer niveauInf; 
    protected Integer niveauSup;
    protected Integer nbDemandes;
    protected boolean statut;
    //false signifie que l'intervenat est libre 
      
   
    public Intervenant() {
    }

    public Intervenant(String nom, String prenom, String telephone, String login, String motDePasse, Integer niveauInf, Integer niveauSup) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.login = login;
        this.motDePasse = motDePasse;
        this.niveauInf = niveauInf;
        this.niveauSup = niveauSup; 
    }

    public Intervenant(String nom, String prenom, String telephone, String login, String motDePasse, Integer niveauInf, Integer niveauSup, Integer nbDemandes, boolean statut) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.login = login;
        this.motDePasse = motDePasse;
        this.niveauInf = niveauInf;
        this.niveauSup = niveauSup;
        this.nbDemandes = nbDemandes;
        this.statut = statut;
    }


    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }
    
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getLogin() {
        return login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Integer getNiveauInf() {
        return niveauInf;
    }

    public void setNiveauInf(Integer niveauInf) {
        this.niveauInf = niveauInf;
    }

    public Integer getNiveauSup() {
        return niveauSup;
    }

    public void setNiveauSup(Integer niveauSup) {
        this.niveauSup = niveauSup;
    }

    public Integer getNbDemandes() {
        return nbDemandes;
    }

    public void setNbDemandes(Integer nbDemandes) {
        this.nbDemandes = nbDemandes;
    }
    
    public void augmenterNombreDemandes(){
        this.nbDemandes++;
    }

    @Override
    public String toString() {
        return "Intervenant{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", telephone=" + telephone + ", login=" + login + ", motDePasse=" + motDePasse + ", niveauInf=" + niveauInf + ", niveauSup=" + niveauSup + ", nbDemandes=" + nbDemandes + '}';
    }
      
    
}
