package fr.insalyon.dasi.appliweb;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import actions.Action;
import actions.ListeEvenementsAction;
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
@WebServlet(name = "ActionServlet",urlPatterns = {"/ActionServlet"})
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
        
        
        String todo = request.getParameter("todo");
        if(todo != null){       
        switch(todo){
            case "ListeEvenements":{
                Action action = new ListeEvenementsAction();
                action.execute(request);
                this.getServletContext().getRequestDispatcher("/EventsList.jsp").forward(request, response);
                //RequestDispatcher rd = request.getRequestDispatcher("/ServletVueEtudiant");
                //rd.forward(request, response);
//                response.setContentType("text/html;charset=UTF-8");
//                try (PrintWriter out = response.getWriter()) {
//                    out.println("<!DOCTYPE html>");
//                    out.println("<html>");
//                    out.println("<head>");
//                    out.println("<title>Servlet ActionServlet</title>");            
//                    out.println("</head>");
//                    out.println("<body>");
//                    out.println("<h1>Servlet ActionServlet at " + request.getRequestURI()+ "</h1>");
//                    out.println(request.getAttribute("ListActivite"));
//                    out.println("ListeEvenements");
//                    out.println("</body>");
//                    out.println("</html>");
//                }
                break;
            }
            
            default:{
                response.setContentType("text/html;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Servlet ActionServlet</title>");            
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h1>Servlet ActionServlet at " + request.getRequestURI()+ "</h1>");
                    out.println("Default");
                    out.println("<a href=\"ActionServlet?todo=ListeEvenements\">Voir la liste</a>");
                    out.println("</body>");
                    out.println("</html>");
                }
                break;
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