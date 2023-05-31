/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.td1_dasi.dao;

import dasi.td1_dasi.metier.modele.Etablissement;
import javax.persistence.TypedQuery;

/**
 *
 * @author cchabal
*/
public class EtablissementDao {
    public void create(Etablissement e){
        JpaUtil.obtenirContextePersistance().persist(e);
        
    }
 
    public Etablissement findById(Long id){
        return JpaUtil.obtenirContextePersistance().find(Etablissement.class,id);
    }
    
    public Etablissement findByCode(String code){
        String s = "select e from Etablissement e where e.code = :code";
        TypedQuery query = JpaUtil.obtenirContextePersistance().createQuery(s, Etablissement.class);
        query.setParameter("code",code);
        Etablissement etab;
        try{
            etab = (Etablissement) query.getSingleResult();
        }catch(Exception e){
            etab = null;
        }
        return etab;
    }
    
}
