/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.metier.modele;

import javax.persistence.*;

/**
 *
 * @author smaillard
 * 
 * Cette classe défini l'objet métier Astrologue qui hérite de la classe Medium
 * Un Astrologue a une formation et une promotion, en plus des attributs hérités de Medium
 */
@Entity
public class Astrologue extends Medium{
    private String formation;
    private String promotion;

    public Astrologue() {
    }

    public Astrologue(String surnom, String genre, String formation, String promotion, String presentation) {
        super(surnom, genre, presentation);
        this.formation = formation;
        this.promotion = promotion;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    @Override
    public String toString() {
        return "Astrologue{" + "id=" + getId() + ", formation=" + formation + ", promotion=" + promotion + '}';
    }
    
    
    
}
