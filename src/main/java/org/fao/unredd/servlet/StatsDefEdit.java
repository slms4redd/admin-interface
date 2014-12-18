package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.geostore.services.rest.model.RESTStoredData;
import it.geosolutions.unredd.geostore.model.UNREDDStatsDef;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class StatsDefEdit extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -2329581346690519868L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sId          = request.getParameter("id");
        String statsDefName = request.getParameter("name");
        String xml          = request.getParameter("xml");
        String[] layers     = request.getParameterValues(UNREDDStatsDef.ReverseAttributes.LAYER.getName());
        String zonalLayer   = request.getParameter(UNREDDStatsDef.Attributes.ZONALLAYER.getName());
        
        boolean newRecord = true;
        long id = 0;

        if (sId != null) {
            id = Long.parseLong(sId);
            newRecord = false;
        }

        UNREDDStatsDef unreddStatsDef;
        if (newRecord)
            unreddStatsDef = new UNREDDStatsDef();
        else
            unreddStatsDef = new UNREDDStatsDef(manager.getResource(id, false));

        // remove all previous layers
        List<String> toBeRemoved = unreddStatsDef.getReverseAttributes(UNREDDStatsDef.ReverseAttributes.LAYER.getName());
        for (String attrName : toBeRemoved) {
            unreddStatsDef.removeReverseAttribute(UNREDDStatsDef.ReverseAttributes.LAYER, attrName);
        }
        
        // add new layers
        if (layers != null)
            unreddStatsDef.addReverseAttribute(UNREDDStatsDef.ReverseAttributes.LAYER, layers);
        
        unreddStatsDef.setAttribute(UNREDDStatsDef.Attributes.ZONALLAYER, zonalLayer);
        
        RESTResource statsDefRestResource = unreddStatsDef.createRESTResource();

        if (!newRecord)
        {
            // don't set name - name can't be modified on the web interface
            statsDefRestResource.setCategory(null); // Category needs to be null for updates
            
            manager.updateResource(id, statsDefRestResource);
            manager.setData(id, xml);
        } else {
            statsDefRestResource.setName(statsDefName);

            RESTStoredData rsd = new RESTStoredData();
            rsd.setData(xml);
            statsDefRestResource.setStore(rsd);
            id = manager.insert(statsDefRestResource);
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("StatsDefShow?name=" + statsDefName);
        rd.forward(request, response);
    }
}
