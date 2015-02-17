package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ModelDomainNames;
import it.geosolutions.unredd.services.data.ResourcePOJO;
import it.geosolutions.unredd.services.data.utils.ResourceDecorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBException;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class StatsDefShow extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1656006682372151931L;

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
                ResourcePOJO res = manager.searchResourceByName(name, CategoryPOJO.STATSDEF);
                if(!CategoryPOJO.STATSDEF.equals(res.getCategory())){
                    throw new IOException("The resource with name: '" + name + "' is not a StatDef resource... This should never happen...");
                }
                
                String data = manager.getData(res.getId(), MediaType.WILDCARD_TYPE.getType());

                request.setAttribute("resource", res);
                

                request.setAttribute("storedData", data);

                ResourceDecorator statsDef = new ResourceDecorator(res);
                String zonalLayer = statsDef.getFirstAttributeValue(ModelDomainNames.ATTRIBUTES_ZONALLAYER);
                request.setAttribute("zonalLayer", zonalLayer);

                List<ResourcePOJO> relatedLayers = new ArrayList<ResourcePOJO>();
                
                List<String> layerNames = statsDef.getAttributeValues(ModelDomainNames.ATTRIBUTES_LAYER);
                for (String layerName : layerNames) {
                    relatedLayers.add(manager.searchLayer(layerName));
                }
                request.setAttribute("relatedLayers", relatedLayers);

                List<ResourcePOJO> relatedChartScripts = manager.searchChartScriptByStatsDef(res.getName());
                request.setAttribute("relatedChartScripts", relatedChartScripts);
            } else {
                request.setAttribute("resource", null);
                request.setAttribute("storedData", "");
            }

            List<ResourcePOJO> layers = manager.getLayers();
            request.setAttribute("layerList", layers);

            String outputPage = getServletConfig().getInitParameter("outputPage");
            RequestDispatcher rd = request.getRequestDispatcher(outputPage);
            
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
