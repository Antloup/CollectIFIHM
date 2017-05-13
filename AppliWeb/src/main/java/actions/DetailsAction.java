/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.modele.DemandeEvenement;
import metier.modele.Evenement;
import metier.modele.EvenementPayant;
import metier.modele.Lieu;
import metier.service.ServiceMetier;

/**
 *
 * @author Anthony
 */
public class DetailsAction extends Action {

    @Override
    public String execute(HttpServletRequest request) {
        ServiceMetier sm = new ServiceMetier();
        HttpSession session = request.getSession();
        Adherent adherent = sm.connexion((String) session.getAttribute("Email"));
        List<DemandeEvenement> lde = sm.obtenirDemandesPerso(adherent, true);
        DemandeEvenement demande = null;

        JsonObject jsonResponse = new JsonObject();

        for (DemandeEvenement de : lde) {
            long id = Long.parseLong(request.getParameter("id"));
            if (de.getId() == id) {
                demande = de;
                break;
            }
        }

        if (demande == null) {
            return "";
        }

        Evenement event = sm.obtenirEvenement(demande);
        if (event == null) {
            return "";
        }
        
        if(event.getLieu() != null){
            jsonResponse.addProperty("lieu", event.getLieu().getDenomination());
        }
        else{
            jsonResponse.addProperty("lieu", "-");
        }

        if (event instanceof EvenementPayant) {
            EvenementPayant ep = (EvenementPayant) event;
            jsonResponse.addProperty("prix", ep.getPAF());
        } else {
            jsonResponse.addProperty("prix", "-");
        }
        

        jsonResponse.addProperty("activite", demande.getActivity().getDenomination());
        jsonResponse.addProperty("date", demande.getDate().toString());
        jsonResponse.addProperty("moment", demande.getDay_moment().toString());

        //demande.getList_adher();
        JsonArray jsonListe = new JsonArray();

        for (Adherent ad : demande.getList_adher()) {
            JsonObject jsonPersonne = new JsonObject();
            jsonPersonne.addProperty("id", ad.getId());
            jsonPersonne.addProperty("nom", ad.getNom());
            jsonPersonne.addProperty("prenom", ad.getPrenom());
            jsonListe.add(jsonPersonne);
        }

        //Objet JSON "conteneur"
        JsonObject container = new JsonObject();
        container.add("personnes", jsonListe);
        container.add("details", jsonResponse);

        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);

        return json;
    }

}
