/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.dao;

import dasi.predictif.metier.modele.Employe;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author smaillard
 * Cette classe permet les interactions entre la table Employe dans la BDD et notre application
 * On peut créer un Employe, en supprimer un, les modifier, et aussi faire des requêtes SQL retournant soit un Employe,
 * soit une Liste de Employes
 * 
 */
public class EmployeDao {
    
    public void create(Employe client) {
        JpaUtil.getEntityManager().persist(client);
    }
    
    public void delete(Employe client) {
        JpaUtil.getEntityManager().remove(client);
    }
    
    public Employe update(Employe client) {
        return JpaUtil.getEntityManager().merge(client);
    }
    
    public Employe findById(Long id) {
        return JpaUtil.getEntityManager().find(Employe.class, id);
    }
    
    public Employe findByEmail(String email){
        String request = "SELECT e FROM Employe e WHERE e.email = :Email";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Employe.class);
        query.setParameter("Email", email);
        
        List<Employe> results = query.getResultList();
        
        return results.size() > 0 ? results.get(0) : null;
    }
    
    public List<Employe> findAll() {
        String request = "SELECT e FROM Employe e ORDER BY e.nom ASC";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Employe.class);
        return query.getResultList();
    }
    
    public List<Employe> findEmployesDispo(String genre) {
        String request = "SELECT e FROM Employe e WHERE e.dispo = :Dispo AND e.genre = :Genre";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Employe.class);
        query.setParameter("Dispo", true);
        query.setParameter("Genre", genre);
        return query.getResultList();
    }
    
}
