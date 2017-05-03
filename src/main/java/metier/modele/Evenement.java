/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author hmartin2
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Evenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    @OneToOne
    protected DemandeEvenement demandeAboutie;
    @ManyToOne
    protected Lieu lieu;
    protected boolean validated;

    protected Evenement() {
    }
    
    public abstract boolean estPayant();

    public Evenement(DemandeEvenement demandeAboutie) {
        this.demandeAboutie = demandeAboutie;
        validated = false;
    }

    public Long getId() {
        return id;
    }

    public DemandeEvenement getDemandeAboutie() {
        return demandeAboutie;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }
    /*
    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", demandeAboutie=" + demandeAboutie + ", lieu=" + lieu + ", validated=" + validated + '}';
    }*/

}
