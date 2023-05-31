package dasi.td1_dasi.metier.services;

import dasi.td1_dasi.dao.DemandeDao;
import dasi.td1_dasi.dao.EleveDao;
import dasi.td1_dasi.dao.EtablissementDao;
import dasi.td1_dasi.dao.IntervenantDao;
import dasi.td1_dasi.dao.JpaUtil;
import dasi.td1_dasi.dao.MatiereDao;
import dasi.td1_dasi.metier.modele.Autre;
import static dasi.td1_dasi.metier.modele.Classe.tableauClasses;
import dasi.td1_dasi.metier.modele.Demande;
import dasi.td1_dasi.metier.modele.Eleve;
import dasi.td1_dasi.metier.modele.Enseignant;
import dasi.td1_dasi.metier.modele.Etablissement;
import dasi.td1_dasi.metier.modele.Etudiant;
import dasi.td1_dasi.metier.modele.Intervenant;
import dasi.td1_dasi.metier.modele.Matiere;
import static dasi.td1_dasi.metier.services.Message.envoyerMail;
import static dasi.td1_dasi.metier.services.Message.envoyerNotification;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author cchabal
 */
public class Service {

    public Service() {
    }

    public boolean inscrireEleve(Eleve eleve, String code) {
        //Message mes = new Message();
        EleveDao eleveDao = new EleveDao();
        EtablissementDao etabDao = new EtablissementDao();
        boolean sortie;
        try {
            JpaUtil.creerContextePersistance();
            List<String> result;
            Etablissement etabCherche = etabDao.findByCode(code);
            Boolean etablissementInscrit = true;
            if(etabCherche==null){
                etablissementInscrit = false;
                EducNetApi educ = new EducNetApi();
                if(educ.getInformationLycee(code)!=null){
                    result = educ.getInformationLycee(code);
                }else{
                    result = educ.getInformationCollege(code);
                }
                etabCherche = new Etablissement(result.get(1), result.get(0), result.get(2), result.get(3), result.get(4), result.get(5), result.get(6), result.get(7), result.get(8));
            }
            
            JpaUtil.ouvrirTransaction();          
            eleveDao.create(eleve);
            if(etablissementInscrit==false){
                etabDao.create(etabCherche);
            }
            eleve.setEtablissement(etabCherche);
            JpaUtil.validerTransaction();
            
            envoyerMail("contact@instruct.if", eleve.getMail(), "Bienvenue sur le réseau INSTRUCT'IF", "Bonjour "+eleve.getPrenom()+", nous te confirmons ton inscription sur le réseau INSTRUCT'IF. Si tu as besoin d'un soutien pour tes leçons ou tes devoirs, rends-toi sur notre site pour une mise en relation avec un intervenant.");
            sortie = true;
        } catch (Exception e) {
            //e.printStackTrace();
            envoyerMail("contact@instruct.if", eleve.getMail(), "Echec de l'inscription sur le réseau INSTRUCT'IF", "Bonjour "+eleve.getPrenom()+", ton inscription sur le réseau INSTRUCT'IF a malheureusement échoué... Merci de recommencer utérieurement.");
            JpaUtil.annulerTransaction();
            sortie = false;
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sortie;
    }
    
    public Eleve authentifierEleve(String mail, String motDePasse) {
        Eleve eleve = null;
        EleveDao eleveDao = new EleveDao();
        try {
            JpaUtil.creerContextePersistance();
            eleve = eleveDao.authentificationMail(mail, motDePasse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return eleve;
    }
    
    public Intervenant authentifierIntervenant(String login, String motDePasse) {
        Intervenant intervenant = null;
        IntervenantDao intervenantDao = new IntervenantDao();
        try {
            JpaUtil.creerContextePersistance();
            intervenant = intervenantDao.authentificationLogin(login, motDePasse);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return intervenant;
    }
   
    
    public boolean creerDemande(Matiere matiere, String description, Eleve eleve){
        Date jour = new Date();
        
        DateFormat formatH = new SimpleDateFormat("HH:mm");
        String heure = formatH.format(jour);
   
        Demande demande = new Demande(description, jour, false, matiere, eleve);
        
        boolean sortie = false;
        
        IntervenantDao intervenantDao = new IntervenantDao();
        DemandeDao demandeDao = new DemandeDao();
        
        Intervenant intervenantChoisi = null;
        try{
            JpaUtil.creerContextePersistance();
            intervenantChoisi = (Intervenant) intervenantDao.trouverIntervenant(eleve.getClasse());
            
            //intervenant trouve
            if(intervenantChoisi != null){
                
                demande.setIntervenant(intervenantChoisi);
                intervenantChoisi.setStatut(true);
                intervenantChoisi.augmenterNombreDemandes();
                
                //creation demande et mise a jour du statut
                JpaUtil.ouvrirTransaction();
                demandeDao.create(demande);
                intervenantDao.update(intervenantChoisi);
                JpaUtil.validerTransaction();
                
                sortie = true;

                //gerer l'envoi de mail avec envoyerNotification
                envoyerNotification(intervenantChoisi.getTelephone(), "Bonjour "+intervenantChoisi.getPrenom()+". Merci de prendre en charge la demande de soutien en <<"+matiere.getNom()+">> demandée à "+heure+" par "+eleve.getPrenom()+" en classe de "+tableauClasses[eleve.getClasse()]);

            }
            
        }catch (Exception e){
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
                                       
        return sortie;
    } 
    
    public Demande consulterDemandeEnCoursIntervenant(Intervenant intervenant){
        DemandeDao demandeDao = new DemandeDao();
        Demande demande = null;
        try {
            JpaUtil.creerContextePersistance();
            demande = demandeDao.getDemandeEnCoursIntervenant(intervenant);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return demande;
    }
    
    public Demande consulterDemandeEnCoursEleve(Eleve eleve){
        DemandeDao demandeDao = new DemandeDao();
        Demande demande = null;
        try {
            JpaUtil.creerContextePersistance();
            demande = demandeDao.getDemandeEnCoursEleve(eleve);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return demande;
    }
    
    public boolean lancerVisioIntervenant(Demande demande){
        //permet de dire que l'intervenant a lance la visio
        demande.setEtatVisio(true);
        boolean sortie = false;
        DemandeDao demandeDao = new DemandeDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            demande = demandeDao.update(demande);
            JpaUtil.validerTransaction();
            sortie = true;
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sortie;        
    }
    
    public boolean arreterVisio(Demande demande){
        boolean sortie = false;
        IntervenantDao intervenantDao = new IntervenantDao();
        DemandeDao demandeDao = new DemandeDao();
        demande.setPriseEnCharge(true);
        demande.setEtatVisio(false);
        Intervenant intervenant = demande.getIntervenant();
        intervenant.setStatut(false);
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            demandeDao.update(demande);
            intervenantDao.update(intervenant);
            JpaUtil.validerTransaction();
            sortie = true;
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sortie;
    }
    
    public boolean evaluerDemande(Demande demande, Integer note){
        boolean sortie = false;
        //mettre à jour une demande avec la note 
        DemandeDao demandeDao = new DemandeDao();
        demande.setNote(note);
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            demandeDao.update(demande);
            JpaUtil.validerTransaction();
            sortie = true;
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sortie;
    }
    
    public List<Demande> genererHistoriqueDemandesEleve(Eleve eleve){
        List<Demande> liste = null;
        DemandeDao demande = new DemandeDao();
        try {
            JpaUtil.creerContextePersistance();
            liste = demande.getDemandesEleve(eleve);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
        
    public List<Demande> genererHistoriqueDemandesIntervenant(Intervenant intervenant){
        List<Demande> liste = null;
        DemandeDao demande = new DemandeDao();
        Long id = intervenant.getId();
        try {
            JpaUtil.creerContextePersistance();
            liste = demande.getInterventions(intervenant);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
    
    public List<Matiere> genererListeMatieres(){
        List<Matiere> liste = null;
        MatiereDao mat = new MatiereDao();
        try {
            JpaUtil.creerContextePersistance();
            liste = mat.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
    
    public boolean creerBDIntervenant() {
        IntervenantDao emp = new IntervenantDao();
        boolean sortie = false;
       
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Etudiant e1 = new Etudiant( "INSA LYON", "Informatique","CHABAL", "Chloe", "0767872236", "cchabal", "coucou",3, 6, 5, false);
            Etudiant e2 = new Etudiant("Sorbonne","Mathématiques", "FAVRO", "Samuel", "0642049305", "sfavro", "coucou1", 1, 6, 3, false);
            Autre e3 = new Autre("Retraité", "DEKEW", "Simon", "0713200950", "sdekew4845", "coucou3", 3, 4, 1,true);
            Enseignant e4 = new Enseignant("College", "LOU", "Flavien", "0437340532", "flavien.lou", "coucou4", 0, 6, 5, true);
            Enseignant e5 = new Enseignant("Lycee", "HERNENDEZ", "Vincent", "0441564193", "vhernendez", "coucou5", 4, 6, 0, false);

            emp.create(e1);
            emp.create(e2);
            emp.create(e3);
            emp.create(e4);
            emp.create(e5);
            JpaUtil.validerTransaction();
            sortie = true;
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sortie;
    }
    
    public boolean creerBDMatiere() {
        MatiereDao emp = new MatiereDao();
        
        boolean sortie = false;
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            
            Matiere m1 = new Matiere("Informatique");
            Matiere m2 = new Matiere("Histoire-Geo");
            Matiere m3 = new Matiere("Français");
            Matiere m4 = new Matiere("Mathématiques");
            Matiere m5 = new Matiere("Anglais");
            Matiere m6 = new Matiere("Sciences de la vie et de la terre");
            
            emp.create(m1);
            emp.create(m2);
            emp.create(m3);
            emp.create(m4);
            emp.create(m5);
            emp.create(m6);
            JpaUtil.validerTransaction();
            sortie = true;
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return sortie;
    }
    
    
    public Matiere trouverMatiereParId(Long idMatiere) {
        MatiereDao mat = new MatiereDao();
        Matiere matiere = null;
        try {
            JpaUtil.creerContextePersistance();
            matiere = mat.findById(idMatiere);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return matiere;
    }
    
    public Demande trouverDemandeParId(Long idDemande) {
        DemandeDao demandeDao = new DemandeDao();
        Demande demande = null;
        try {
            JpaUtil.creerContextePersistance();
            demande = demandeDao.findById(idDemande);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return demande;
    }
    
    public Intervenant trouverIntervenantParId(Long idIntervenant) {
        IntervenantDao intervenantDao = new IntervenantDao();
        Intervenant intervenant = null;
        try {
            JpaUtil.creerContextePersistance();
            intervenant = intervenantDao.findById(idIntervenant);
        } catch (Exception e) {
            e.printStackTrace();   
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return intervenant;
    }
    
    public Eleve trouverEleveParId(Long idEleve) {
        EleveDao eleveDao = new EleveDao();
        Eleve eleve = null;
        try {
            JpaUtil.creerContextePersistance();
            eleve = eleveDao.findById(idEleve);
        } catch (Exception e) {
            e.printStackTrace();   
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return eleve;
    }
        
    public List<Object[]> genererStatistiqueDemandeParCodeIPS(){
        DemandeDao demandeDao = new DemandeDao();
        List<Object[]> liste = null;
        try {
            JpaUtil.creerContextePersistance();
            liste = demandeDao.getNombreDemandeParEtablissementIPS();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
    
    public List<Object[]> genererStatistiqueDemandeParDepartement(){
        DemandeDao demandeDao = new DemandeDao();
        List<Object[]> liste = null;
        try {
            JpaUtil.creerContextePersistance();
            liste = demandeDao.getNombreDemandeParEtablissementDepartement();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
    
    public List<Object[]> genererStatistiqueDemandeParClasse(){
        DemandeDao demandeDao = new DemandeDao();
        List<Object[]> liste = null;
        try {
            JpaUtil.creerContextePersistance();
            liste = demandeDao.getNombreDemandeParClasse();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
    
    public List<Object[]> genererStatistiqueDemandeParMatiere(){
        DemandeDao demandeDao = new DemandeDao();
        List<Object[]> liste = null;
        try {
            JpaUtil.creerContextePersistance();
            liste = demandeDao.getNombreDemandeParMatiere();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerContextePersistance();
        }
        return liste;
    }
    
    public int genererNombreDemandesTotales(){
        DemandeDao demandeDao = new DemandeDao();
        int nbDemandes ;
        try{
            JpaUtil.creerContextePersistance();
            nbDemandes = demandeDao.getNombreDemandesTotales();
        }catch(Exception e){
            e.printStackTrace();
            nbDemandes=0;
        }finally{
            JpaUtil.fermerContextePersistance();
        }
        return nbDemandes;
    }
   
    
}
    



