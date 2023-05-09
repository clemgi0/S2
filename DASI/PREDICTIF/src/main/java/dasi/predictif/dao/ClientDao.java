/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.dao;

import dasi.predictif.metier.modele.Client;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author smaillard
 * 
 * Cette classe permet les interactions entre la table Client dans la BDD et notre application
 * On peut créer un Client, en supprimer un, les modifier, et aussi faire des requêtes SQL retournant soit un Client,
 * soit une Liste de Clients
 * 
 */
public class ClientDao {
    
    public void create(Client client) {
        JpaUtil.getEntityManager().persist(client);
    }
    
    public void delete(Client client) {
        JpaUtil.getEntityManager().remove(client);
    }
    
    public Client update(Client client) {
        return JpaUtil.getEntityManager().merge(client);
    }
    
    public Client findById(Long id) {
        return JpaUtil.getEntityManager().find(Client.class, id);
    }
    
    public Client findByEmail(String email){
        String request = "SELECT c FROM Client c WHERE c.email = :Email";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Client.class);
        query.setParameter("Email", email);
        
        List<Client> results = query.getResultList();
        
        return results.size() > 0 ? results.get(0) : null;
    }
    
    public List<Client> findAll() {
        String request = "SELECT c FROM Client c ORDER BY c.nom ASC";
        TypedQuery query = JpaUtil.getEntityManager().createQuery(request, Client.class);
        return query.getResultList();
    }
    
}
