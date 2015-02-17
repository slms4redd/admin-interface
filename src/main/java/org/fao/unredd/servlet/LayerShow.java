package org.fao.unredd.servlet;

import it.geosolutions.unredd.services.data.ResourcePOJO;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.fao.unredd.LayerManager;


/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class LayerShow extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4236414462478817607L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id = new Integer(request.getParameter("id"));

        
        ResourcePOJO res = null;
//        try {
            res = manager.getResource(id, false);
//        } catch (JAXBException e) {
//            throw new IOException(e.getMessage());
//        }
        LayerManager layerBean = new LayerManager(res);
        
        try {
            
            request.setAttribute("layer", layerBean);
            
            String layerName = layerBean.getName();
            
            List<ResourcePOJO> relatedStatsDefs = manager.searchStatsDefByLayer(layerName);
            request.setAttribute("statsDefs", relatedStatsDefs);
            
            List<ResourcePOJO> relatedLayerUpdates = manager.searchLayerUpdatesByLayerName(layerName);
            request.setAttribute("layerUpdates", relatedLayerUpdates);
            
            String data = manager.getData(id, MediaType.WILDCARD_TYPE.toString());
            layerBean.setData(data);
            
            request.setAttribute("storedData", data);
            
        } catch (Exception ex) {
            Logger.getLogger(LayerShow.class.getName()).log(Level.SEVERE, null, ex);
        }
        String outputPage = getServletConfig().getInitParameter("outputPage");
        RequestDispatcher rd = request.getRequestDispatcher(outputPage);
        rd.forward(request, response);
    }
}
