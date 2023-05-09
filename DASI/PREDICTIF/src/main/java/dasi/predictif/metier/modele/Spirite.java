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
 * Cette classe défini l'objet métier Spirite qui hérite de la classe Medium
 * Un Spirite a un support en plus que des attributs hérités de la classe Medium
 * 
 */
@Entity
public class Spirite extends Medium{
    private String support;

    public Spirite() {
    }

    public Spirite(String surnom, String genre, String support, String presentation) {
        super(surnom, genre, presentation);
        this.support = support;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    @Override
    public String toString() {
        return "Spirite{" + "id=" + getId() + ", support=" + support + '}';
    }
}
