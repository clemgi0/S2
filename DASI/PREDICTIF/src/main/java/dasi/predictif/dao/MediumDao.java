/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.dao;

import dasi.predictif.metier.modele.Medium;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author smaillard
 * 
 * Cette classe permet les interactions entre la table Medium dans la BDD et notre application
 * On peut créer un Medium, en supprimer un, les modifier, et aussi faire des requêtes SQL retournant soit un Medium,
 * soit une Liste de Mediums
 * 
 */
public class MediumDao {
    
    public void create(Medium medium) {
        JpaUtil.getEntityManager().persist(medium);
    }
    
    public void delete(Medium medium) {
        JpaUtil.getEntityManager().remove(medium);
    }
    
    public Medium update(Medium medium) {
        return JpaUtil.getEntityManager().merge(medium);
    }
    
    public Medium findById(Long id) {
        return JpaUtil.getEntityManager().find(Medium.class, id);
    }
    
    public List<Medium> findAll() {
        String request = "SELECT m FROM Medium m ORDER BY m.id ASC";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Medium.class);
        return query.getResultList();
    }
    
}
