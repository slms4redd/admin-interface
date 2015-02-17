package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ResourcePOJO;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class StatsDefList extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -7064107254526692213L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String layer = request.getParameter("layer");
        
        try {
            List<ResourcePOJO> resources;
            if (layer == null || "".equals(layer)) {
                // if no layer is given in http parameters, find all stats defs
                CategoryPOJO categoryObj = CategoryPOJO.STATSDEF;
                resources = manager.getUNREDDResources(categoryObj);
            } else {
                // otherwise find all stats defs with the given layer attribute
                resources = manager.searchStatsDefByLayer(layer);
            }
            
            request.setAttribute("resources", resources);
            RequestDispatcher rd = request.getRequestDispatcher("stats-def-list.jsp");
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
