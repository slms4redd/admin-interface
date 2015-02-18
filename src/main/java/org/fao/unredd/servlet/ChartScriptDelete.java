/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ResourcePOJO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class ChartScriptDelete extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4588334659713722575L;

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
        
        ResourcePOJO resource = manager.getResource(id, false);
        if (!CategoryPOJO.CHARTSCRIPT.equals(resource.getCategory()))
            throw new ServletException("Category (resource id = " + id + " is not a " + CategoryPOJO.CHARTSCRIPT.getName());
        
        manager.deleteResource(id);
        try {
            // delete related ChartData
            List<ResourcePOJO> layerUpdates = manager.searchChartDataByChartScript(resource.getName());
            for (ResourcePOJO res : layerUpdates)
                manager.deleteResource(res.getId());
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
        
        response.sendRedirect("ChartScriptList");
    }
}
