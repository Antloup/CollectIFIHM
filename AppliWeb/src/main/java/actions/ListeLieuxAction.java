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
import metier.modele.Lieu;
import metier.service.ServiceMetier;

/**
 *
 * @author Anthony
 */
public class ListeLieuxAction extends Action{

    @Override
    public String execute(HttpServletRequest request) {

        ServiceMetier sm = new ServiceMetier();
        List<Lieu> ll = sm.obtenirLieux();

        if(ll != null){
            request.setAttribute("ListLieu", ll);
        }
        else{
            request.setAttribute("ListLieu", "NULL");
        }
        
        JsonArray jsonListe = new JsonArray();
        
        for (Lieu lieu : ll) {
            
            JsonObject jsonActivite = new JsonObject();
            
            jsonActivite.addProperty("id", lieu.getId());
            jsonActivite.addProperty("denomination", lieu.getDenomination());
            jsonActivite.addProperty("lat", lieu.getLatitude());
            jsonActivite.addProperty("lng", lieu.getLongitude());
            jsonActivite.addProperty("adresse", lieu.getAdresse());
            
            jsonListe.add(jsonActivite);
        }
        
        //Objet JSON "conteneur"
        JsonObject container = new JsonObject();
        container.add("lieux", jsonListe);
        
        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);
        
        return json;
    }
    
}
