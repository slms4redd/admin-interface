package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.unredd.services.data.ResourcePOJO;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class StatsDataShow extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -6927784063605698436L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletName();
        
        long id = Long.parseLong(request.getParameter("id"));

        ResourcePOJO res = manager.getResource(id, false);
        String data = manager.getData(res.getId(), MediaType.WILDCARD_TYPE.toString());

        request.setAttribute("resource", res);
        request.setAttribute("storedData", data);
        
        RequestDispatcher rd = request.getRequestDispatcher("stats-data-show.jsp");
        
        rd.forward(request, response);
    }
}
