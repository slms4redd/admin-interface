package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class LayerUpdateList extends AdminGUIAbstractServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -6200686536035383303L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String layerName = request.getParameter("layer");
        
        try {
            List<Resource> resources;
            if (layerName == null || "".equals(layerName)) {
                // if no layer is given in http parameters, find all layersupdates
                resources = manager.getUNREDDResources(UNREDDCategories.LAYERUPDATE);
            } else {
                // otherwise find all layerswith the given layer name
                resources = manager.searchLayerUpdatesByLayerName(layerName);
            }
            
            request.setAttribute("resources", resources);
            RequestDispatcher rd = request.getRequestDispatcher("layer-update-list.jsp");
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
