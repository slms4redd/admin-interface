package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ModelDomainNames;
import it.geosolutions.unredd.services.data.ResourcePOJO;
import it.geosolutions.unredd.services.data.StoredDataPOJO;
import it.geosolutions.unredd.services.data.utils.ResourceDecorator;

import java.io.IOException;

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
        String[] layers     = request.getParameterValues(ModelDomainNames.ATTRIBUTES_LAYER.getName());
        String zonalLayer   = request.getParameter(ModelDomainNames.ATTRIBUTES_ZONALLAYER.getName());
        
        boolean newRecord = true;
        long id = 0;

        if (sId != null) {
            id = Long.parseLong(sId);
            newRecord = false;
        }

        ResourcePOJO unreddStatsDefRes;
        if (newRecord){
            unreddStatsDefRes = new ResourcePOJO();
        }
        else{
            unreddStatsDefRes = manager.getResource(id, false);
            if(!CategoryPOJO.STATSDEF.equals(unreddStatsDefRes.getCategory())){
                throw new IOException("The requested resource with id '" + id + "' is not a StatDef resource as expected... this should never happen...");
            }
        }

        ResourceDecorator unreddStatsDef = new ResourceDecorator(unreddStatsDefRes);
        unreddStatsDef.deleteAttributes(ModelDomainNames.ATTRIBUTES_LAYER);
        
        // add new layers
        if (layers != null){
            unreddStatsDef.addTextAttributes(ModelDomainNames.ATTRIBUTES_LAYER, layers);
        }
        
        // Update (or add if it wasn't already present) the Zonal Layer
        if(!unreddStatsDef.updateTextAttribute(ModelDomainNames.ATTRIBUTES_ZONALLAYER, zonalLayer)){
            unreddStatsDef.addTextAttribute(ModelDomainNames.ATTRIBUTES_ZONALLAYER, zonalLayer);
        }

        if (!newRecord)
        {
            // don't set name - name can't be modified on the web interface
            unreddStatsDefRes.setCategory(null); // Category needs to be null for updates
            
            manager.updateResource(id, unreddStatsDefRes);
            manager.setData(id, xml);
        } else {
            unreddStatsDefRes.setName(statsDefName);
            unreddStatsDefRes.setCategory(CategoryPOJO.STATSDEF);
            StoredDataPOJO rsd = new StoredDataPOJO();
            rsd.setData(xml);
            unreddStatsDefRes.setData(rsd);
            id = manager.insert(unreddStatsDefRes);
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("StatsDefShow?name=" + statsDefName);
        rd.forward(request, response);
    }
}
