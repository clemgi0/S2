/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.dao;

import dasi.td1_dasi.metier.modele.Matiere;
import java.util.List;
import javax.persistence.TypedQuery;

/**
 *
 * @author cchabal
 */
public class MatiereDao {
    public void create(Matiere matiere){
        JpaUtil.obtenirContextePersistance().persist(matiere);  
    }
    
    public Matiere findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Matiere.class,id);
    }
    
    
    public List<Matiere> getAll(){
        String s = "select m from Matiere m";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Matiere.class);
        List<Matiere> liste ;
        try{
            liste = query.getResultList();
        }catch(Exception e){
            liste = null;
        }
        return liste;
    }
    
}
