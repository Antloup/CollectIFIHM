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
public class EvenementPayant extends Evenement implements Serializable {

    protected float PAF;

    protected EvenementPayant() {
    }

    public EvenementPayant(DemandeEvenement demandeAboutie) {
        super(demandeAboutie);
        PAF = 0;
    }

    public float getPAF() {
        return PAF;
    }

    public void setPAF(float prix) {
        this.PAF = prix;
    }
    
    public boolean estPayant(){
        return true;
    }

    @Override
    public String toString() {
        return "EvenementPayant{" + "id=" + id + ", demandeAboutieId=" + demandeAboutie.getId() + ", lieu=" + lieu + ", validated=" + validated + ", PAF=" + PAF + '}';
    }

}
