/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.dao;

import dasi.predictif.metier.modele.Client;
import dasi.predictif.metier.modele.Consultation;
import dasi.predictif.metier.modele.Employe;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author smaillard
 * 
 * Cette classe permet les interactions entre la table Consultation dans la BDD et notre application
 * On peut créer une Consultation, en supprimer une, les modifier, faire des requêtes SQL retournant soit une Consultation,
 * soit une Liste de Consultations
 * 
 */
public class ConsultationDao {
    
    public void create(Consultation consultation) {
        JpaUtil.getEntityManager().persist(consultation);
    }
    
    public void delete(Consultation consultation) {
        JpaUtil.getEntityManager().remove(consultation);
    }
    
    public Consultation update(Consultation consultation) {
        return JpaUtil.getEntityManager().merge(consultation);
    }
    
    public Consultation findById(Long id) {
        return JpaUtil.getEntityManager().find(Consultation.class, id);
    }
    
    public List<Consultation> findAll() {
        String request = "SELECT c FROM Consultation c";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Consultation.class);
        return query.getResultList();
    }
    
     public Consultation findConsultationAttente(Employe employe){
        String request = "SELECT c FROM Consultation c WHERE c.employe = :Employe AND c.etat = :Etat";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Consultation.class);
        query.setParameter("Employe", employe);
        query.setParameter("Etat", Consultation.ETAT_ATTENTE);
        
        List<Consultation> results = query.getResultList();
        
        return results.size() > 0 ? results.get(0) : null;
    }

    public Consultation findConsultationCouranteClient(Client client){
        String request = "SELECT c FROM Consultation c WHERE c.client = :Client AND c.etat != :Etat";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Consultation.class);
        query.setParameter("Client", client);
        query.setParameter("Etat", Consultation.ETAT_FINI);
        
        List<Consultation> results = query.getResultList();
        
        return results.size() > 0 ? results.get(0) : null;
    }
    
    public Consultation findConsultationCouranteEmploye(Employe employe){
        String request = "SELECT c FROM Consultation c WHERE c.employe = :Employe AND c.etat != :Etat";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Consultation.class);
        query.setParameter("Employe", employe);
        query.setParameter("Etat", Consultation.ETAT_FINI);
        
        List<Consultation> results = query.getResultList();
        
        return results.size() > 0 ? results.get(0) : null;
    }
    
}
