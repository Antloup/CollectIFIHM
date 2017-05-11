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
import metier.modele.DemandeEvenement;
import metier.modele.Evenement;
import metier.service.ServiceMetier;

/**
 *
 * @author augustin
 */
public class ListeAdminAction extends Action{

    @Override
    public String execute(HttpServletRequest request) {
        JpaUtil.init();
        ServiceMetier sm = new ServiceMetier();
        List<Evenement> le = sm.obtenirEvenementAValider(); // TODO : a modifier, ca doit etre le commande complete.
        
        if(le != null){
            request.setAttribute("ListActivite", le);
        }
        else{
            request.setAttribute("ListActivite", "NULL");
        }
        
        JsonArray jsonListe = new JsonArray();
        
        for (Evenement de : le) {
            
            JsonObject jsonActivite = new JsonObject();
            
            jsonActivite.addProperty("id", de.getId());
            jsonActivite.addProperty("denomination", de.getDemandeAboutie().getActivity().getDenomination());
            jsonActivite.addProperty("date", de.getDemandeAboutie().getDate().toString());
            jsonActivite.addProperty("moment", de.getDemandeAboutie().getDay_moment().toString());
            jsonActivite.addProperty("tarif", de.getDemandeAboutie().getActivity().getPayant());
            jsonActivite.addProperty("nb_participants", de.getDemandeAboutie().getListSize());
            
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
