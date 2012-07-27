/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.UNREDDGeostoreManager;
import it.geosolutions.unredd.geostore.model.UNREDDChartScript;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import org.fao.unredd.Util;

/**
 *
 * @author sgiaccio
 */
public class ChartScriptDelete extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        long id = Long.parseLong(request.getParameter("id"));
        GeoStoreClient client = Util.getGeostoreClient(getServletContext());
        
        Resource resource = client.getResource(id);
        if (!UNREDDChartScript.CATEGORY_NAME.equals(resource.getCategory().getName()))
            throw new ServletException("Category (resource id = " + id + " is not a " + UNREDDChartScript.CATEGORY_NAME);
        
        UNREDDGeostoreManager manager = new UNREDDGeostoreManager(client);
        client.deleteResource(id);
        try {
            // delete related ChartData
            List<Resource> layerUpdates = manager.searchChartDataByChartScript(resource.getName());
            for (Resource res : layerUpdates)
                client.deleteResource(res.getId());
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("ChartScriptList");
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
