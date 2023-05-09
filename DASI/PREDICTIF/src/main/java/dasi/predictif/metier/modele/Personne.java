/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.metier.modele;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author smaillard
 * 
 * Cette classe est une interface non-instanciable définissant les attributs d'une
 * Personne, à savoir: une id unique auto-générée à chaque nouvelle instance héritant de 
 * Personne, un nom, un prénom, une email unique, un numéro de téléphone et un 
 * mot de passe.
 * 
 * L'héritage est de type TABLE_PER_CLASS, chaque nouvelle classe héritant de Personne
 * créera une nouvelle table dans la BDD sans jointure.
 * 
 */

@Entity
@Inheritance (strategy = InheritanceType.TABLE_PER_CLASS)
abstract public class Personne implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    protected Long id;
    protected String nom;
    protected String prenom;
    @Column(unique=true)
    protected String email;
    protected String tel;
    protected String motDePasse;

    
    public Personne() {
        
    }
    
    public Personne(String prenom, String nom, String email, String tel, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.motDePasse = motDePasse;
    }
    
    

    public Long getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    @Override
    public String toString() {
        return "Personne{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", tel=" + tel + ", motDePasse=" + motDePasse + '}';
    }
    
    
}
