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
import metier.modele.Activite;
import metier.modele.DemandeEvenement;
import metier.service.ServiceMetier;

/**
 *
 * @author Anthony
 */
public class ActivitesAction extends Action {

    @Override
    public String execute(HttpServletRequest request) {

        JpaUtil.init();
        ServiceMetier sm = new ServiceMetier();
        
        
        
        
        List<Activite> la = null;
        if(sm !=null){
            la = sm.obtenirActivites();
        }
        if (la != null) {
            request.setAttribute("ListActivite", la);
        } else {
            request.setAttribute("ListActivite", "NULL");
        }

        JsonArray jsonListe = new JsonArray();

        for (Activite act : la) {
            JsonObject jsonActivite = new JsonObject();

            jsonActivite.addProperty("id", act.getId());
            jsonActivite.addProperty("denomination", act.getDenomination());

            jsonListe.add(jsonActivite);
        }

        //Objet JSON "conteneur"
        JsonObject container = new JsonObject();
        container.add("activites", jsonListe);

        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);

        JpaUtil.destroy();

        return json;

    }

}
