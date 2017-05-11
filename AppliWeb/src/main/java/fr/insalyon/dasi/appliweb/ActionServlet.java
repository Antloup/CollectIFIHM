package fr.insalyon.dasi.appliweb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import actions.Action;
import actions.ActivitesAction;
import actions.ConnexionAction;
import actions.HistoriqueAction;
import actions.InscriptionAction;
import actions.InsertDemandeAction;
import actions.ListeAdminAction;
import actions.ListeEvenementsAction;
import actions.MomentsAction;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Anthony
 */
@WebServlet(name = "ActionServlet", urlPatterns = {"/ActionServlet"})
public class ActionServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String todo = request.getParameter("action");
        if (todo != null) {
            Action action = null;
            switch (todo) {
                case "getListeEvenements": {
                    action = new ListeEvenementsAction();
                    //this.getServletContext().getRequestDispatcher("/AccueilUtilisateurs.html").forward(request, response);
//                RequestDispatcher rd = request.getRequestDispatcher("/ServletVueEtudiant");
//                rd.forward(request, response);
                    //response.setContentType("text/html;charset=UTF-8");
                    break;
                }

                case "Inscription": {
                    action = new InscriptionAction();
                    break;
                }

                case "Connexion": {
                    action = new ConnexionAction();
                    break;
                }

                case "getHistorique": {
                    action = new HistoriqueAction();
                    break;
                }

                case "getActivites": {
                    action = new ActivitesAction();
                    break;
                }

                case "getMoments": {
                    action = new MomentsAction();
                    break;
                }

                case "insertDemande": {
                    action = new InsertDemandeAction();
                    break;
                }

                case "getListeAdmin": {
                    action = new ListeAdminAction();
                    break;
                }

            }
            if (action != null) {
                response.setContentType("application/json");
                try (PrintWriter out = response.getWriter()) {
                    out.println(action.execute(request));
                }
            }

        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
