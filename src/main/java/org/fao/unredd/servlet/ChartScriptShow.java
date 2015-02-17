package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ResourcePOJO;

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
            ResourcePOJO res = manager.getResource(id, false);

            if(!CategoryPOJO.CHARTSCRIPT.equals(res.getCategory())){
                throw new IOException("The requested resource with id '" + id + "' is not a ChartScript resource as expected... this should never happen...");
            }
            
//            ResourceDecorator chartScript = new ResourceDecorator(res);
//            List<String> lRelatedStatDefs = chartScript.getAttributeValues(ModelDomainNames.CHARTSCRIPT_STATDEF);
            
            request.setAttribute("resource", res);
        } else {
            // new record
            request.setAttribute("resource", null);
        }
        
        try {
            List<ResourcePOJO> statsDefs = manager.getUNREDDResources(CategoryPOJO.STATSDEF);
            request.setAttribute("statsDefList", statsDefs);
            
            String outputPage = getServletConfig().getInitParameter("outputPage");
            RequestDispatcher rd = request.getRequestDispatcher(outputPage);
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
