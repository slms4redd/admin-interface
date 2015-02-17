package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.unredd.services.data.ModelDomainNames;
import it.geosolutions.unredd.services.data.ResourcePOJO;
import it.geosolutions.unredd.services.data.utils.ResourceDecorator;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class LayerUpdateAdd extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8582876547293075001L;

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

            ResourcePOJO unreddLayerUpdateRes = new ResourcePOJO();
            ResourceDecorator unreddLayerUpdate = new ResourceDecorator(unreddLayerUpdateRes);
            
            unreddLayerUpdate.addTextAttribute(ModelDomainNames.LAYERUPDATE_LAYER, "forest_mask");
            unreddLayerUpdate.addTextAttribute(ModelDomainNames.LAYERUPDATE_YEAR, year);
            unreddLayerUpdate.addTextAttribute(ModelDomainNames.LAYERUPDATE_PUBLISHED, "true");

            unreddLayerUpdate.setName("forest_mask_" + year);
            long id = manager.insert(unreddLayerUpdateRes);

            out.println("id = " + id);
        } finally {
            out.close();
        }
    }
}
