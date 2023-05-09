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
 * Cette classe défini l'objet métier Cartomancient qui hérite de la classe Medium
 * Un cartomancien n'a aucun attribut en plus que ceux hérités de la classe Medium
 * 
 */
@Entity
public class Cartomancien extends Medium{

    public Cartomancien() {
    }
    
    public Cartomancien(String surnom, String genre, String presentation) {
        super(surnom, genre, presentation);
    }

    @Override
    public String toString() {
        return "Cartomancien{id=" + getId() + "}";
    }
}
