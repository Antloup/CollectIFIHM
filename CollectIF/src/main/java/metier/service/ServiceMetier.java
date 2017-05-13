/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.service;

import com.google.maps.model.LatLng;
import dao.ActiviteDAO;
import dao.AdherentDAO;
import dao.DemandeEvenementDAO;
import dao.EvenementDAO;
import dao.JpaUtil;
import dao.LieuDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;
import metier.modele.*;
import metier.service.*;
import util.Moment;
import util.Saisie;

/**
 *
 * @author hmartin2
 */
public class ServiceMetier {

    public Adherent connexion(String mail) {
        AdherentDAO mon_adherent_dao = new AdherentDAO();
        Adherent r = null;
        try {
            JpaUtil.creerEntityManager();
            Adherent adher = mon_adherent_dao.findByMail(mail);
            r = adher;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return r;
    }

    public Adherent inscription(Adherent mon_adherent) {
        Adherent result = null;
        AdherentDAO mon_adherent_dao = new AdherentDAO();
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            mon_adherent_dao.createAdherent(mon_adherent);
            JpaUtil.validerTransaction();
            result = mon_adherent;
            ServiceTechnique.envoyerMailInscription(mon_adherent);
            //throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            ServiceTechnique.envoyerMailInscription(mon_adherent.getMail(), mon_adherent.getPrenom());
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return result;
    }

    public List<Activite> obtenirActivites() {
        ActiviteDAO mon_activite_dao = new ActiviteDAO();
        List<Activite> r = null;
        try {
            JpaUtil.creerEntityManager();
            r = mon_activite_dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return r;
    }
    
    // RAJOUTER
    public Activite obtenirActivite(long id) {
        ActiviteDAO mon_activite_dao = new ActiviteDAO();
        Activite r = null;
        try {
            JpaUtil.creerEntityManager();
            r = mon_activite_dao.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return r;
    }

    public List<Lieu> obtenirLieux() {
        LieuDAO mon_lieu_dao = new LieuDAO();
        List<Lieu> r = null;
        try {
            JpaUtil.creerEntityManager();
            r = mon_lieu_dao.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return r;
    }

    public List<Moment> obtenirMoments() {
        List<Moment> r = new ArrayList<>();
        for (int i = 0; i < Moment.values().length; i++) {
            r.add(Moment.values()[i]);
        }
        return r;
    }

    public List<DemandeEvenement> obtenirDemandesFuturesNonComplet() {
        DemandeEvenementDAO ma_demande_evenement_dao = new DemandeEvenementDAO();
        Date now = new Date();
        List<DemandeEvenement> r = null;
        try {
            JpaUtil.creerEntityManager();
            r = ma_demande_evenement_dao.findFutureNotComplete(now);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return r;
    }

    private List<DemandeEvenement> obtenirDemandesPerso(Adherent user) {
        DemandeEvenementDAO ma_demande_evenement_dao = new DemandeEvenementDAO();
        List<DemandeEvenement> r = null;
        try {
            r = ma_demande_evenement_dao.findOwn(user, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    public List<DemandeEvenement> obtenirDemandesPerso(Adherent user, boolean complete_only) {
        List<DemandeEvenement> r = null;
        try {
            JpaUtil.creerEntityManager();
            if (!complete_only) {
                r = obtenirDemandesPerso(user);
            } else {
                DemandeEvenementDAO ma_demande_evenement_dao = new DemandeEvenementDAO();
                List<DemandeEvenement> ma_list_demande = ma_demande_evenement_dao.findOwn(user, true);
                r = ma_demande_evenement_dao.findOwn(user, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return r;
    }
    
    //RAJOUTEE
    public Evenement obtenirEvenement(DemandeEvenement de){
        EvenementDAO mon_evenement_dao = new EvenementDAO();
        List<Evenement> r = null;
        Evenement event = null;
        try {
            JpaUtil.creerEntityManager();
            r = mon_evenement_dao.findAll();
            for(Evenement e : r){
                if(e.getDemandeAboutie().getId() == de.getId()){
                    event = e;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return event;
    }

    public DemandeEvenement nouvelleDemandeEvenement(Date date, Moment day_moment, Activite activity, Adherent user) {
        DemandeEvenementDAO ma_demande_evenement_dao = new DemandeEvenementDAO();
        DemandeEvenement demandeEnCours = null;
        boolean conflict = false;
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            demandeEnCours = ma_demande_evenement_dao.findSimilarNotComplete(date, day_moment, activity);

            Saisie.pause();
            if (demandeEnCours != null) {
                demandeEnCours.ajoutAdherent(user);
                ma_demande_evenement_dao.updateDemandeEvenement(demandeEnCours);
            } else {
                demandeEnCours = new DemandeEvenement(date, day_moment, activity, user);
                ma_demande_evenement_dao.createDemandeEvenement(demandeEnCours);
            }

            if (demandeEnCours.getListSize() == demandeEnCours.getActivity().getNbParticipants()) {

                EvenementDAO mon_evenement_dao = new EvenementDAO();

                Evenement event = null;
                if (!demandeEnCours.getActivity().getPayant()) {
                    event = new EvenementGratuit(demandeEnCours);
                } else {
                    event = new EvenementPayant(demandeEnCours);
                }
                demandeEnCours.setEvent(event);

                mon_evenement_dao.createEvenement(event);
            }

            JpaUtil.validerTransaction();
        } catch (RollbackException e) {
            JpaUtil.annulerTransaction();
            conflict = true;
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }

        if (conflict) {
            demandeEnCours = nouvelleDemandeEvenement(date, day_moment, activity, user);
        }

        return demandeEnCours;
    }

    public Evenement validerEvenement(EvenementGratuit event, Lieu place) {
        EvenementDAO mon_evenement_dao = new EvenementDAO();
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            event.setLieu(place);
            event.setValidated(true);
            mon_evenement_dao.updateEvenement(event);
            JpaUtil.validerTransaction();
            List<Adherent> list_adher = event.getDemandeAboutie().getList_adher();
            int taille = list_adher.size();
            for (int i = 0; i < taille; i++) {
                ServiceTechnique.envoyerMailParticipation(event, list_adher.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return event;
    }

    public Evenement validerEvenement(EvenementPayant event, Lieu place, float paf_le_chien) {
        EvenementDAO mon_evenement_dao = new EvenementDAO();
        try {
            JpaUtil.creerEntityManager();
            JpaUtil.ouvrirTransaction();
            event.setLieu(place);
            event.setPAF(paf_le_chien);
            event.setValidated(true);
            mon_evenement_dao.updateEvenement(event);
            JpaUtil.validerTransaction();
            List<Adherent> list_adher = event.getDemandeAboutie().getList_adher();
            int taille = list_adher.size();
            for (int i = 0; i < taille; i++) {
                ServiceTechnique.envoyerMailParticipation(event, list_adher.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return event;
    }

    public List<Evenement> obtenirEvenementAValider() {
        EvenementDAO mon_evenement_dao = new EvenementDAO();
        List<Evenement> r = null;
        try {
            JpaUtil.creerEntityManager();
            r = mon_evenement_dao.findNotValidated();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JpaUtil.fermerEntityManager();
        }
        return r;
    }

    public Evenement validerEvenement(DemandeEvenement de, Lieu lieu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
