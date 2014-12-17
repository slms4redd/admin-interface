/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.unredd.services.UNREDDPersistenceFacade;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fao.unredd.Util;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sgiaccio
 */
public class ChartScriptReprocess extends HttpServlet {

    private final static org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ChartScriptByLayer.class);
    
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
            throws ServletException, IOException {
        Long id   = Long.parseLong(request.getParameter("id"));
        String   chartName = manager.getResource(id, false).getName();
        
        LOGGER.info("Saving flow config: " + Util.getGeostoreFlowSaveDir(getServletContext()) + File.separator + "reprocess");
        Util.saveReprocessFile(getServletContext(), getXml(chartName), Util.getGeostoreFlowSaveDir(getServletContext()));
        
        response.sendRedirect("ChartScriptList");
    }

    private String getXml(String chartName) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r");
        xml.append("<ReprocessChart>\r");
        xml.append("\t<chartName>").append(chartName).append("</chartName>\r");
        xml.append("</ReprocessChart>\r");
        
        return xml.toString();
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
        processRequest(request, response);
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
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
