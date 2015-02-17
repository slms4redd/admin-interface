/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.fao.unredd.Util;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class ChartScriptReprocess extends AdminGUIAbstractServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 2222444232136989128L;
    
    private final static Logger LOGGER = Logger.getLogger(ChartScriptReprocess.class);
    
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
        
        try{
            Util.saveReprocessFile(getServletContext(), getXml(chartName), Util.getGeostoreFlowSaveDir(getServletContext()));
        } catch (IOException e) {
            throw new IOException("I/O Error while copying the XML file to in the geobatch watch directory... REVIEW THE APPLICATION 'geobatchFlowSaveDir' DIRECTORY CONFIGURATION");
        }
        
        response.sendRedirect("ChartScriptList");
    }

    private String getXml(String chartName) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r");
        xml.append("<ReprocessChart>\r");
        xml.append("\t<chartName>").append(chartName).append("</chartName>\r");
        xml.append("</ReprocessChart>\r");
        
        return xml.toString();
    }
}
