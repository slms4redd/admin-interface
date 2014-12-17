package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;
import it.geosolutions.unredd.services.UNREDDPersistenceFacade;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
public class ChartScriptShow extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get HTTP parameters
        String sId = request.getParameter("id");
        
        if (sId != null && !"".equals(sId))
        {
            // existing record
            long id = Long.parseLong(sId);
            
            // get the resource
            GeoStoreClient client = Util.getGeostoreClient(getServletContext());
            Resource res = client.getResource(id);

            //UNREDDChartScript chartScript = new UNREDDChartScript(res);
            //List<String> lRelatedStatDefs = chartScript.getReverseAttributes(UNREDDChartScript.ReverseAttributes.STATSDEF.getName());
            
            request.setAttribute("resource", res);
        } else {
            // new record
            request.setAttribute("resource", null);
        }
        
        try {
            UNREDDPersistenceFacade manager = Util.getGeostoreManager(getServletContext());
            List<Resource> statsDefs = manager.getUNREDDResources(UNREDDCategories.STATSDEF);
            request.setAttribute("statsDefList", statsDefs);
            
            String outputPage = getServletConfig().getInitParameter("outputPage");
            RequestDispatcher rd = request.getRequestDispatcher(outputPage);
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
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
