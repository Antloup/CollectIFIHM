/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actions;

import dao.JpaUtil;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Activite;
import metier.modele.Lieu;
import metier.service.ServiceMetier;

/**
 *
 * @author Anthony
 */
public class ListeEvenementsAction extends Action{

    @Override
    public void execute(HttpServletRequest request) {
        ServiceMetier sm = new ServiceMetier();
        JpaUtil.init();
        
        List<Activite> la = sm.obtenirActivites();
        if(la != null){
            request.setAttribute("ListActivite", la);
        }
        else{
            request.setAttribute("ListActivite", "NULL");
        }
        JpaUtil.destroy();
        
    }
    
}
