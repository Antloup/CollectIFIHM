package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import metier.modele.Adherent;

public class AdherentDAO {

    public Adherent findById(long id) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        Adherent adherent = null;
        try {
            adherent = em.find(Adherent.class, id);
        } catch (Exception e) {
            throw e;
        }
        return adherent;
    }

    public List<Adherent> findAll() throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Adherent> adherents = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a");
            adherents = (List<Adherent>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }

        return adherents;
    }

    public Adherent findByMail(String mail) throws Exception {
        EntityManager em = JpaUtil.obtenirEntityManager();
        List<Adherent> adherents = null;
        try {
            Query q = em.createQuery("SELECT a FROM Adherent a WHERE a.mail = :mail");
            q.setParameter("mail", mail);

            adherents = (List<Adherent>) q.getResultList();
        } catch (Exception e) {
            throw e;
        }
        if (adherents == null) {
            return null;
        } else if (adherents.size() < 1) {
            return null;
        } else {
            return adherents.get(0);
        }
    }

    public long createAdherent(Adherent nouvel_adherent) {
        EntityManager em = JpaUtil.obtenirEntityManager();

        try {
            em.persist(nouvel_adherent);
        } catch (Exception e) {
            throw e;
        }

        return nouvel_adherent.getId();
    }

}
