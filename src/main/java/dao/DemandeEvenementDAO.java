/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.DemandeEvenement;
import util.Moment;

/**
 *
 * @author hmartin2
 */
public class DemandeEvenementDAO {

    public DemandeEvenement findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        DemandeEvenement demandeEvenement = null;
        try {
            demandeEvenement = em.find(DemandeEvenement.class, id);
        } catch (Exception e) {
            throw e;
        }
        return demandeEvenement;
    }

    public DemandeEvenement findSimilarNotComplete(Date date, Moment day_moment, Activite activity) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<DemandeEvenement> demandesEvenements = null;
        try {
            Query q = em.createQuery("SELECT d FROM DemandeEvenement d WHERE d.activity = :acti "
                    + "AND d.day_moment = :moment "
                    + "AND d.date = :date "
                    + "AND d.event IS NULL");
            q.setParameter("acti", activity);
            q.setParameter("moment", day_moment);
            q.setParameter("date", date);

            demandesEvenements = (List<DemandeEvenement>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        if (demandesEvenements.size() < 1) {
            return null;
        } else {
            return demandesEvenements.get(0);
        }
    }

    public List<DemandeEvenement> findFutureNotComplete(Date date) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<DemandeEvenement> demandesEvenements = null;
        try {
            Query q = em.createQuery("SELECT d FROM DemandeEvenement d WHERE d.date >= :date "
                    + "AND d.event IS NULL");
            q.setParameter("date", date);

            demandesEvenements = (List<DemandeEvenement>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return demandesEvenements;
    }

    public List<DemandeEvenement> findOwn(Adherent user, boolean complete) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<DemandeEvenement> demandesEvenements = null;
        try {
            String rqst = "SELECT d FROM DemandeEvenement d WHERE d.list_adher = :user ";
            if (complete) {
                rqst += "AND d.event IS NOT NULL";
            }
            Query q = em.createQuery(rqst);
            q.setParameter("user", user);

            demandesEvenements = (List<DemandeEvenement>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        return demandesEvenements;
    }

    public List<DemandeEvenement> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<DemandeEvenement> demandesEvenements = null;
        try {
            Query q = em.createQuery("SELECT d FROM DemandeEvenement d");
            demandesEvenements = (List<DemandeEvenement>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return demandesEvenements;
    }

    public long createDemandeEvenement(DemandeEvenement demandeEvenement) {
        EntityManager em = JpaUtil.obtenirEntityManager();

        try {
            em.persist(demandeEvenement);
        } catch (Exception e) {
            throw e;
        }

        return demandeEvenement.getId();
    }

    public long updateDemandeEvenement(DemandeEvenement demandeEvenement) {
        EntityManager em = JpaUtil.obtenirEntityManager();

        try {
            em.merge(demandeEvenement);
        } catch (Exception e) {
            throw e;
        }

        return demandeEvenement.getId();
    }

}
