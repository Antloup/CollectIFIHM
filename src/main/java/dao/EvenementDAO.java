/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Evenement;
import metier.modele.EvenementGratuit;
import metier.modele.EvenementPayant;

/**
 *
 * @author hmartin2
 */
public class EvenementDAO {

    public Evenement findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Evenement event = null;
        try {
            event = em.find(Evenement.class, id);
        } catch (Exception e) {
            throw e;
        }
        return event;
    }

    public EvenementPayant findPayantById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        EvenementPayant event = null;
        try {
            event = em.find(EvenementPayant.class, id);
        } catch (Exception e) {
            throw e;
        }
        return event;
    }

    public List<Evenement> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> event = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e");
            event = (List<Evenement>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return event;
    }

    public long createEvenement(Evenement event) {
        EntityManager em = JpaUtil.obtenirEntityManager();

        try {
            em.persist(event);
        } catch (Exception e) {
            throw e;
        }

        return event.getId();
    }

    public long updateEvenement(Evenement event) {
        EntityManager em = JpaUtil.obtenirEntityManager();

        try {
            em.merge(event);
        } catch (Exception e) {
            throw e;
        }

        return event.getId();
    }

    public List<Evenement> findNotValidated() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Evenement> event = null;
        try {
            Query q = em.createQuery("SELECT e FROM Evenement e WHERE e.validated = false");
            event = (List<Evenement>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return event;
    }

}
