/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.unredd.services.data.ResourcePOJO;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fao.unredd.Util;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class StatsDefReprocess extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 6176692885854491385L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long   statsDefId = Long.parseLong(request.getParameter("id"));
        
        ResourcePOJO unreddStatsDefResource = manager.getResource(statsDefId, false);
        String statsDefName = unreddStatsDefResource.getName();
        
        String xml = getXml(statsDefName);
        
        try {
            Util.saveReprocessFile(getServletContext(), xml, Util.getGeostoreFlowSaveDir(getServletContext()) + File.separator + "reprocess");
        } catch (IOException e) {
            throw new IOException("I/O Error while copying the XML file to in the geobatch watch directory... REVIEW THE APPLICATION 'geobatchFlowSaveDir' DIRECTORY CONFIGURATION");
        }
        
        //RequestDispatcher rd = request.getRequestDispatcher("LayerUpdateList?layer=" + layerName);
        //rd.forward(request, response);
        response.sendRedirect("StatsDefList");
     }

    private String getXml(String statsDefName) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r");
        xml.append("<ReprocessStats>\r");
        xml.append("\t<statsName>").append(statsDefName).append("</statsName>\r");
        xml.append("</ReprocessStats>\r");
        
        return xml.toString();
    }
}
