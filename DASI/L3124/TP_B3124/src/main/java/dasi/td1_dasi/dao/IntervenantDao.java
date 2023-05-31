/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.dao;

import dasi.td1_dasi.metier.modele.Intervenant;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author cchabal
 */
public class IntervenantDao {
    
    public void create(Intervenant e){
        JpaUtil.obtenirContextePersistance().persist(e);
    }
    
    public Intervenant update(Intervenant intervenant){
        return JpaUtil.obtenirContextePersistance().merge(intervenant);
    }
    
    public Intervenant findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Intervenant.class,id);
    }
    
    public Intervenant authentificationLogin(String login, String motDePasse){
        String s = "select i from Intervenant i where i.login = :login and i.motDePasse = :mdp";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Intervenant.class);
        query.setParameter("login",login);
        query.setParameter("mdp",motDePasse);
        Intervenant intervenant;
        try{
            intervenant = (Intervenant) query.getSingleResult();
        }catch(Exception e){
            intervenant =  null;
        }
        return intervenant;
    }
    
    public Intervenant trouverIntervenant(Integer niveau){
        String s = "select i from Intervenant i where i.niveauInf <= :niveau and i.niveauSup >= :niveau and i.statut=0 order by i.nbDemandes asc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Intervenant.class);
        query.setParameter("niveau",niveau);
        Intervenant intervenant;
        try{
            List<Intervenant> liste= query.getResultList();
            intervenant = liste.get(0);
        }catch(Exception e){
            intervenant =  null;
        }
        return intervenant;
    }
   
    
}
