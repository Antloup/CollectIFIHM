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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Adherent;
import metier.service.ServiceMetier;

/**
 *
 * @author Anthony
 */
public class InscriptionAction extends Action{

    @Override
    public String execute(HttpServletRequest request) {
        ServiceMetier sm = new ServiceMetier();
        JpaUtil.init();
        Adherent adherent = new Adherent(request.getParameter("nom"), request.getParameter("prenom"), request.getParameter("email"), request.getParameter("adresse"));
        Adherent result = sm.inscription(adherent);
        JsonObject jsonResponse = new JsonObject();
        
        if(result == null){ // Inscription pas OK
            jsonResponse.addProperty("Inscription", "KO");
        }
        else{
            jsonResponse.addProperty("Inscription", "OK");
            HttpSession session = request.getSession();
            session.setAttribute( "Id", adherent.getId() );
            session.setAttribute( "Email", adherent.getMail());
        }
        
        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonResponse);
        JpaUtil.destroy();
        return json;
    }
    
}
