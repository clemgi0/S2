/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.dao;
import dasi.td1_dasi.metier.modele.Eleve;
import javax.persistence.TypedQuery;

/**
 *
 * @author cchabal
 */
public class EleveDao {
    
    public void create(Eleve e){
        JpaUtil.obtenirContextePersistance().persist(e);        
    }
    
    public Eleve findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Eleve.class,id);
    }
    
    public Eleve authentificationMail(String mail, String motDePasse){
        String s = "select e from Eleve e where e.mail = :mail and e.motDePasse = :mdp";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Eleve.class);
        query.setParameter("mail",mail);
        query.setParameter("mdp",motDePasse);
        Eleve eleve;
        try{
            eleve = (Eleve) query.getSingleResult();
        }catch(Exception e){
            eleve = null;
        }
        return eleve;
    }
    
}

