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
public class ChartScriptShow extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4314078190514062201L;

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
            Resource res = manager.getResource(id, false);

            //UNREDDChartScript chartScript = new UNREDDChartScript(res);
            //List<String> lRelatedStatDefs = chartScript.getReverseAttributes(UNREDDChartScript.ReverseAttributes.STATSDEF.getName());
            
            request.setAttribute("resource", res);
        } else {
            // new record
            request.setAttribute("resource", null);
        }
        
        try {
            List<Resource> statsDefs = manager.getUNREDDResources(UNREDDCategories.STATSDEF);
            request.setAttribute("statsDefList", statsDefs);
            
            String outputPage = getServletConfig().getInitParameter("outputPage");
            RequestDispatcher rd = request.getRequestDispatcher(outputPage);
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
