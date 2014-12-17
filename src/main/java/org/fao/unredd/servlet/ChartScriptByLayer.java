package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.services.UNREDDPersistenceFacade;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fao.unredd.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sgiaccio
 */
public class ChartScriptByLayer extends HttpServlet {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChartScriptByLayer.class);
    
    @Autowired
    private UNREDDPersistenceFacade manager;
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String layerName = request.getParameter("name");
        
        Set<Resource> chartScripts = searchChartScriptByLayerName(layerName, manager);
        
        request.setAttribute("chartScripts", chartScripts);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


    public Set<Resource> searchChartScriptByLayerName(String layername, UNREDDPersistenceFacade geostore) throws Exception {
        List<Resource> relatedStatsDef = null;
        try {
            relatedStatsDef = geostore.searchStatsDefByLayer(layername);
        } catch (Exception e) {
            LOGGER.debug("Parameter : [layername=" + layername + "]");
            throw new Exception("Error while searching for StatsDef", e);
        }

        Set<Resource> chartScript = new HashSet<Resource>();

        try {
            for (Resource statsDef : relatedStatsDef) {
                List<Resource> localChartScript = geostore.searchChartScriptByStatsDef(statsDef.getName());
                chartScript.addAll(localChartScript);
            }
        } catch (Exception e) {
            throw new Exception("Error while running stats", e);
        }
        
        return chartScript;
    }
}
