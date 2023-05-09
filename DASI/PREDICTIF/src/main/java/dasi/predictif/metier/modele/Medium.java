/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

/**
 *
 * @author smaillard
 * 
 * Cette classe est une interface non-instanciable définissant les attributs d'un
 * Medium, à savoir: une id unique auto-générée à chaque nouvelle instance héritant de 
 * Medium, un surnom, un genre, une présentation et une Liste de consultations 
 * en lien bi-directionnel OneToMany mappé avec la classe Consultation
 * 
 * L'héritage est de type TABLE_PER_CLASS, chaque nouvelle classe héritant de Medium
 * créera une nouvelle table dans la BDD sans jointure.
 */
@Entity
@Inheritance (strategy = InheritanceType.JOINED)
abstract public class Medium implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String surnom;
    private String genre;
    private String presentation;
    @OneToMany(mappedBy="Medium")
    private List<Consultation> consultations;

    public Medium() {
    }
    
    public Medium(String surnom, String genre, String presentation) {
        this.surnom = surnom;
        this.genre = genre;
        this.presentation = presentation;
        this.consultations = new ArrayList<>();
    }
    
    public void addConsultation(Consultation consultation) {
        if(consultation.getMedium() != this) 
            consultation.setMedium(this);
        this.consultations.add(consultation);        
    }
    
    public Long getId() {
        return id;
    }
    
     public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
    }

    public String getSurnom() {
        return surnom;
    }

    public void setSurnom(String surnom) {
        this.surnom = surnom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }
          
}
