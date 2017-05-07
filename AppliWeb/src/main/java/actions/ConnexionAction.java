/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
public class ConnexionAction extends Action{

    @Override
    public String execute(HttpServletRequest request) {
        
        ServiceMetier sm = new ServiceMetier();
        JpaUtil.init();
        Adherent adherent = sm.connexion(request.getParameter("email"));
        
        JsonObject jsonResponse = new JsonObject();
        if(adherent == null){ // Connexion échoué
            jsonResponse.addProperty("Connexion", "KO");
        }
        else{
            jsonResponse.addProperty("Connexion", "OK");
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
