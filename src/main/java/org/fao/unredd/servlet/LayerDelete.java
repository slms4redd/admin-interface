/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.geostore.core.model.Resource;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
public class LayerDelete extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5799160532811584811L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        String name = request.getParameter("name");
        
        List<Resource> statsDefs;
        try {
            statsDefs = manager.searchStatsDefByLayer(name);

            if (!statsDefs.isEmpty())
            {
                request.setAttribute("statsDefs", statsDefs);
                RequestDispatcher rd = request.getRequestDispatcher("stats-defs-to-be-deleted.jsp");
                rd.forward(request, response);
            } else {
                manager.deleteLayer(name);
                response.sendRedirect("LayerList");
            }
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
