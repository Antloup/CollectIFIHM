/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import java.util.Objects;
import metier.modele.*;
import util.GeoTest;

/**
 *
 * @author hmartin2
 */
public class ServiceTechnique {

    public static LatLng localiser(String adresse) {
        return GeoTest.getLatLng(adresse);
    }

    public static void envoyerMailInscription(Adherent adher) {
        System.out.println("Expediteur: collectif@collectif.org");
        System.out.println("Pour: " + adher.getMail());
        System.out.println("Sujet: Bienvenue chez Collect'IF");
        System.out.println("Corps:");
        System.out.println("Bonjour " + adher.getPrenom() + ",");
        System.out.println("Nous vous confirmons votre adhésion à l'association COLLECT'IF. Votre numéro d'adhérent est : " + adher.getId() + "\r\n");
    }

    public static void envoyerMailInscription(String mail, String prenom) {
        System.out.println("Expediteur: collectif@collectif.org");
        System.out.println("Pour: " + mail);
        System.out.println("Sujet: Bienvenue chez Collect'IF");
        System.out.println("Corps:");
        System.out.println("Bonjour " + prenom + ",");
        System.out.println("Votre adhésion à l'association COLLECT’IF a malencontreusement échoué... Merci de recommencer ultérieurement\r\n");
    }

    public static void envoyerMailParticipation(Evenement event, Adherent adher) {
        //TODO
        //System.out.println("Confirmation participation Evenement: " + event.toString() + " \r\n situe a une distance de: " + GeoTest.getFlightDistanceInKm(new LatLng(adher.getLongitude(), adher.getLatitude()), new LatLng(event.getLieu().getLongitude(), event.getLieu().getLatitude())) + " km");

        System.out.println("Expediteur: collectif@collectif.org");
        System.out.println("Pour: " + adher.getMail());
        System.out.println("Sujet: Nouvel Évènement Collect'IF");
        System.out.println("Corps:");
        System.out.println("\r\nBonjour " + adher.getPrenom() + ",");
        System.out.println("\r\n\tComme vous l'aviez souhaité, COLLECT’IF organise un évènement de " + event.getDemandeAboutie().getActivity().getDenomination()
                + " le " + event.getDemandeAboutie().getDate() + ". Vous trouverez ci-dessous les détails de cet évènement.\r\n"
                + "\tAssociativement vôtre,\r\n"
                + "\t\tLe Responsable de l'Association\r\n");
        System.out.println("Evénement : " + event.getDemandeAboutie().getActivity().getDenomination());
        System.out.println("Date : " + event.getDemandeAboutie().getDate());
        System.out.println("Lieu : " + event.getLieu().getDenomination() + ", " + event.getLieu().getAdresse());
        System.out.println("(à " + GeoTest.getFlightDistanceInKm(new LatLng(adher.getLongitude(), adher.getLatitude()), new LatLng(event.getLieu().getLongitude(), event.getLieu().getLatitude())) + " km de chez vous)");
        System.out.println("Vous jouerez avec :");
        int taille = event.getDemandeAboutie().getListSize();
        for (int i = 0; i < taille; i++) {
            if (!Objects.equals(event.getDemandeAboutie().getList_adher().get(i).getId(), adher.getId())) {
                System.out.println(event.getDemandeAboutie().getList_adher().get(i).getPrenom() + " " + event.getDemandeAboutie().getList_adher().get(i).getNom());
            }
        }
    }
}
