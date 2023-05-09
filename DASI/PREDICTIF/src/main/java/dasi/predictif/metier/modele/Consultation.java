/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.metier.modele;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author smaillard
 * 
 * Cette classe défini l'objet métier Consultation.
 * 
 * Une consultation possède une id unique auto-générée à chaque nouvelle instance,
 * un etat, un commentaire, une date de demande de la consultation, une date de 
 * début de consultation, une date de fin de consultation en attributs.
 * 
 * Elle possède aussi des liens bi-directionnels ManyToOne sur les attributs 
 * Client, Employe et Medium mappé sur les classes du même nom.
 * 
 */

@Entity
public class Consultation implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private int etat;
    private String commentaire;
    @Temporal(TemporalType.DATE)
    private Date dateDemande;
    @Temporal(TemporalType.DATE)
    private Date dateDebutConsult;
    @Temporal(TemporalType.DATE)
    private Date dateFinConsult;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Employe employe;
    @ManyToOne
    private Medium medium;
    
    public static final int ETAT_ATTENTE = 0;
    public static final int ETAT_EN_COURS = 1;
    public static final int ETAT_FINI = 2;
    
    
    public Consultation() {
        
    }

    public Consultation(Date dateDemande, Client client, Medium medium, Employe employe) {
        this.etat = ETAT_ATTENTE;
        this.dateDemande = dateDemande;
        this.client = client;
        this.medium = medium;
        this.employe = employe;
    }

    public Long getId() {
        return id;
    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateDemande() {
        return dateDemande;
    }

    public void setDateDemande(Date dateDemande) {
        this.dateDemande = dateDemande;
    }

    public Date getDateDebutConsult() {
        return dateDebutConsult;
    }

    public void setDateDebutConsult(Date dateDebutConsult) {
        this.dateDebutConsult = dateDebutConsult;
    }

    public Date getDateFinConsult() {
        return dateFinConsult;
    }

    public void setDateFinConsult(Date dateFinConsult) {
        this.dateFinConsult = dateFinConsult;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Consultation{" + "id=" + id + ", etat=" + etat + ", commentaire=" + commentaire + ", dateDemande=" + dateDemande + ", dateDebutConsult=" + dateDebutConsult + ", dateFinConsult=" + dateFinConsult + ", client=" + client.getId() + ", employe=" + employe.getId() + ", medium=" + medium.getId() + '}';
    }
    
}
