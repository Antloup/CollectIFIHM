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
import metier.service.ServiceMetier;
import util.Moment;

/**
 *
 * @author Anthony
 */
public class MomentsAction extends Action{

    @Override
    public String execute(HttpServletRequest request) {
        ServiceMetier sm = new ServiceMetier();
        
        List<Moment> lm = sm.obtenirMoments();
        
        if(lm != null){
            request.setAttribute("ListMoment", lm);
        }
        else{
            request.setAttribute("ListMoment", "NULL");
        }
        
        JsonArray jsonListe = new JsonArray();
        
        for (Moment moment : lm) {
            JsonObject jsonActivite = new JsonObject();
            
            jsonActivite.addProperty("id", moment.ordinal());
            jsonActivite.addProperty("denomination", moment.name());
            
            jsonListe.add(jsonActivite);
        }
        
        //Objet JSON "conteneur"
        JsonObject container = new JsonObject();
        container.add("moments", jsonListe);
        
        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);
        
        return json;
    }
    
}
