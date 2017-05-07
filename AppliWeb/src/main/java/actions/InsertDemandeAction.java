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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import metier.modele.Activite;
import metier.modele.Adherent;
import metier.modele.DemandeEvenement;
import metier.service.ServiceMetier;
import util.Moment;

/**
 *
 * @author Anthony
 */
public class InsertDemandeAction extends Action{

    @Override
    public String execute(HttpServletRequest request) {
        System.out.println("\n\n-----------------Demande-------------\n\n");
        ServiceMetier sm = new ServiceMetier();
        JpaUtil.init();
        HttpSession session = request.getSession();
        Adherent adherent = sm.connexion((String)session.getAttribute("Email"));
        DemandeEvenement result =  null;
        JsonObject jsonResponse = new JsonObject();
        if(adherent != null){
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            Date date = null;
            try {
                System.out.println("DATE:"+(String)request.getParameter("date"));
                date = formatter.parse((String)request.getParameter("date"));
                System.out.println("-----------------DATE-------------");
                System.out.println(date);
                System.out.println(formatter.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            long activite_id = Long.parseLong((String)request.getParameter("activite"));
            Activite activite = sm.obtenirActivite(activite_id);
            int int_moment = Integer.parseInt((String)request.getParameter("moment"));
            Moment moment = null;
            switch(int_moment){
                case 0:
                    moment = Moment.matin;
                    break;
                case 1:
                    moment = Moment.apres_midi;
                    break;
                case 2:
                    moment = Moment.soir;
                    break;
                default:
                    moment = Moment.matin;
                    break;
            }
            System.out.println("-----RESUME-------");
            System.out.println(date);
            System.out.println(moment);
            System.out.println(activite);
            System.out.println(adherent);
            result = sm.nouvelleDemandeEvenement(date, moment, activite, adherent);
        }
        
        
        if(result == null){ // Demande pas OK
            jsonResponse.addProperty("Demande", "KO");
        }
        else{
            jsonResponse.addProperty("Demande", "OK");
        }
        
        //Serialisation & Ecriture sur le flux de sortie
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(jsonResponse);
        JpaUtil.destroy();
        return json;
    }
    
}
