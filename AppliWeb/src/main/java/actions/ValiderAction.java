/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.modele.DemandeEvenement;
import metier.modele.Evenement;
import metier.modele.EvenementGratuit;
import metier.modele.EvenementPayant;
import metier.modele.Lieu;
import metier.service.ServiceMetier;

/**
 *
 * @author Anthony
 */
public class ValiderAction extends Action {

    @Override
    public String execute(HttpServletRequest request) {
        ServiceMetier sm = new ServiceMetier();
        long prix = Long.parseLong(request.getParameter("prix"));
        long id_lieu = Long.parseLong(request.getParameter("lieu"));
        long id_demande = Long.parseLong(request.getParameter("id"));
        Evenement evt = null;
        Lieu lieu = null;
        //Recherche du lieu
        List<Lieu> ll = sm.obtenirLieux();
        for (Lieu l : ll) {
            if (l.getId() == id_lieu) {
                lieu = l;
                break;
            }
        }

        
        //Recherche de la d'évènement
        List<Evenement> le = sm.obtenirEvenementAValider();
        for (Evenement e : le) {
            if (e.getId() == id_demande) {
                if(e instanceof EvenementPayant){
                    System.out.println("EvenementPayant");
                    evt = sm.validerEvenement((EvenementPayant)e, lieu,prix);
                }
                if(e instanceof EvenementGratuit){
                    System.out.println("EvenementGratuit");
                    evt = sm.validerEvenement((EvenementGratuit)e, lieu);
                }
                break;
            }
        }


        JsonObject jsonResponse = new JsonObject();

        if (evt == null) { // Evenement pas OK
            jsonResponse.addProperty("Validation", "KO");
        } else {
            jsonResponse.addProperty("Validation", "OK");
        }

        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonResponse);
        return json;
    }

}
