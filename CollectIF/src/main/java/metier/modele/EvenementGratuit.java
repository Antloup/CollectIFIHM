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
public class EvenementGratuit extends Evenement {

    protected EvenementGratuit() {
    }

    public EvenementGratuit(DemandeEvenement demandeAboutie) {
        super(demandeAboutie);
    }
    
    public boolean estPayant(){
        return false;
    }

}
