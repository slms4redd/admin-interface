package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.services.UNREDDPersistenceFacade;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class ChartScriptByLayer extends AdminGUIAbstractServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 3127995224796090356L;
    
    private final static Logger LOGGER = Logger.getLogger(AdminGUIAbstractServlet.class); 
    
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String layerName = request.getParameter("name");
        
        Set<Resource> chartScripts = null;
        try {
            chartScripts = searchChartScriptByLayerName(layerName, manager);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        request.setAttribute("chartScripts", chartScripts);
    }

    public Set<Resource> searchChartScriptByLayerName(String layername, UNREDDPersistenceFacade geostore) throws Exception {
        List<Resource> relatedStatsDef = null;
        try {
            relatedStatsDef = geostore.searchStatsDefByLayer(layername);
        } catch (Exception e) {
            LOGGER.debug("Parameter : [layername=" + layername + "]");
            throw new IOException("Error while searching for StatsDef", e);
        }

        Set<Resource> chartScript = new HashSet<Resource>();

        try {
            for (Resource statsDef : relatedStatsDef) {
                List<Resource> localChartScript = geostore.searchChartScriptByStatsDef(statsDef.getName());
                chartScript.addAll(localChartScript);
            }
        } catch (Exception e) {
            throw new IOException("Error while searching for ChartScript", e);
        }
        
        return chartScript;
    }
}
