package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
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
public class LayerUpdatePublish extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long   layerUpdateId = Long.parseLong(request.getParameter("layerUpdateId"));
        String action  = request.getParameter("action");
        
        GeoStoreClient client = Util.getGeostoreClient(getServletContext());

        UNREDDLayerUpdate unreddLayerUpdate = new UNREDDLayerUpdate(client.getResource(layerUpdateId));
        String layerName = unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.LAYER);
        String year      = unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.YEAR);
        String month     = unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.MONTH);
        String day       = unreddLayerUpdate.getAttribute(UNREDDLayerUpdate.Attributes.DAY);
        
        boolean publish   = "publish".equals(action);
        //boolean unpublish = "republish".equals(action); // unpublish action not yet implemented
        
        unreddLayerUpdate.setAttribute(UNREDDLayerUpdate.Attributes.PUBLISHED, "" + (publish));
        
        RESTResource resource = unreddLayerUpdate.createRESTResource();
        resource.setCategory(null); // Category needs to be null for updates
        
        String xml;
        if (publish) { // only publish action is implemented in GeoBatch for now
            xml = getPublishXml(layerName, year, month, day);
            Util.saveReprocessFile(getServletContext(), xml, Util.getGeostoreFlowSaveDir(getServletContext()) + File.separator + "publish");
            client.updateResource(layerUpdateId, resource);
            
            response.sendRedirect("LayerUpdateList?layer=" + layerName);
        }
                
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
    
    private String getPublishXml(String layerName, String year, String month, String day) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r");
        xml.append("<PublishLayer>\r");
        xml.append("\t<layerName>").append(layerName).append("</layerName>\r");
        if (day != null)   xml.append("\t<dat>").append(day).append("</dat>\n");
        if (month != null) xml.append("\t<month>").append(month).append("</month>\n");
        xml.append("\t<year>").append(year).append("</year>\r");
        xml.append("</PublishLayer>\r");
        
        return xml.toString();
    }
    
    /*
    private String getUnpublishXml(String layerName, String year, String month) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r");
        xml.append("<UnpublishLayer>\r");
        xml.append("\t<layerName>").append(layerName).append("</layerName>\r");
        xml.append("\t<month>").append(month).append("</month>\r");
        xml.append("\t<year>").append(year).append("</year>\r");
        xml.append("</UnpublishLayer>\r");
        
        return xml.toString();
    }
    */
}
