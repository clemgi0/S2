package dasi.td1_dasi.vue;


import dasi.td1_dasi.dao.JpaUtil;
import static dasi.td1_dasi.metier.modele.Classe.niveauMax;
import static dasi.td1_dasi.metier.modele.Classe.tableauClasses;
import dasi.td1_dasi.metier.modele.Demande;
import dasi.td1_dasi.metier.modele.Eleve;
import dasi.td1_dasi.metier.modele.Etablissement;
import dasi.td1_dasi.metier.modele.Etudiant;
import dasi.td1_dasi.metier.modele.Intervenant;
import dasi.td1_dasi.metier.modele.Matiere;
import dasi.td1_dasi.metier.services.Service;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cchabal
 */
public class Main {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        JpaUtil.creerFabriquePersistance();        
        creerBD();
        testerInscriptionEleve();
        testerAuthentificationEleve();
        testerAuthentificationIntervenant();
        testerCreationDemande();
        testerGestionDemandeIntervenant();
        testerFinVisio();
        testerConsultationInterventionsIntervenant();
        testerConsultationDemandeEleve();
        testerStatistiques();
        JpaUtil.fermerFabriquePersistance();
    }
    
    public static void testerInscriptionEleve(){
        System.out.println("TEST INSCRIPTION ");
        try {
            System.out.println("***********************************************************");
            System.out.println("Test 1");
            //inscriptions qui marchent
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Service ser = new Service();
            String code1 = "0691664J";//college 69
            String code2 = "0300098U";//college 30
            Eleve eleve = new Eleve("Chloe", "Chabal", dateFormat.parse("01/07/2010"), 0, "chloe@gmail.com", "coucou");
            
            System.out.println("Liste des classes : ");
            for(int i=0; i<=niveauMax;i++){
                System.out.println(tableauClasses[i]);
            }
            
            boolean sortie = ser.inscrireEleve(eleve, code2);
            
            System.out.println("Le resultat est : "+sortie);
            if(sortie ==true){
                
                System.out.println("Inscription réussie ! ");
                System.out.println("Le profil de l'eleve est : ");
                System.out.println("Nom : "+eleve.getNom()+" Prenom : "+eleve.getPrenom()+" Classe : "+tableauClasses[eleve.getClasse()]+" Etablissement : "+eleve.getEtablissement().getNom());
                
            }else{
                System.out.println("L'inscription a échoué ! Veuillez recommencer \n");
            }
            
            
            System.out.println("***********************************************************");
            System.out.println("Test 2");
            System.out.println("Liste des classes : ");
            for(int i=0; i<=niveauMax;i++){
                System.out.println(tableauClasses[i]);
            }
            Eleve eleve1 = new Eleve("Widad", "Farhat",dateFormat.parse("17/06/2005"), 6, "widad@gmail.com", "coucou2" );
            boolean sortie1 = ser.inscrireEleve(eleve1,"0840003X");
            
            System.out.println("Le resultat est : "+sortie1);
            if(sortie1==true){
                System.out.println("Inscription réussie ! ");
                System.out.println("Le profil de l'eleve est : ");
                System.out.println("Nom : "+eleve1.getNom()+" Prenom : "+eleve1.getPrenom()+" Classe : "+tableauClasses[eleve1.getClasse()]+" Etablissement : "+eleve1.getEtablissement().getNom());
            }else{
                System.out.println("L'inscription a échoué ! Veuillez recommencer \n");
            }
            
            System.out.println("***********************************************************");
            System.out.println("Test 3");
            System.out.println("Liste des classes : ");
            for(int i=0; i<=niveauMax;i++){
                System.out.println(tableauClasses[i]);
            }
            Eleve eleve2 = new Eleve("Theo", "Lagrange",dateFormat.parse("17/05/2007"), 3, "theo@gmail.com", "coucou3" );
            boolean sortie2 = ser.inscrireEleve(eleve2, code2);
            
            System.out.println("Le resultat est : "+sortie2);
            if(sortie2==true){
                System.out.println("Inscription réussie ! ");
                System.out.println("Le profil de l'eleve est : ");
                System.out.println("Nom : "+eleve2.getNom()+" Prenom : "+eleve2.getPrenom()+" Classe : "+tableauClasses[eleve2.getClasse()]+" Etablissement : "+eleve2.getEtablissement().getNom());
            }else{
                System.out.println("L'inscription a échoué ! Veuillez recommencer \n");
            }
            System.out.println("***********************************************************");
            System.out.println("Test 4");
            System.out.println("Liste des classes : ");
            for(int i=0; i<=niveauMax;i++){
                System.out.println(tableauClasses[i]);
            }
            Eleve eleve3 = new Eleve("Ludivine", "Asthier",dateFormat.parse("24/02/2008"), 3, "ludivine@gmail.com", "coucou4" );
            boolean sortie3 = ser.inscrireEleve(eleve3, code1);
            
            System.out.println("Le resultat est : "+sortie3);
            if(sortie3==true){
                System.out.println("Inscription réussie ! ");
                System.out.println("Le profil de l'eleve est : ");
                System.out.println("Nom : "+eleve3.getNom()+" Prenom : "+eleve3.getPrenom()+" Classe : "+tableauClasses[eleve3.getClasse()]+" Etablissement : "+eleve3.getEtablissement().getNom());
            }else{
                System.out.println("L'inscription a échoué ! Veuillez recommencer \n");
            }
            
            //inscriptions qui échouent
            
            System.out.println("***********************************************************");
            System.out.println("Test 5");
            System.out.println("Liste des classes : ");
            for(int i=0; i<=niveauMax;i++){
                System.out.println(tableauClasses[i]);
            }
            Eleve eleve4 = new Eleve("mateo", "paul",dateFormat.parse("14/06/2002"), 4, "widad@gmail.com", "coucou3" );
            boolean sortie4 = ser.inscrireEleve(eleve4, code1);
            
            System.out.println("Le resultat est : "+sortie4);
            if(sortie4==true){
                System.out.println("Inscription réussie ! ");
                System.out.println("Le profil de l'eleve est : ");
                System.out.println("Nom : "+eleve4.getNom()+" Prenom : "+eleve4.getPrenom()+" Classe : "+tableauClasses[eleve4.getClasse()]+" Etablissement : "+eleve4.getEtablissement().getNom());
            }else{
                System.out.println("L'inscription a échoué ! Veuillez recommencer \n");
            }

        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
    public static void testerAuthentificationEleve(){
        System.out.println("TEST AUTHENTIFICATION ELEVE ");
        Service ser = new Service();
        //reussite
        System.out.println("***********************************************************");
        System.out.println("Test 1");
        Eleve eleve = ser.authentifierEleve("chloe@gmail.com", "coucou");
        if(eleve!=null){
            System.out.println("Authentification réussie ! ");
            System.out.println("Le profil de l'eleve est : ");
            System.out.println("Nom : "+eleve.getNom()+" Prenom : "+eleve.getPrenom()+" Classe : "+tableauClasses[eleve.getClasse()]+" Etablissement : "+eleve.getEtablissement().getNom());
        }else{
            System.out.println("L'authentification a échoué ! Veuillez recommencer \n");
        }
        //echec
        System.out.println("***********************************************************");
        System.out.println("Test 2");
        Eleve eleve1 = ser.authentifierEleve("chloe@gmail.com", "coucou2");
        if(eleve1!=null){
            System.out.println("Authentification réussie ! ");
            System.out.println("Le profil de l'eleve est : ");
            System.out.println("Nom : "+eleve1.getNom()+" Prenom : "+eleve1.getPrenom()+" Classe : "+tableauClasses[eleve1.getClasse()]+" Etablissement : "+eleve1.getEtablissement().getNom());
        }else{
            System.out.println("L'authentification a échoué ! Veuillez recommencer \n");
        }
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
    public static void testerAuthentificationIntervenant(){
        System.out.println("TEST ATHENTIFICATION INTERVENANT ");
        Service ser = new Service();
        //reussite
        System.out.println("***********************************************************");
        System.out.println("Test 1");
        Intervenant intervenant = ser.authentifierIntervenant("cchabal", "coucou");
        if(intervenant!=null){
            System.out.println("Authentification réussie ! ");
            System.out.println("Le profil de l'intervenant est : ");
            System.out.println("Nom : "+intervenant.getNom()+" Prenom : "+intervenant.getPrenom()+" Telephone : "+intervenant.getTelephone()+" Nombre demandes : "+intervenant.getNbDemandes()+" Niveau inf : "+tableauClasses[intervenant.getNiveauInf()]+" Niveau sup : "+tableauClasses[intervenant.getNiveauSup()]+" Statut : "+intervenant.isStatut());
        }else{
            System.out.println("L'authentification a échoué ! Veuillez recommencer \n");
        }
        
        //echec
        System.out.println("***********************************************************");
        System.out.println("Test 2");
        Intervenant intervenant2 = ser.authentifierIntervenant("cchabal", "coucou2");
        
        if(intervenant2!=null){
            System.out.println("Le profil de l'intervenant est : ");
            System.out.println("Nom : "+intervenant2.getNom()+" Prenom : "+intervenant2.getPrenom()+" Telephone : "+intervenant2.getTelephone()+" Nombre demandes : "+intervenant2.getNbDemandes()+" Niveau inf : "+tableauClasses[intervenant2.getNiveauInf()]+" Niveau sup : "+tableauClasses[intervenant2.getNiveauSup()]+" Statut"+intervenant2.isStatut());
        }else{
            System.out.println("L'authentification a échoué ! Veuillez recommencer \n");
        }
        System.out.println("FIN DE TEST \n");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
     
    public static void creerBD(){
        System.out.println("TEST CREATION BD ");
        Service ser = new Service();
        boolean BDMatiere = ser.creerBDMatiere();
        boolean BDIntervenant = ser.creerBDIntervenant();
        if(BDMatiere==true){
            System.out.println("La base Matière a été créée");
        }else{
            System.out.println("La base Matiere n'a pas pu être créée");
        }
        if(BDIntervenant==true){
            System.out.println("La base Intervenant a été créée");
        }else{
            System.out.println("La base Intervenant n'a pas pu être créée");
        }
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
    public static void testerCreationDemande(){
        System.out.println("TEST CREATION DEMANDE ");
        Service ser = new Service();
        //demande numero 1
        //authentification
        System.out.println("****Demande 1*****");
        System.out.println("***********************************************************");
        Eleve eleve = ser.authentifierEleve("widad@gmail.com", "coucou2");
        System.out.println("Authentification réussie ! ");
        System.out.println("Eleve authentifié : ");
        System.out.println("Nom : "+eleve.getNom()+" Prenom : "+eleve.getPrenom()+" Classe : "+tableauClasses[eleve.getClasse()]+" Etablissement : "+eleve.getEtablissement().getNom());
        //menu deroulant
        List<Matiere> listeMatiere = ser.genererListeMatieres();
        System.out.println("Liste des matières disponibles :");
        for(Matiere m :listeMatiere){
            System.out.println(m.getNom());
        }
        //ecrire description
        String description = "J'aimerais finir mon devoir d'informatique.";
        Matiere matiere = ser.trouverMatiereParId(listeMatiere.get(0).getId());
        
        boolean resultat = (ser.creerDemande(matiere,description,eleve));
        System.out.println("Le resultat de la fonction est : "+resultat);
        if(resultat == true){
            System.out.println("Un intervenant a été trouvé");
        }else{
            System.out.println("Aucun intervenant n'a été trouvé");
        }
        
        //demande numero 2
        System.out.println("****Demande 2*****");
        //authentification
        System.out.println("***********************************************************");
        Eleve eleve1 = ser.authentifierEleve("chloe@gmail.com", "coucou");
        System.out.println("Authentification réussie ! ");
        System.out.println("Eleve authentifié : ");
        System.out.println("Nom : "+eleve1.getNom()+" Prenom : "+eleve1.getPrenom()+" Classe : "+tableauClasses[eleve1.getClasse()]+" Etablissement : "+eleve1.getEtablissement().getNom());
        //menu deroulant
        System.out.println("Liste des matières disponibles :");
        for(Matiere m :listeMatiere){
            System.out.println(m.getNom());
        }
        //ecrire description
        String description1 = "J'aimerais finir mon devoir d'informatique.";
        Matiere matiere1 = ser.trouverMatiereParId(listeMatiere.get(0).getId());
        
        boolean resultat1 = (ser.creerDemande(matiere1,description1,eleve1));
        System.out.println("Le resultat de la fonction est : "+resultat1);
        if(resultat1 == true){
            System.out.println("Un intervenant a été trouvé");
        }else{
            System.out.println("Aucun intervenant n'a été trouvé");
        }
        
        //demande 3
        System.out.println("****Demande 3*****");
        System.out.println("***********************************************************");
        Eleve eleve3 = ser.authentifierEleve("theo@gmail.com", "coucou3");
        System.out.println("Authentification réussie ! ");
        System.out.println("Eleve authentifié : ");
        System.out.println("Nom : "+eleve3.getNom()+" Prenom : "+eleve3.getPrenom()+" Classe : "+tableauClasses[eleve3.getClasse()]+" Etablissement : "+eleve3.getEtablissement().getNom());
        //menu deroulant
        System.out.println("Liste des matières disponibles :");
        for(Matiere m :listeMatiere){
            System.out.println(m.getNom());
        }
        //ecrire description
        String description3 = "J'ai un controle demain et j'aimerais appronfondir le chapitre.";
        Matiere matiere3 = ser.trouverMatiereParId(listeMatiere.get(2).getId());
        
        boolean resultat3 = (ser.creerDemande(matiere3,description3,eleve3));
        System.out.println("Le resultat de la fonction est : "+resultat3);
        if(resultat3 == true){
            System.out.println("Un intervenant a été trouvé");
        }else{
            System.out.println("Aucun intervenant n'a été trouvé");
        }
        
        //demande 4
        System.out.println("****Demande 4*****");
        System.out.println("***********************************************************");
        Eleve eleve4 = ser.authentifierEleve("ludivine@gmail.com", "coucou4");
        System.out.println("Authentification réussie ! ");
        System.out.println("Eleve authentifié : ");
        System.out.println("Nom : "+eleve4.getNom()+" Prenom : "+eleve4.getPrenom()+" Classe : "+tableauClasses[eleve4.getClasse()]+" Etablissement : "+eleve4.getEtablissement().getNom());
        //menu deroulant
        System.out.println("Liste des matières disponibles :");
        for(Matiere m :listeMatiere){
            System.out.println(m.getNom());
        }
        //ecrire description
        String description4 = "J'ai un DM à faire pour demain";
        Matiere matiere4 = ser.trouverMatiereParId(listeMatiere.get(1).getId());
        
        boolean resultat4 = (ser.creerDemande(matiere4,description4,eleve4));
        System.out.println("Le resultat de la fonction est : "+resultat4);
        if(resultat4 == true){
            System.out.println("Un intervenant a été trouvé");
        }else{
            System.out.println("Aucun intervenant n'a été trouvé");
        }
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
    public static void testerGestionDemandeIntervenant(){
        System.out.println("TEST GESTION DEMANDE INTERVENANT ");
        Service ser = new Service();
        //demande numero 1
        //authentification
        System.out.println("***********************************************************");
        Intervenant intervenant = ser.authentifierIntervenant("vhernendez", "coucou5");
        System.out.println("Authentification réussie ! ");
        System.out.println("Intervenant authentifié : Nom : "+intervenant.getNom()+" Prenom : "+intervenant.getPrenom()+" Telephone : "+intervenant.getTelephone()+" Nombre demandes : "+intervenant.getNbDemandes()+" Niveau inf : "+tableauClasses[intervenant.getNiveauInf()]+" Niveau sup : "+tableauClasses[intervenant.getNiveauSup()]+" Statut : "+intervenant.isStatut());
        
        //consultation demande
        System.out.println("***********************************************************");
        Demande demande = ser.consulterDemandeEnCoursIntervenant(intervenant);
        Eleve eleve = demande.getEleve();
        DateFormat formatH = new SimpleDateFormat("yyyy");
        Date date = new Date();
        Integer age = Integer.parseInt(formatH.format(eleve.getDateNaissance()));
        Integer annee = Integer.parseInt(formatH.format(date));
        age = annee - age;
        
        System.out.println("Demande en cours : Matiere : "+demande.getMatiere().getNom()+" Description : "+demande.getDescription()+" Etat : "+demande.isPriseEnCharge());
        System.out.println("Le profil de l'eleve est : ");
        System.out.println("Nom : "+eleve.getNom()+" Prenom : "+eleve.getPrenom()+" Classe : "+tableauClasses[eleve.getClasse()]+" Age : "+age+" Etablissement : "+eleve.getEtablissement().getNom());
        
        ser.lancerVisioIntervenant(demande);
        boolean visio = ser.consulterDemandeEnCoursEleve(eleve).isEtatVisio();
                
        if(visio==true){
            System.out.println("L'intervenant a lancé la visio ! ");
        }else{
            System.out.println("L'intervenant n'a pas encore lancé la visio ! ");
        }
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
    public static void testerFinVisio(){
        System.out.println("TEST FIN VISIO ");
        Service ser = new Service();
        System.out.println("***********************************************************");
        Intervenant intervenant = ser.authentifierIntervenant("vhernendez", "coucou5");        
        Demande demande = ser.consulterDemandeEnCoursIntervenant(intervenant);
        System.out.println("Authentification réussie ! ");
        System.out.println("Intervenant authentifié : Nom : "+intervenant.getNom()+" Prenom : "+intervenant.getPrenom()+" Telephone : "+intervenant.getTelephone()+" Nombre demandes : "+intervenant.getNbDemandes()+" Niveau inf : "+tableauClasses[intervenant.getNiveauInf()]+" Niveau sup : "+tableauClasses[intervenant.getNiveauSup()]+" Statut : "+intervenant.isStatut());
        //consultation demande
        System.out.println("Demande en cours : Matiere : "+demande.getMatiere().getNom()+" Description : "+demande.getDescription()+" Etat de la prise en charge : "+demande.isPriseEnCharge()+" Etat de la visio : "+demande.isEtatVisio());
        
        boolean finVisio = ser.arreterVisio(demande);
        demande = ser.trouverDemandeParId(demande.getId());
        intervenant = ser.trouverIntervenantParId(intervenant.getId());
        System.out.println("***********************************************************");
        System.out.println("Le resultat du service arretVisio est : "+finVisio);
        if(finVisio==true){
            System.out.println("Visio finie\nMise à jour des attributs");
            System.out.println("Demande en cours : Matiere : "+demande.getMatiere().getNom()+" Description : "+demande.getDescription()+" Etat de la prise en charge : "+demande.isPriseEnCharge()+" Etat de la visio : "+demande.isEtatVisio()+"  Note : "+demande.getNote());
            System.out.println("Intervenant authentifié  : Nom : "+intervenant.getNom()+" Prenom : "+intervenant.getPrenom()+" Telephone : "+intervenant.getTelephone()+" Nombre de demandes : "+intervenant.getNbDemandes()+" Niveau inf : "+tableauClasses[intervenant.getNiveauInf()]+" Niveau sup : "+tableauClasses[intervenant.getNiveauSup()]+" Statut : "+intervenant.isStatut());
        
        }else{
            System.out.println("Visio pas finie");
        }
        
        boolean eval = ser.evaluerDemande(demande,4);        
        demande = ser.trouverDemandeParId(demande.getId());
        
        System.out.println("***********************************************************");
        System.out.println("Le resultat du service evaluerVisio est : "+finVisio);
        if(eval==true){
            System.out.println("Mise à jour de la note");
            System.out.println("Demande en cours : Matiere : "+demande.getMatiere().getNom()+" Description : "+demande.getDescription()+"  Note : "+demande.getNote()+" Etat de la prise en charge: "+demande.isPriseEnCharge());
        }else{
            System.out.println("Demande non evaluée");
        }
        System.out.println("FIN DE TEST \n");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
    public static void testerConsultationDemandeEleve(){
        System.out.println("TEST CONSULTATION HISTORIQUE ELEVE ");
        Service ser = new Service();
        System.out.println("***********************************************************");
        Eleve eleve = ser.authentifierEleve("widad@gmail.com", "coucou2");
        List<Demande> liste = ser.genererHistoriqueDemandesEleve(eleve);
        System.out.println("Authentification réussie ! ");
        System.out.println("Le profil de l'eleve est : ");
        System.out.println("Eleve authentifié : Nom: "+eleve.getNom()+",Prenom: "+eleve.getPrenom()+",Classe: "+tableauClasses[eleve.getClasse()]+", Etablissement: "+eleve.getEtablissement().getNom());
        System.out.println("Liste des demandes :");
        for(Demande demande : liste){
            System.out.println("Matiere :"+demande.getMatiere().getNom()+" Description : "+demande.getDescription()+" Note :"+demande.getNote()+"Date :"+demande.getJourDemande());
        }
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
        
    }
    
    public static void testerConsultationInterventionsIntervenant(){
        System.out.println("TEST CONSULTATION HISTORIQUE INTERVENANT ");
        Service ser = new Service();
        System.out.println("***********************************************************");
        Intervenant intervenant = ser.authentifierIntervenant("vhernendez", "coucou5");
        List<Demande> liste = ser.genererHistoriqueDemandesIntervenant(intervenant);
        System.out.println("Authentification réussie ! ");
        System.out.println("Intervenant authentifié  : Nom : "+intervenant.getNom()+" Prenom : "+intervenant.getPrenom()+" Telephone : "+intervenant.getTelephone()+" Nombre de demandes : "+intervenant.getNbDemandes()+" Niveau inf : "+tableauClasses[intervenant.getNiveauInf()]+" Niveau sup : "+tableauClasses[intervenant.getNiveauSup()]+" Statut : "+intervenant.isStatut());
        System.out.println("Liste des demandes :");
        for(Demande demande : liste){
            System.out.println("Matiere : "+demande.getMatiere().getNom()+" Description : "+demande.getDescription()+" Note : "+demande.getNote()+" Date : "+demande.getJourDemande());
        }
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");   
    }
    
    public static void testerStatistiques(){
        System.out.println("TEST CONSULTATION STATISTIQUES ");
        Service ser = new Service();
        System.out.println("***********************************************************");
        int nbDemandes = ser.genererNombreDemandesTotales();
        System.out.println("Statistique : Nombre de demandes totales : "+nbDemandes);
        
        System.out.println("***********************************************************");
        List<Object[]> liste = ser.genererStatistiqueDemandeParCodeIPS();
        System.out.println("Statistique : Nombre de demandes en fonction du code IPS de l'établissement ");

        for(int i=0; i<liste.size(); i++){
            System.out.println("Nombre demandes : "+liste.get(i)[0]+" - Code IPS de l'Etablissement : "+liste.get(i)[1]);
        }
        System.out.println("***********************************************************");
        List<Object[]> liste1 = ser.genererStatistiqueDemandeParClasse();        
        System.out.println("Statistique : Nombre de demandes en fonction de la classe ");

        for(int i=0; i<liste1.size(); i++){
            Integer classe = (Integer) liste1.get(i)[1];
            System.out.println("Nombre demandes : "+liste1.get(i)[0]+" - Classe : "+tableauClasses[classe]);
        }
        System.out.println("***********************************************************");
        List<Object[]> liste2 = ser.genererStatistiqueDemandeParMatiere();
        System.out.println("Statistique : Nombre de demandes en fonction de la matière ");

        for(int i=0; i<liste2.size(); i++){
            Matiere mat = (Matiere) liste2.get(i)[1];
            System.out.println("Nombre demandes : "+liste2.get(i)[0]+" - Matiere : "+mat.getNom());
        }
        
        System.out.println("***********************************************************");
        List<Object[]> liste3 = ser.genererStatistiqueDemandeParDepartement();
        System.out.println("Statistique : Nombre de demandes en fonction du code du departement de l'établissement ");

        for(int i=0; i<liste3.size(); i++){
            System.out.println("Nombre demandes : "+liste3.get(i)[0]+" - Code département de l'Etablissement : "+liste3.get(i)[1]);
        }
        
        System.out.println("FIN DE TEST");
        System.out.println("---------------------------------------------------------------------------------------------\n");      
   
    }
    
    

    
    
}
