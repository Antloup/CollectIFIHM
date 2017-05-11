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
import metier.modele.Adherent;
import metier.modele.Evenement;
import metier.service.ServiceMetier;

/**
 *
 * @author augustin
 */
public class ListeAdherentsEvenement extends Action{

    @Override
    public String execute(HttpServletRequest request) {
        ServiceMetier sm = new ServiceMetier();
        List<Evenement> le = sm.obtenirEvenementAValider(); // TODO : a modifier, ca doit etre le commande complete.
        List<Adherent> la = null;
        
        if(le != null){
            request.setAttribute("ListActivite", le);
        }
        else{
            request.setAttribute("ListActivite", "NULL");
        }
        
        JsonArray jsonListe = new JsonArray();
        
        int id = Integer.parseInt(request.getParameter("id"));
        
        //Trouve la liste des adherents depuis l'id de l'event
        for (Evenement de : le) {
            if(de.getId() == id){
                    la = de.getDemandeAboutie().getList_adher();
                    break;
            }
        }
        
        for (Adherent a : la) {
            JsonObject jsonAdherent = new JsonObject();
            
            jsonAdherent.addProperty("id", a.getId());
            jsonAdherent.addProperty("prenom", a.getPrenom());
            jsonAdherent.addProperty("nom", a.getNom());
            jsonAdherent.addProperty("latitude", a.getLatitude());
            jsonAdherent.addProperty("longitude", a.getLongitude());
            
            jsonListe.add(jsonAdherent);
        }
        
        //Objet JSON "conteneur"
        JsonObject container = new JsonObject();
        container.add("adherents", jsonListe);
        
        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(container);
        
        return json;
    }
    
}