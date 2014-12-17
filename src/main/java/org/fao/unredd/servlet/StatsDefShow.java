package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;
import it.geosolutions.unredd.geostore.model.UNREDDStatsDef;
import it.geosolutions.unredd.services.UNREDDPersistenceFacade;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

import org.fao.unredd.Util;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sgiaccio
 */
public class StatsDefShow extends HttpServlet {

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
        try {
            getServletName();

            String name = request.getParameter("name");

            if (name != null && !"".equals(name))
            {   
                Resource res = manager.searchResourceByName(name, UNREDDCategories.STATSDEF);
                
                String data = manager.getData(res.getId(), MediaType.WILDCARD_TYPE);

                request.setAttribute("resource", res);

                request.setAttribute("storedData", data);

                UNREDDStatsDef statsDef = new UNREDDStatsDef(res);
                String zonalLayer = statsDef.getAttribute(UNREDDStatsDef.Attributes.ZONALLAYER);
                request.setAttribute("zonalLayer", zonalLayer);

                List<Resource> relatedLayers = new ArrayList<Resource>();
                
                List<String> layerNames = statsDef.getReverseAttributes(UNREDDStatsDef.ReverseAttributes.LAYER.getName());
                for (String layerName : layerNames) {
                    relatedLayers.add(manager.searchLayer(layerName));
                }
                request.setAttribute("relatedLayers", relatedLayers);

                List<Resource> relatedChartScripts = manager.searchChartScriptByStatsDef(res.getName());
                request.setAttribute("relatedChartScripts", relatedChartScripts);
            } else {
                request.setAttribute("resource", null);
                request.setAttribute("storedData", "");
            }

            List<Resource> layers = manager.getLayers();
            request.setAttribute("layerList", layers);

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
