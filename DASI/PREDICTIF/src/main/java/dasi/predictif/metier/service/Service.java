/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.metier.service;

import dasi.predictif.dao.ClientDao;
import dasi.predictif.dao.ConsultationDao;
import dasi.predictif.dao.EmployeDao;
import dasi.predictif.dao.JpaUtil;
import dasi.predictif.dao.MediumDao;
import dasi.predictif.metier.modele.Astrologue;
import dasi.predictif.metier.modele.Cartomancien;
import dasi.predictif.metier.modele.Client;
import dasi.predictif.metier.modele.Consultation;
import dasi.predictif.metier.modele.Employe;
import dasi.predictif.metier.modele.Medium;
import dasi.predictif.metier.modele.Personne;
import dasi.predictif.metier.modele.Spirite;
import dasi.predictif.util.AstroNetApi;
import dasi.predictif.util.Message;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author smaillard
 * 
 * Cette classe gère tous les Services de notre application. 
 * 
 * Elle possède les attributs clientDao, employeDao, mediumDao et consultationDao
 * qui lui permettent d'intéragir avec la BDD après ouverture de l'EntityManager 
 * puis fermeture.
 * 
 */
public class Service {
    private final ClientDao clientDao;
    private final EmployeDao employeDao;
    private final MediumDao mediumDao;
    private final ConsultationDao consultationDao;
    
    public Service() {
        clientDao = new ClientDao();
        employeDao = new EmployeDao();
        mediumDao = new MediumDao();
        consultationDao = new ConsultationDao();
    }
    
    
    // SERVICES CLIENT
    /**
     * Cette méthode permet d'initialiser un premier client automatiquement dans
     * la BDD pour les phases de tests de l'application.
     */
    public void initialiserClient() {
        JpaUtil.createEntityManager();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Client client = new Client("John", "Doe", "jd", "00 00 00 00 00", "INSA", dateFormat.parse("10/08/2001"), "a");
            JpaUtil.beginTransaction();
            clientDao.create(client);
            JpaUtil.commitTransaction();
        }
        catch(Exception e) {
            JpaUtil.rollbackTransaction();
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
    }
    
    /**
     * Cette classe permet l'inscription d'un nouveau Client dans la BDD saisie 
     * par l'utilisateur à condition qu'il respecte le format pour la date de 
     * naissance et que son adresse email ne soit pas déjà utilisée.
     * Puis elle envoie un mail de validation ou d'échec selon le résultat de 
     * l'inscription.
     * 
     * @param prenom
     * @param nom
     * @param email
     * @param tel
     * @param adresse
     * @param dateNaissance
     * @param motDePasse
     * @return Client
     */
    public Client inscription(String prenom, String nom, String email, String tel, String adresse, String dateNaissance, String motDePasse) {
        JpaUtil.createEntityManager();

        Client client = null;
        
        try {
            JpaUtil.beginTransaction();
            
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            client = new Client(prenom, nom, email, tel, adresse, dateFormat.parse(dateNaissance), motDePasse);

            clientDao.create(client);
            
            JpaUtil.commitTransaction();
            
            Message.envoyerMail("contact@predict.if", email, "Bienvenue chez PREDICT(IF", "Bonjour " + client.getPrenom() + ", nous vous confirmons votre inscription au service PREDICT'IF. Rendez-vous vite sur notre site pour consulter votre profil astrologique et profiter des dons incroyables de nos mediums.");
        }
        catch (Exception e) {
            JpaUtil.rollbackTransaction();
            client = null;
            Message.envoyerMail("contact@predict.if", email, "Echec de l'inscription de PREDICT'IF", "Bonjour " + client.getPrenom() + ", votre inscription au service PREDICT'IF a malencontreusement échoué... Merci de recommencer ultérieurement.");
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return client;
    }
    
    /**
     * Cette méthode permet la connexion d'un Personne (Client ou Employe) sur
     * l'application.
     * 
     * @param mail
     * @param motDePasse
     * @return Personne
     */
    public Personne connexion(String mail, String motDePasse){
        JpaUtil.createEntityManager();
        Personne utilisateur = null;
        
        try {
            Employe employe = employeDao.findByEmail(mail);
            if (employe != null && employe.getMotDePasse().equals(motDePasse))
                utilisateur = employe;
        }
        catch (Exception e) {
            utilisateur = null;
        }
        
        if (utilisateur == null) {
            try {
                Client client = clientDao.findByEmail(mail);
                if (client != null && client.getMotDePasse().equals(motDePasse))
                    utilisateur = client;
            }
            catch (Exception e) {
                utilisateur = null;
            }
        }
        
        JpaUtil.closeEntityManager();
        
        return utilisateur;
    }
    
    
    // SERVICES EMPLOYES
    /**
     * Cette méthode permet l'initialisation des Employes sur la BDD à chaque
     * lancement de l'application.
     */
    public void initialiserEmployes() {
        List<Employe> employes = new ArrayList<>();
        employes.add(new Employe("Mathieu", "Marechal", "mathieu.marechal@gmail.com", "06 89 77 59 58", "password", "H"));
        employes.add(new Employe("Gautier", "Pelletier", "gautier.pelletier@gmail.com", "06 83 31 51 38", "password", "H"));
        employes.add(new Employe("Lucille", "Bouche", "lucille.bouche@gmail.com", "06 38 43 91 79", "password", "F"));
        employes.add(new Employe("Emma", "Gaudin", "emma.gaudin@gmail.com", "06 13 38 33 24", "password", "F"));
        employes.add(new Employe("Louis", "Rose", "louis.rose@gmail.com", "06 51 27 91 93", "password", "H"));
        employes.add(new Employe("Caroline", "Armand", "caroline.armand@gmail.com", "06 69 31 49 33", "password", "F"));

        employes.forEach((e) -> ajouterEmploye(e));
    }
    
    /**
     * Cette méthode permet l'ajout d'un employe sur la BDD à condition que l'email
     * utilisé ne soit pas déjà utilisé.
     * 
     * @param employe
     * @return 
     */
    private Employe ajouterEmploye(Employe employe) {
        JpaUtil.createEntityManager();
        
        try {
            JpaUtil.beginTransaction();
            
            employeDao.create(employe);
            
            JpaUtil.commitTransaction();
        }
        catch (Exception e) {
            JpaUtil.rollbackTransaction();
            employe = null;
            e.printStackTrace(System.err);
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return employe;
    }
    
    /**
     * Cette méthode permet d'obtenir une prédiction sur le client de la consultation 
     * en cours en fournissant ses niveaux d'amour, sante et travail sur une 
     * échelle de 1 à 4.
     * 
     * @param client
     * @param amour
     * @param sante
     * @param travail
     * @return
     * @throws IOException 
     */
    public List<String> obtenirPredictions(Client client, int amour, int sante, int travail) throws IOException {
        AstroNetApi astroApi1 = new AstroNetApi();
        List<String> predictions = astroApi1.getPredictions(client.getCouleur(), client.getAnimal(), amour, sante, travail);
        return predictions;
    }
    
    // SERVICE MEDIUM
    /**
     * Cette méthode permet l'initialisation des mediums dans la BDD à chaque
     * lancement de l'application.
     */
    public void initialiserMediums() {
        List<Medium> mediums = new ArrayList<>();
        
        mediums.add(new Cartomancien("Endora", "F", "Mes cartes répondront à toutes vos questions personnelles."));
        mediums.add(new Spirite("Gwenaëlle", "F", "Boule de cristal", "Spécialiste des grandes conversations au-delà de TOUTES les frontières."));
        mediums.add(new Cartomancien("Mme Irma", "F", "Comprenez votre entourage grâce à mes cartes ! Résultats rapides."));
        mediums.add(new Astrologue("Mr M", "H", "Institut des Nouveaux Savoirs Astrologiques", "2010", "Avenir, avenir, que nous réserves-tu ? N'attendez plus, demandez à me consulter !"));
        mediums.add(new Spirite("Professeur Tran", "H", "Marc de café, boule de cristal, oreilles de lapin", "Votre avenir est devant vous : regardons-le ensemble !"));
        mediums.add(new Astrologue("Serena", "F", "École Normale Supérieure d'Astrologie (ENS-Astro)", "2006", "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé."));
        
        mediums.forEach((m) -> ajouterMedium(m));
    }
    
    /**
     * Cette méthode permet l'ajout d'un medium sur la BDD.
     * 
     * @param medium
     * @return 
     */
    private Medium ajouterMedium(Medium medium) {
        JpaUtil.createEntityManager();
        try {
            JpaUtil.beginTransaction();
            
            mediumDao.create(medium);
            
            JpaUtil.commitTransaction();
        }
        catch(Exception e) {
            JpaUtil.rollbackTransaction();
            medium = null;
            e.printStackTrace(System.err);
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return medium;
    }
    
    /**
     * Cette méthode permet d'obtenir la liste des mediums inscrits sur la BDD.
     * 
     * @return 
     */
    public List<Medium> obtenirListeMediums() {
        JpaUtil.createEntityManager();
        List<Medium> mediums = mediumDao.findAll();
        JpaUtil.closeEntityManager();
        
        return mediums;
    }
    
    // SERVICE CONSULTATION
    /**
     * Cette méthode permet à un client de demander une consultation avec le medium
     * choisi à condition qu'un employé du bon genre soit disponible pour la consultation.
     * Puis la méthode définie l'employé sélectionné comme indisponible.
     * De plus, parmi les employés disponible, elle en choisit un aléatoirement,
     * puis envoie un mail de validation ou d'échec à cet employé pour l'informer 
     * qu'il a une consultation en attente.
     * 
     * @param client
     * @param mediumId
     * @return 
     */
    public Consultation demanderConsultation(Client client, long mediumId) {
        JpaUtil.createEntityManager();
        
        Medium medium = mediumDao.findById(mediumId);
        
        List<Employe> employesDispo = employeDao.findEmployesDispo(medium.getGenre());
        
        Consultation consultation = null;
        
        boolean dejaEnAttente = false;
        
        List<Consultation> consultations = client.getConsultations();
        for(Consultation c : consultations){
            if(c.getMedium().getId() == mediumId)
                dejaEnAttente = true;
        }
        
        if (!employesDispo.isEmpty() && !dejaEnAttente) {
            Collections.shuffle(employesDispo);
            
            Employe employe = null;
            int nbConsultationMin = 0;
            
            for(Employe e : employesDispo){
                int nbConsultation = 0;
                for(Consultation c : e.getConsultations()){
                    if (c.getEtat() == Consultation.ETAT_FINI)
                        nbConsultation++;
                }
                
                if (employe == null || nbConsultation < nbConsultationMin) {
                    employe = e;
                    nbConsultationMin = nbConsultation;
                }
            }

            
            try {
                JpaUtil.beginTransaction();
                Calendar date = Calendar.getInstance();
                consultation = new Consultation(date.getTime(), client, medium, employe);
                consultationDao.create(consultation);
                
                client.addConsultation(consultation);
                employe.addConsultation(consultation);
                medium.addConsultation(consultation);
                employe.setDispo(false);
                employeDao.update(employe);
                clientDao.update(client);
                mediumDao.update(medium);
                
                JpaUtil.commitTransaction();
                
                Message.envoyerNotification(employe.getTel(), "Bonjour " + employe.getPrenom() + ". Consultation requise pour " + client.getPrenom() + " " + client.getNom().toUpperCase() + ". Médium à incarner : " + medium.getSurnom());
            }
            catch (Exception e) {
                JpaUtil.rollbackTransaction();
                consultation = null;
            }
            finally {
                JpaUtil.closeEntityManager();
            }
        }
        return consultation;
    }
    
    /**
     * Cette méthode permet à un employé ayant une consultation en attente de la 
     * passé dans l'état en cours pour signifier au client qu'il peut appeler 
     * le medium joué par l'employé a tout moment. 
     * Le client en est informé par l'envoi d'un mail.
     * Un employé ne peut pas refuser une consultation.
     * 
     * @param employe
     * @return 
     */
    public Consultation accepterConsultation(Employe employe) {
        JpaUtil.createEntityManager();
        Consultation consultation = consultationDao.findConsultationAttente(employe);
        if (consultation != null) {
            try {
                JpaUtil.beginTransaction();
                
                
                Calendar date = Calendar.getInstance();
                consultation.setEtat(Consultation.ETAT_EN_COURS);
                consultation.setDateDebutConsult(date.getTime());
                
                consultationDao.update(consultation);
                
                JpaUtil.commitTransaction();
                
                Client client = consultation.getClient();
                Medium medium = consultation.getMedium();
                SimpleDateFormat dateFormatJour = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat dateFormatHeure = new SimpleDateFormat("hh'h'mm");
                String jourDemande = dateFormatJour.format(consultation.getDateDemande());
                String heureDemande = dateFormatHeure.format(consultation.getDateDemande());
                
                Message.envoyerNotification(consultation.getClient().getTel(), "Bonjour " + client.getPrenom() + ". J'ai bien reçu votre demande de consultation du " + jourDemande + " à " + heureDemande + ". Vous pouvez dès à présent me contacter au " + employe.getTel() + ". A tout de suite ! Médiumiquement vôtre, " + medium.getSurnom());
            }
            catch (Exception e) {
                JpaUtil.rollbackTransaction();
                consultation = null;
                e.printStackTrace(System.err);
            }
            finally {
                JpaUtil.closeEntityManager();
            }
        }

        return consultation;
    }
    
    /**
     * Cette méthode permet d'obtenir le nombre de consultations réalisées par 
     * chaque médium.
     * 
     * @return 
     */
    public Map<String, Integer> obtenirConsultationsParMedium() {
        JpaUtil.createEntityManager();
        Map<String, Integer> consultationsParMedium = new HashMap<>();
        try {
            List<Medium> mediums = mediumDao.findAll();
            for (Medium medium : mediums) {
                consultationsParMedium.put(medium.getSurnom(), medium.getConsultations().size());
            }
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return consultationsParMedium;
    }
    
    /**
     * Cette méthode permet d'obtenir le classement des médiums ayant réalisé
     * le plus de consultations.
     * 
     * @return 
     */
    public Map<Integer, List<String>> obtenirClassementMedium() {
        JpaUtil.createEntityManager();
        int nombreConsultations = 0;
        Map<Integer, List<String>> classementMediums = new TreeMap<>();
        try {
            List<Medium> mediums = mediumDao.findAll();
            for (Medium medium : mediums) {
                nombreConsultations += medium.getConsultations().size();
            }
            
            if (nombreConsultations > 0) {
                for (Medium medium : mediums) {
                    if (medium.getConsultations().isEmpty()) continue;
                    Integer key = medium.getConsultations().size()*100/nombreConsultations;
                    if (classementMediums.containsKey(key))
                        classementMediums.get(key).add(medium.getSurnom());
                    else {
                        List<String> value = new ArrayList<>();
                        value.add(medium.getSurnom());
                        classementMediums.put(key, value);
                    }
                    
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return classementMediums;
    }
    
    /**
     * Cette méthode permet de réaliser le classement des employés ayant réalisé
     * le plus de consultations.
     * 
     * @return 
     */
    public Map<Integer, List<String>> obtenirClassementEmployes() {
        JpaUtil.createEntityManager();
        int nombreConsultations = 0;
        Map<Integer, List<String>> classementEmployes = new TreeMap<>();
        try {
            List<Employe> employes = employeDao.findAll();
            for (Employe medium : employes) {
                nombreConsultations += medium.getConsultations().size();
            }
            
            if (nombreConsultations > 0) {
                for (Employe employe : employes) {
                    if (employe.getConsultations().isEmpty()) continue;
                    Integer key = employe.getConsultations().size()*100/nombreConsultations;
                    if (classementEmployes.containsKey(key))
                        classementEmployes.get(key).add(employe.getPrenom() + " " + employe.getNom().toUpperCase());
                    else {
                        List<String> value = new ArrayList<>();
                        value.add(employe.getPrenom() + " " + employe.getNom().toUpperCase());
                        classementEmployes.put(key, value);
                    }
                    
                }
            }
            
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return classementEmployes;
    }
    
    /**
     * Cette méthode permet à un employé de terminer une consultation en cours et 
     * d'y laisse un commentaire qui ne sera pas visible par le client.
     * 
     * @param employe
     * @param commentaire
     * @return 
     */
    public Consultation terminerConsultation(Employe employe, String commentaire) {
        JpaUtil.createEntityManager();
        Consultation consultation = consultationDao.findConsultationCouranteEmploye(employe);
        
        if (consultation != null && consultation.getEtat() == Consultation.ETAT_EN_COURS) {
            try {
                JpaUtil.beginTransaction();
                
                Calendar date = Calendar.getInstance();
                consultation.setEtat(Consultation.ETAT_FINI);
                consultation.setDateFinConsult(date.getTime());
                consultation.setCommentaire(commentaire);
                employe.setDispo(true);
                
                consultationDao.update(consultation);
                employeDao.update(employe);
                
                JpaUtil.commitTransaction();
            }
            catch (Exception e) {
                JpaUtil.rollbackTransaction();
                consultation = null;
                e.printStackTrace(System.err);
            }
            finally {
                JpaUtil.closeEntityManager();
            }
        }

        return consultation;
    }
    
    /**
     * Cette méthode permet à un employe d'obtenir les informations sur sa 
     * consultation courante.
     * 
     * @param client
     * @return 
     */
    public Consultation obtenirConsultationCouranteClient(Client client) {
        JpaUtil.createEntityManager();
        Consultation consultation = null;
        try {
            consultation = consultationDao.findConsultationCouranteClient(client);
        }
        catch (Exception e) {
            consultation = null;
            e.printStackTrace(System.err);
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return consultation;
    }
    
    /**
     * Cette méthode permet d'obtenir les informations sur la consultation courante 
     * de n'importe quel employe.
     * 
     * @param employe
     * @return 
     */
    public Consultation obtenirConsultationCouranteEmploye(Employe employe) {
        JpaUtil.createEntityManager();
        Consultation consultation = null;
        try {
            consultation = consultationDao.findConsultationCouranteEmploye(employe);
        }
        catch (Exception e) {
            consultation = null;
            e.printStackTrace(System.err);
        }
        finally {
            JpaUtil.closeEntityManager();
        }
        
        return consultation;
    }
}
