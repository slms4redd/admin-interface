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
public class ResourceList extends AdminGUIAbstractServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 2972562927937115937L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String category = getServletConfig().getInitParameter("unreddCategory");
            String outputPage = getServletConfig().getInitParameter("outputPage");

            UNREDDCategories categoryObj = UNREDDCategories.valueOf(category);
            List<Resource> resources = manager.getUNREDDResources(categoryObj);
            request.setAttribute("resources", resources);

            RequestDispatcher rd = request.getRequestDispatcher(outputPage);
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
