/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.metier.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author cchabal
 */
@Entity
public class Enseignant extends Intervenant{
    private String typeEtablissement;
    
    public Enseignant() {
    }

    public Enseignant(String typeEtablissement, String nom, String prenom, String telephone, String login, String motDePasse, Integer niveauInf, Integer niveauSup, Integer nbDemandes, boolean statut) {
        super(nom, prenom, telephone, login, motDePasse, niveauInf, niveauSup, nbDemandes, statut);
        this.typeEtablissement = typeEtablissement;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMotDePasse() {
        return motDePasse;
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

    public String getTypeEtablissement() {
        return typeEtablissement;
    }

    public void setTypeEtablissement(String typeEtablissement) {
        this.typeEtablissement = typeEtablissement;
    }
    
    
}
