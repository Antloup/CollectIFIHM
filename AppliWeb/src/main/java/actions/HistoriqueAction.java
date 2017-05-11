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
import dao.JpaUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.DemandeEvenement;
import metier.service.ServiceMetier;

/**
 *
 * @author Anthony
 */
public class HistoriqueAction extends Action{

    @Override
    public String execute(HttpServletRequest request) {
        ServiceMetier sm = new ServiceMetier();

        HttpSession session = request.getSession();
        Adherent adherent = sm.connexion((String)session.getAttribute("Email"));
        List<DemandeEvenement> lde = sm.obtenirDemandesPerso(adherent, false);
        
        JsonArray jsonListe = new JsonArray();

        for (DemandeEvenement de : lde) {
            JsonObject jsonActivite = new JsonObject();
            
            jsonActivite.addProperty("id", de.getId());
            jsonActivite.addProperty("denomination", de.getActivity().getDenomination());
            jsonActivite.addProperty("date", de.getDate().toString());
            jsonActivite.addProperty("moment", de.getDay_moment().toString());
            jsonActivite.addProperty("tarif", de.getActivity().getPayant());
            jsonActivite.addProperty("nb_participants", de.getListSize());
            jsonActivite.addProperty("nb_max", de.getActivity().getNbParticipants());
            jsonActivite.addProperty("payant", de.getActivity().getPayant());
            if(de.getEvent() != null){
                jsonActivite.addProperty("etat", de.getEvent().isValidated());
            }
            else{
                jsonActivite.addProperty("etat", "NULL");
            }
            jsonListe.add(jsonActivite);
        }

        //Objet JSON "conteneur"
        JsonObject container = new JsonObject();
        container.add("activites", jsonListe);
        
        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);

        return json;
    }
    
}
