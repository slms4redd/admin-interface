package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.core.model.Resource;

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
public class StatsDataList extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -398294415393024699L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String statsDefName = request.getParameter("stats_def");
        
        try {
            List<Resource> resources;
            if (statsDefName == null || "".equals(statsDefName)) {
                // if no layer is given in http parameters, find all stats data
                resources = manager.searchStatsDataByStatsDef(null);
            } else {
                // otherwise find all stats with the given statsDef attribute
                resources = manager.searchStatsDataByStatsDef(statsDefName);
                //request.setAttribute("statsDef", statsDefName);
            }
            
            request.setAttribute("resources", resources);
            RequestDispatcher rd = request.getRequestDispatcher("stats-data-list.jsp");
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
