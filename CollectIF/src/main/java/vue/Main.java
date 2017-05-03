/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.*;

import java.util.List;

import metier.modele.*;
import java.util.Date;
import metier.service.ServiceMetier;
import util.Moment;

/**
 *
 * @author hmartin2
 */
public class Main {

    public static void main(String[] args) {
        JpaUtil.init();

        //System.out.println("Afficher Adherents");
        //afficherTousAdherents();
        //System.out.println("Ajouter Adherents");
        //inscrireAdherent();
        //afficherTousAdherents();
        //System.out.println("Afficher lieux");
        //afficherTousLieux();
        /*
        System.out.println("Afficher demandes");
        avoirDemandesDe(45);
         */
 
        System.out.println("Ajouter demande 43");
        ajouterDemande(17, 43);
        avoirDemandesDe(43);
         
        //System.out.println("Test de mauvaise connexion");
        //testerConnexion("toto");
        //System.out.println("Test de bonne connexion");
        //testerConnexion("asing8183@free.fr");
        System.out.println("Afficher demandes futures");
        afficherToutesDemandesFutures();
        System.out.println("Afficher des demandes perso");
        avoirDemandesDe(45);
        saturerTennis();
        System.out.println("Attribuer un lieu");
        attribuerLieu(5,4401,10f);
        System.out.println("Afficher Evenement Non Validé par l'admin");
        afficherNonValide();
        
        afficherMoment();
        
        JpaUtil.destroy();
    }

    private static void afficherTousAdherents() {
        AdherentDAO mon_adherent_dao = new AdherentDAO();

        try {
            JpaUtil.creerEntityManager();
            List<Adherent> Liste_Adherents = mon_adherent_dao.findAll();
            int taille = Liste_Adherents.size();

            for (int i = 0; i < taille; i++) {
                System.out.println(Liste_Adherents.get(i).toString());
            }

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            JpaUtil.fermerEntityManager();
        }
    }

    private static void inscrireAdherent() {
        ServiceMetier service = new ServiceMetier();
        Adherent mama = new Adherent("Curie", "Marie", "m.c@fr.fr", "France");
        System.out.println(service.inscription(mama));
    }

    private static void afficherTousLieux() {
        ServiceMetier service = new ServiceMetier();

        List<Lieu> Liste_Lieux = service.obtenirLieux();
        int taille = Liste_Lieux.size();

        for (int i = 0; i < taille; i++) {
            System.out.println(Liste_Lieux.get(i).toString());
        }

    }

    private static void ajouterDemande(long activite, long adherent) {
        ServiceMetier service = new ServiceMetier();

        ActiviteDAO mon_activite_dao = new ActiviteDAO();
        AdherentDAO mon_adherent_dao = new AdherentDAO();

        try {
            JpaUtil.creerEntityManager();
            Activite activite_test = mon_activite_dao.findById(activite);
            Adherent adheration = mon_adherent_dao.findById(adherent);
            JpaUtil.fermerEntityManager();
            service.nouvelleDemandeEvenement(new Date(), Moment.apres_midi, activite_test, adheration);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void avoirDemandesDe(long adherent) {
        ServiceMetier service = new ServiceMetier();

        AdherentDAO mon_adherent_dao = new AdherentDAO();

        try {
            JpaUtil.creerEntityManager();
            Adherent adheration = mon_adherent_dao.findById(adherent);
            JpaUtil.fermerEntityManager();
            List<DemandeEvenement> all = service.obtenirDemandesPerso(adheration, false);
            for (int i = 0; i < all.size(); i++) {
                System.out.println(all.get(i));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void testerConnexion(String mail) {
        ServiceMetier service = new ServiceMetier();
        System.out.println(service.connexion(mail));
    }

    private static void afficherToutesDemandesFutures() {
        ServiceMetier service = new ServiceMetier();
        List<DemandeEvenement> all = service.obtenirDemandesFuturesNonComplet();
        int taille = all.size();
        for (int i = 0; i < taille; i++) {
            System.out.println(all.get(i));
        }
    }

    private static void saturerTennis() {
        ServiceMetier service = new ServiceMetier();
        AdherentDAO mon_adherent_dao = new AdherentDAO();
        ActiviteDAO mon_activite_dao = new ActiviteDAO();

        Activite activite_test = null;

        Adherent adheration1 = null;
        Adherent adheration2 = null;
        Adherent adheration3 = null;
        try {

            JpaUtil.creerEntityManager();
            activite_test = mon_activite_dao.findById(17);
            adheration1 = mon_adherent_dao.findById(41);
            adheration2 = mon_adherent_dao.findById(42);
            adheration3 = mon_adherent_dao.findById(43);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }

        service.nouvelleDemandeEvenement(new Date(), Moment.soir, activite_test, adheration1);
        service.nouvelleDemandeEvenement(new Date(), Moment.soir, activite_test, adheration2);
        service.nouvelleDemandeEvenement(new Date(), Moment.soir, activite_test, adheration3);
    }

    private static void attribuerLieu(long lieu, long eventparam) {
        ServiceMetier service = new ServiceMetier();
        EvenementDAO mon_evenement_dao = new EvenementDAO();
        LieuDAO mon_lieu_dao = new LieuDAO();
        Lieu l = null;
        Evenement event = null;
        try {

            JpaUtil.creerEntityManager();
            l = mon_lieu_dao.findById(lieu);
            event = mon_evenement_dao.findById(eventparam);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        if (event instanceof EvenementPayant) {
            EvenementPayant eventp = (EvenementPayant) event;

            service.validerEvenement(eventp, l, 10f);
        }
    }

    private static void attribuerLieu(long lieu, long eventparam, float paf) {
        ServiceMetier service = new ServiceMetier();
        EvenementDAO mon_evenement_dao = new EvenementDAO();
        LieuDAO mon_lieu_dao = new LieuDAO();
        Lieu l = null;
        Evenement event = null;

        try {

            JpaUtil.creerEntityManager();
            l = mon_lieu_dao.findById(lieu);
            event = mon_evenement_dao.findById(eventparam);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        if (event instanceof EvenementPayant) {
            EvenementPayant eventp = (EvenementPayant) event;

            service.validerEvenement(eventp, l, paf);
        }
    }

    private static void afficherNonValide() {
        ServiceMetier service = new ServiceMetier();
        List<DemandeEvenement> demande_non_complete = service.obtenirDemandesFuturesNonComplet();

        int taille = demande_non_complete.size();
        for (int i = 0; i < taille; i++) {
            System.out.println(demande_non_complete.get(i));
        }
    }

    private static void afficherMoment(){
        ServiceMetier service = new ServiceMetier();
        List<Moment> list_m = service.obtenirMoments();
        for (int i = 0; i < list_m.size(); i++) {
            System.out.println(list_m.get(i));
        }
        
    }
}
