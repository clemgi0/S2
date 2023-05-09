/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dasi.predictif.vue;

import dasi.predictif.dao.JpaUtil;
import dasi.predictif.metier.modele.Astrologue;
import dasi.predictif.metier.modele.Client;
import dasi.predictif.metier.modele.Consultation;
import dasi.predictif.metier.modele.Employe;
import dasi.predictif.metier.modele.Medium;
import dasi.predictif.metier.modele.Personne;
import dasi.predictif.metier.modele.Spirite;
import dasi.predictif.metier.service.Service;
import dasi.predictif.util.Saisie;
import java.io.IOException;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @author smaillard
 * 
 * Cette classe est la classe Main et permet d'imiter l'IHM grâce à des 
 * interactions avec la console.
 * 
 */

public class Main {
    
    
    private static final Service service = new Service();
    private static Personne utilisateurConnecte = null;

    
    public static void main(String[] args) throws IOException {
        JpaUtil.disableLog();
        JpaUtil.createEntityManagerFactory();
        
        service.initialiserClient();
        
        service.initialiserMediums();
        service.initialiserEmployes();
        
        System.out.println("BIENVENUE SUR NOTRE SITE PREDICT'IF !!");
        
        boolean quit = false;
        while (!quit) {
            Map<Integer, String> actions = new HashMap<>();
            if (utilisateurConnecte == null) {
                actions.put(1, "Inscription");
                actions.put(2, "Connexion");
            }
            else {
                System.out.println("\nBonjour " + utilisateurConnecte.getPrenom() + " !");
                if (utilisateurConnecte instanceof Client) {
                    actions.put(4, "Accéder à votre profil");
                    actions.put(5, "Accéder à votre historique des consultations");
                    actions.put(6, "Consulter la liste des médiums");
                   
                    Consultation consultation = service.obtenirConsultationCouranteClient((Client) utilisateurConnecte);
                    if (consultation == null)
                        actions.put(7, "Demander une consultation");
                    else if (consultation.getEtat() == Consultation.ETAT_ATTENTE)
                        System.out.println("Vous avez une consultation en attente");
                    else if (consultation.getEtat() == Consultation.ETAT_EN_COURS)
                        System.out.println("Vous avez une consultation en cours");
                }
                else if (utilisateurConnecte instanceof Employe) {
                    Consultation consultation = service.obtenirConsultationCouranteEmploye((Employe) utilisateurConnecte);
                    if (consultation != null) {
                        actions.put(9, "Voir les informations sur la consultation");
                        if (consultation.getEtat() == Consultation.ETAT_ATTENTE)  {
                            System.out.println("Vous avez reçu une demande de consultation");
                            actions.put(10, "Valider la consultation");
                        }
                        else if (consultation.getEtat() == Consultation.ETAT_EN_COURS) {
                            System.out.println("Vous avez une consultation en cours");
                            actions.put(11, "Obtenir une prédiction");
                            actions.put(12, "Terminer la consultation");
                        }
                    }
                    else {
                        System.out.println("Vous n'avez aucune consultation en attente ni en cours");
                    }
                    actions.put(8, "Voir les statistiques");
                }
                
                actions.put(13, "Déconnexion");
            }
            actions.put(14, "Quitter l'application");
            
            System.out.println("\nQue souhaitez-vous faire ?");
            for (int i = 0; i < actions.size(); i++)
                System.out.println("\t" + (i+1) + " - " + actions.values().toArray()[i]);
            
            int choice = min(actions.size(), max(1, Saisie.lireInteger("Votre choix : ")));
            Integer actionKey = (Integer) actions.keySet().toArray()[choice-1];
            
            switch (actionKey) {
                case 1:
                    inscription();
                    break;
                case 2:
                    connexion();
                    break;
                case 4:
                    voirProfilClient();
                    break;
                case 5:
                    voirHistoriqueConsultationsClient();
                    break;
                case 6:
                    consulterListeMediums();
                    break;
                case 7:
                    demanderConsultation();
                    break;
                case 8:
                    obtenirStatistiques();
                    break;
                case 9:
                    voirInfosConsultation();
                    break;
                case 10:
                    accepterConsultation();
                    break;
                case 11:
                    obtenirPrediction();
                    break;
                case 12:
                    terminerConsultation();
                    break;
                case 13:
                    utilisateurConnecte = null;
                    System.out.println("Vous êtes déconnectés");
                    break;
                case 14:
                    System.out.println("Quitter l'application");
                    quit = true;
                    break;
            }
            
        }

        JpaUtil.closeEntityManagerFactory();
    }
    
    public static void inscription() {
        System.out.println("\n- INSCRIPTION -");
        String prenom = Saisie.lireChaine("Prénom : ");
        String nom = Saisie.lireChaine("Nom : ");
        String email = Saisie.lireChaine("Email : ");
        String tel = Saisie.lireChaine("Numéro de téléphone : ");
        String adresse = Saisie.lireChaine("Adresse : ");
        String dateNaissance = Saisie.lireChaine("Date de naissance (jj/mm/aaaa) : ");
        String motDePasse = Saisie.lireChaine("Mot de passe : ");
        
        if (service.inscription(prenom, nom, email, tel, adresse, dateNaissance, motDePasse) != null) {
            System.out.println("Inscription réussie ! Vous pouvez maintenant vous connecter");
        }
        else {
            System.out.println("L'inscription a échouée...");
        }
    }
    
    public static void connexion() {
        System.out.println("\n- CONNEXION -");
        String email = Saisie.lireChaine("Email : ");
        String motDePasse = Saisie.lireChaine("Mot de passe : ");
        
        utilisateurConnecte = service.connexion(email, motDePasse);
        if (utilisateurConnecte == null) {
            System.out.println("L'email ou le mot de passe est incorrect...");
        }
    }
    
    public static void voirProfilClient() {
        Client client = (Client) utilisateurConnecte;
        System.out.println("Découvrez votre PROFIL ASTRAL");
        System.out.println("SIGNE DU ZODIAQUE : " + client.getSigneZodiaque());
        System.out.println("SIGNE CHINOIS : " + client.getSigneChinois());
        System.out.println("ANIMAL TOTEM : " + client.getAnimal());
        System.out.println("COULEUR PORTE-BONHEUR : " + client.getCouleur());
    }
    
    public static void voirHistoriqueConsultationsClient() {
        Client client = (Client) utilisateurConnecte;
        System.out.println("\n- HISTORIQUE DES CONSULTATIONS -");
        List<Consultation> consultations = client.getConsultations();
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        int nbConsultations = 0;
        for (Consultation consultation : consultations) {
            if (consultation.getEtat() != Consultation.ETAT_FINI)
                return;
            
            long durationMillies = consultation.getDateFinConsult().getTime() - consultation.getDateDebutConsult().getTime();
            long durationMin = TimeUnit.MINUTES.convert(durationMillies, TimeUnit.MILLISECONDS);
            
            System.out.println("\nConsultation avec " + consultation.getMedium().getSurnom() + " (" + consultation.getMedium().getClass().getSimpleName() + ")");
            System.out.println("\tTerminée le : " + dateFormat.format(consultation.getDateFinConsult()));
            System.out.println("\tDurée : " + durationMin + " minutes");
            
            nbConsultations++;
        }
        
        if (nbConsultations == 0)
            System.out.println("Tu n'as pas encore consulté. Lance-toi !");
    }
    
    public static void consulterListeMediums(){
        System.out.println("\n- NOS MEDIUMS - ");
        List<Medium> mediums = service.obtenirListeMediums();
        mediums.forEach(medium -> {
            System.out.println("\n" + medium.getSurnom() + " (" + medium.getGenre() + ")");
            System.out.println("> ID : " + medium.getId());
            System.out.println("> TYPE : " + medium.getClass().getSimpleName());
            System.out.println("> PRÉSENTATION : " + medium.getPresentation());
            
            if (medium instanceof Spirite) {
                System.out.println("> SUPPORT : " + ((Spirite) medium).getSupport());
            }
            else if (medium instanceof Astrologue) {
                System.out.println("> FORMATION : " + ((Astrologue) medium).getFormation());
                System.out.println("> PROMOTION : " + ((Astrologue) medium).getPromotion());
            }
        });
    }
    
    public static void demanderConsultation() {
        Client client = (Client) utilisateurConnecte;
        System.out.println("\n- DEMANDE DE CONSULTATION -");
        int mediumId = Saisie.lireInteger("Quelle est l'identifiant du medium avec qui vous souhaitez consulter ?");
        
        if (service.demanderConsultation(client, mediumId) != null)
            System.out.println("Votre demande de consultation a été prise en compte ! Vous recevrez bientôt un message avec plus d'informations.");
        else
            System.out.println("Votre demande de consultation n'a pas pu aboutir...");
    }

    public static void accepterConsultation() {
        Employe employe = (Employe) utilisateurConnecte;
        if (service.accepterConsultation(employe) != null)
            System.out.println("Vous avez accepté la consultation");
        else 
            System.out.println("Une erreur est survenue, la consultation n'a pas été acceptée.");
    }

    public static void voirInfosConsultation() {
        Employe employe = (Employe) utilisateurConnecte;
        Consultation consultation = service.obtenirConsultationCouranteEmploye(employe);
        
        if (consultation == null) {
            System.out.println("Vous n'avez pas de consultation en cours");
        }
        else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Medium medium = consultation.getMedium();
            System.out.println("\n- CONSULTATION EN COURS -");
            System.out.println("\nVOUS INCARNEZ LE MEDIUM :");
            System.out.println("> " + medium.getSurnom());
            System.out.println("> TYPE : " + medium.getClass().getSimpleName());
            System.out.println("> PRÉSENTATION : " + medium.getPresentation());
            
            if (medium instanceof Spirite) {
                System.out.println("> SUPPORT : " + ((Spirite) medium).getSupport());
            }
            else if (medium instanceof Astrologue) {
                System.out.println("> FORMATION : " + ((Astrologue) medium).getFormation());
                System.out.println("> PROMOTION : " + ((Astrologue) medium).getPromotion());
            }
            
            Client client = consultation.getClient();
            System.out.println("\nPOUR LE CLIENT :");
            System.out.println("> " + client.getPrenom() + " " + client.getNom().toUpperCase());
            System.out.println("> DATE DE NAISSANCE : " + dateFormat.format(client.getDateNaissance()));
            System.out.println("> EMAIL : " + client.getEmail());
            System.out.println("> NUMÉRO DE TELEPHONE : " + client.getTel());
            System.out.println("> ADRESSE : " + client.getAdresse());
            System.out.println("> SIGNE DU ZODIAQUE : " + client.getSigneZodiaque());
            System.out.println("> SIGNE CHINOIS : " + client.getSigneChinois());
            System.out.println("> ANIMAL TOTEM : " + client.getAnimal());
            System.out.println("> COULEUR PORTE-BONHEUR : " + client.getCouleur());
            
            System.out.println("\nHISTORIQUE DU CLIENT :");
            List<Consultation> consultations = client.getConsultations();
            int nbConsultations = 0;
            for (Consultation c : consultations) {
                if (c.getEtat() != Consultation.ETAT_FINI)
                    return;

                long durationMillies = c.getDateFinConsult().getTime() - c.getDateDebutConsult().getTime();
                long durationMin = TimeUnit.MINUTES.convert(durationMillies, TimeUnit.MILLISECONDS);

                System.out.println("Consultation avec " + c.getMedium().getSurnom() + " (" + c.getMedium().getClass().getSimpleName() + ")");
                System.out.println("\tTerminée le : " + dateFormat.format(c.getDateFinConsult()));
                System.out.println("\tDurée : " + durationMin + " minutes");
                System.out.println("\tCommentaire : " + c.getCommentaire());

                nbConsultations++;
            }

            if (nbConsultations == 0)
                System.out.println("Aucune consultation");
        }
        
    }
    
    public static void obtenirPrediction() throws IOException {
        Employe employe = (Employe) utilisateurConnecte;
        Consultation consultation = service.obtenirConsultationCouranteEmploye(employe);
        
        System.out.println("\n- DEMANDE DE PREDICTION -");
        int amour = Saisie.lireInteger("Niveau en amour");
        int sante = Saisie.lireInteger("Niveau en santé");
        int travail = Saisie.lireInteger("Niveau au travail");
        List<String> predictions = service.obtenirPredictions(consultation.getClient(), amour, sante, travail);
        System.out.println("AMOUR : " + predictions.get(0));
        System.out.println("SANTÉ : " + predictions.get(1));
        System.out.println("TRAVAIL : " + predictions.get(2));
    }
    
    public static void terminerConsultation() {
        Employe employe = (Employe) utilisateurConnecte;
        System.out.println("\n- TERMINER LA CONSULTATION -");
        String commentaire = Saisie.lireChaine("Laisser un commentaire : ");
        if (service.terminerConsultation(employe, commentaire) != null)
            System.out.println("La consultation est terminée");
        else
            System.out.println("Une erreur s'est produite, la consultation n'a pas été terminée");
    }
    
    public static void obtenirStatistiques() {
        Map<String, Integer> consultationsParMedium = service.obtenirConsultationsParMedium();
        Map<Integer, List<String>> classementMediums = service.obtenirClassementMedium();
        Map<Integer, List<String>> classementEmployes = service.obtenirClassementEmployes();
        
        
        System.out.println("\n- STATISTIQUES -");
        System.out.println("\n- Nombre de consultations par medium -");
        consultationsParMedium.forEach((nomMedium, nombreConsultations) -> {
            System.out.println(nomMedium + " : " + nombreConsultations);
        });
        System.out.println("\n- Top 5 des mediums -");
        int nbMediums = 0;
        for (int i = 0; i<classementMediums.size(); i++) {
            int percentage = (int) classementMediums.keySet().toArray()[i];
            List<String> mediums = (List<String>) classementMediums.values().toArray()[i];
            for (int j = 0; j < mediums.size(); j++) {
                nbMediums++;
                System.out.println("# " + nbMediums + " - " + mediums.get(j) + " : " + percentage + "%");
                if (nbMediums == 5) break;
            }
            if (nbMediums == 5) break;
        }
        System.out.println("\n- Pourcentage des consultations par employés -");
        int nbEmploye = 0;
        for (int i = 0; i<classementEmployes.size(); i++) {
            int percentage = (int) classementEmployes.keySet().toArray()[i];
            List<String> employes = (List<String>) classementEmployes.values().toArray()[i];
            for (int j = 0; j < employes.size(); j++) {
                nbEmploye++;
                System.out.println("# " + nbEmploye + " - " + employes.get(j) + " : " + percentage + "%");
            }
        }
    }
}
