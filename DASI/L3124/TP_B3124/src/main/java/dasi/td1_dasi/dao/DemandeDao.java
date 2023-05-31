/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.dao;

import dasi.td1_dasi.metier.modele.Demande;
import dasi.td1_dasi.metier.modele.Eleve;
import dasi.td1_dasi.metier.modele.Intervenant;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author cchabal
 */
public class DemandeDao {
    
    public void create(Demande d){
        JpaUtil.obtenirContextePersistance().persist(d);        
    }
       
    public Demande update(Demande d){
        return JpaUtil.obtenirContextePersistance().merge(d);
    }
    
    public Demande findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Demande.class,id);
    }
    
    public List<Demande> getInterventions(Intervenant intervenant){
        String s = "select d from Demande d where d.intervenant = :intervenant";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        query.setParameter("intervenant", intervenant);
        List<Demande> liste;
        try{
            liste = query.getResultList();
        }catch(Exception e){
            liste = null;
        }
        return liste;
    }
        
    public List<Demande> getDemandesEleve(Eleve eleve){
        String s = "select d from Demande d where d.eleve = :eleve order by d.priseEnCharge desc";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        query.setParameter("eleve", eleve);
        List<Demande> liste;
        try{
            liste = query.getResultList();
        }catch(Exception e){
            liste = null;
        }
        return liste;
    }
            
    public Demande getDemandeEnCoursIntervenant(Intervenant intervenant){
        String s ="select d from Demande d where d.intervenant=:intervenant and d.priseEnCharge=0";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        query.setParameter("intervenant", intervenant);
        Demande demande;
        try{
            demande = (Demande) query.getSingleResult();
        }catch(Exception e){
            demande = null;
        }
        return demande;
    }
    
    public Demande getDemandeEnCoursEleve(Eleve eleve){
        String s ="select d from Demande d where d.eleve=:eleve and d.priseEnCharge=0";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        query.setParameter("eleve", eleve);
        Demande demande;
        try{
            demande = (Demande) query.getSingleResult();
        }catch(Exception e){
            demande = null;
        }
        return demande;
    }
    
    public List<Object[]> getNombreDemandeParEtablissementIPS(){
        String s = "select count(d), d.eleve.etablissement.ips from Demande d group by d.eleve.etablissement.ips";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        List<Object[]> liste;
        try{
            liste = (List<Object[]>)query.getResultList();
        }catch(Exception e){
            liste = null;
        }
        return liste;
    }
    
    public List<Object[]> getNombreDemandeParEtablissementDepartement(){
        String s = "select count(d), d.eleve.etablissement.codeDepartement from Demande d group by d.eleve.etablissement.codeDepartement";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        List<Object[]> liste;
        try{
            liste = (List<Object[]>)query.getResultList();
        }catch(Exception e){
            liste = null;
        }
        return liste;
    }
    
    public List<Object[]> getNombreDemandeParClasse(){
        String s = "select count(d), d.eleve.classe from Demande d group by d.eleve.classe";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        List<Object[]> liste;
        try{
            liste = (List<Object[]>)query.getResultList();
        }catch(Exception e){
            liste = null;
        }
        return liste;
    }
    
    public List<Object[]> getNombreDemandeParMatiere(){
        String s = "select count(d), d.matiere from Demande d group by d.matiere";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        List<Object[]> liste;
        try{
            liste = (List<Object[]>)query.getResultList();
        }catch(Exception e){
            liste = null;
        }
        return liste;
    }
    
    public int getNombreDemandesTotales(){
        String s = "select d from Demande d";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Demande.class);
        List<Demande> liste = query.getResultList();
        int nbDemandes;
        try{
            nbDemandes = (int) liste.size();
        }catch(Exception e){
            nbDemandes = 0;
        }
        return nbDemandes;
    }
    
}
