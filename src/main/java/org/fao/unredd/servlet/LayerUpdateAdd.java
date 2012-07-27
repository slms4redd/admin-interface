package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fao.unredd.Util;

/**
 *
 * @author sgiaccio
 */
public class LayerUpdateAdd extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String year = request.getParameter("year");
        if (year == null) throw new ServletException();
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            GeoStoreClient client = Util.getGeostoreClient(getServletContext());

            UNREDDLayerUpdate unreddLayerUpdate = new UNREDDLayerUpdate();

            unreddLayerUpdate.setAttribute(UNREDDLayerUpdate.Attributes.LAYER, "forest_mask");
            unreddLayerUpdate.setAttribute(UNREDDLayerUpdate.Attributes.YEAR, year);
            //unreddLayerUpdate.setAttribute(UNREDDLayerUpdate.Attributes.MONTH, "0");
            //unreddLayerUpdate.setAttribute(UNREDDLayerUpdate.Attributes.RELATIVEPATH, "[relativepath]");
            unreddLayerUpdate.setAttribute(UNREDDLayerUpdate.Attributes.PUBLISHED, "true");

            RESTResource chartScriptRestResource = unreddLayerUpdate.createRESTResource();

            chartScriptRestResource.setName("forest_mask_" + year);
            long id = client.insert(chartScriptRestResource);

            out.println("id = " + id);
        } finally {
            out.close();
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
}
