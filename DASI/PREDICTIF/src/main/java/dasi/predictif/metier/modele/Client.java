/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.metier.modele;

import dasi.predictif.util.AstroNetApi;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author smaillard
 * 
 * Cette classe défini l'objet métier Client qui hérite de la classe Personne
 * Un client possède une adresse, une dat de naissance, un signe du zodiaque, 
 * un signe chinois, une couleur favorite, un animal totem, et une liste 
 * de consultations en plus des attributs hérités de la classe Personne.
 * 
 * Les attributs signe du zodiaque, signe chinois, couleur favorite et animal 
 * totem sont récupérés via l'API AstroNet dans le constructeur.
 * 
 * Un lien bi-directionnel OneToMany est mappé sur l'attribut Liste de consultations
 * avec la classe Consultation.
 * 
 */
@Entity
public class Client extends Personne {
    private String adresse;
    @Temporal(TemporalType.DATE)
    private Date dateNaissance;
    private String signeZodiaque;
    private String signeChinois;
    private String couleur;
    private String animal;
    @OneToMany(mappedBy="client")
    private List<Consultation> consultations;
    
    public Client() {
        
    }

    public Client(String prenom, String nom, String email, String tel, String adresse, Date dateNaissance, String motDePasse) throws IOException {
        super(prenom, nom, email, tel, motDePasse);
        this.adresse = adresse;
        this.dateNaissance = dateNaissance;
        
        AstroNetApi astroApi = new AstroNetApi();
        List<String> profil = astroApi.getProfil(prenom, dateNaissance);
        this.signeZodiaque = profil.get(0);
        this.signeChinois = profil.get(1);
        this.couleur = profil.get(2);
        this.animal = profil.get(3);
        
        consultations = new ArrayList<>();
    }
    
    public void addConsultation(Consultation consultation) {
        if(consultation.getClient() != this) 
            consultation.setClient(this);
        this.consultations.add(consultation);        
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getSigneZodiaque() {
        return signeZodiaque;
    }

    public void setSigneZodiaque(String signeZodiaque) {
        this.signeZodiaque = signeZodiaque;
    }

    public String getSigneChinois() {
        return signeChinois;
    }

    public void setSigneChinois(String signeChinois) {
        this.signeChinois = signeChinois;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getAnimal() {
        return animal;
    }

    public void setAnimal(String animal) {
        this.animal = animal;
    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", tel=" + tel + ", adresse=" + adresse + ", dateNaissance=" + dateNaissance + ", motDePasse=" + motDePasse + ", signeZodiaque=" + signeZodiaque + ", signeChinois=" + signeChinois + ", couleur=" + couleur + ", animal=" + animal + '}';
    }
}
