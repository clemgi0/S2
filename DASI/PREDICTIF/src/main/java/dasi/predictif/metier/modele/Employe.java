/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.metier.modele;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author smaillard
 * 
 * Cette classe défini l'objet métier Employe qui hérite de la classe Personne
 * Un employe possède un genre, une disponibilité et une liste 
 * de consultations en plus des attributs hérités de la classe Personne.
 * 
 * Un lien bi-directionnel OneToMany est mappé sur l'attribut Liste de consultations
 * avec la classe Consultation.
 * 
 */

@Entity
public class Employe extends Personne {
    private String genre;
    private boolean dispo;
    @OneToMany(mappedBy="employe")
    private List<Consultation> consultations;
    
    
    public Employe() {
        
    }

    public Employe(String prenom, String nom, String email, String tel, String motDePasse, String genre) {
        super(prenom, nom, email, tel, motDePasse);
        this.genre = genre;
        this.dispo = true;
        this.consultations = new ArrayList<>();
    }
    
    public void addConsultation(Consultation consultation) {
        if(consultation.getEmploye() != this) 
            consultation.setEmploye(this);
        this.consultations.add(consultation);        
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public boolean isDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }    
    
    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    @Override
    public String toString() {
        return "Employe{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", motDePasse=" + motDePasse + ", tel=" + tel + ", genre=" + genre + ", dispo=" + dispo + ", consultations=" + consultations.size() + '}';
    }
    
}
