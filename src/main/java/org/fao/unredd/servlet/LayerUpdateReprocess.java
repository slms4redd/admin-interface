/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fao.unredd.Util;

/**
 *
 * @author sgiaccio
 */
public class LayerUpdateReprocess extends HttpServlet {
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long   layerId   = Long.parseLong(request.getParameter("layerUpdateId"));
        
        GeoStoreClient client = Util.getGeostoreClient(getServletContext());
        
        UNREDDLayerUpdate unreddLayerUpdate = new UNREDDLayerUpdate(client.getResource(layerId));
        String layerName = unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.LAYER);
        String year      = unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.YEAR);
        String month     = unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.MONTH);
        
        //System.out.println("Saving flow config: " + Util.getGeostoreFlowSaveDir(getServletContext()) + File.separator + "reprocess"); // DEBUG
        Util.saveReprocessFile(getServletContext(), getXml(layerName, year, month), Util.getGeostoreFlowSaveDir(getServletContext()) + File.separator + "reprocess");
        
        //RequestDispatcher rd = request.getRequestDispatcher("LayerUpdateList?layer=" + layerName);
        //rd.forward(request, response);
        response.sendRedirect("LayerUpdateList?layer=" + layerName);
    }

    private String getXml(String layerName, String year, String month) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        xml.append("<ReprocessLayer>\n");
        xml.append("\t<layerName>").append(layerName).append("</layerName>\n");
        if (month != null) xml.append("\t<month>").append(month).append("</month>\n");
        xml.append("\t<year>").append(year).append("</year>\n");
        xml.append("</ReprocessLayer>\n");
        
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
