package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.services.UNREDDPersistenceFacade;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

import org.fao.unredd.LayerBean;
import org.fao.unredd.Util;
import org.springframework.beans.factory.annotation.Autowired;


/**
 *
 * @author sgiaccio
 */
public class LayerShow extends HttpServlet {

    @Autowired
    private UNREDDPersistenceFacade manager;
    
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
        
        String url  = Util.getGeostoreRestURL(getServletContext());
        String user = Util.getGeostoreUsername(getServletContext());
        String pwd  = Util.getGeostorePassword(getServletContext());

        LayerBean layerBean = new LayerBean();
        layerBean.setGeostoreUrl(url);
        layerBean.setGeostoreUserId(user);
        layerBean.setGeostorePassword(pwd);
        
        try {
            layerBean.initWithId(id);
            request.setAttribute("layer", layerBean);
            
            String layerName = layerBean.getName();
            
            List<Resource> relatedStatsDefs = manager.searchStatsDefByLayer(layerName);
            request.setAttribute("statsDefs", relatedStatsDefs);
            
            List<Resource> relatedLayerUpdates = manager.searchLayerUpdatesByLayerName(layerName);
            request.setAttribute("layerUpdates", relatedLayerUpdates);
            
            String data = manager.getData(id, MediaType.WILDCARD_TYPE);

            request.setAttribute("storedData", data);
        } catch (Exception ex) {
            Logger.getLogger(LayerShow.class.getName()).log(Level.SEVERE, null, ex);
        }
        String outputPage = getServletConfig().getInitParameter("outputPage");
        RequestDispatcher rd = request.getRequestDispatcher(outputPage);
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
