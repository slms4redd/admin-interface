/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.unredd.geostore.model.UNREDDLayer;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.fao.unredd.Util;

/**
 *
 * @author sgiaccio
 */
public class LayerEdit extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        long id                     = Long.parseLong(request.getParameter("id"));
        
        String layerType            = request.getParameter(UNREDDLayer.Attributes.LAYERTYPE.getName());
        String origDataDestPath     = request.getParameter(UNREDDLayer.Attributes.ORIGDATADESTPATH.getName());
        String mosaicPath           = request.getParameter(UNREDDLayer.Attributes.MOSAICPATH.getName());
        String rasterAttribName     = request.getParameter(UNREDDLayer.Attributes.RASTERATTRIBNAME.getName());
        String rasterCqlFilter      = request.getParameter(UNREDDLayer.Attributes.RASTERCQLFILTER.getName());
        String rasterNoData         = request.getParameter(UNREDDLayer.Attributes.RASTERNODATA.getName());
        String rasterPixelHeight    = request.getParameter(UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName());
        String rasterPixelWidth     = request.getParameter(UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName());
        String rasterX0             = request.getParameter(UNREDDLayer.Attributes.RASTERX0.getName());
        String rasterX1             = request.getParameter(UNREDDLayer.Attributes.RASTERX1.getName());
        String rasterY0             = request.getParameter(UNREDDLayer.Attributes.RASTERY0.getName());
        String rasterY1             = request.getParameter(UNREDDLayer.Attributes.RASTERY1.getName());
        
        String xml                  = request.getParameter("xml");
        
        GeoStoreClient client = Util.getGeostoreClient(getServletContext());

        Resource res = client.getResource(id);
        UNREDDLayer unreddLayer = new UNREDDLayer(res);

        unreddLayer.setAttribute(UNREDDLayer.Attributes.LAYERTYPE, layerType); // “raster” | “vector”
        unreddLayer.setAttribute(UNREDDLayer.Attributes.ORIGDATADESTPATH, origDataDestPath); // relative path where the geotiff has to be copied in
        unreddLayer.setAttribute(UNREDDLayer.Attributes.MOSAICPATH, mosaicPath); // relative path where the orig/data has to be moved in
        
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELHEIGHT, rasterPixelHeight);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELWIDTH, rasterPixelWidth);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX0, rasterX0);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX1, rasterX1);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY0, rasterY0);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY1, rasterY1);
        
        if ("vector".equals(layerType)) {
            // attributes for vector layers: rasterization
            unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERATTRIBNAME, rasterAttribName); // name of the numeric feature attribute to set in the raster
            unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERCQLFILTER, rasterCqlFilter); // optional CQL filter used to filter the features to be reported on the raster
            unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERNODATA, rasterNoData); // nodata value for the raster
        }

        RESTResource resource = unreddLayer.createRESTResource();

        resource.setCategory(null); // Category needs to be null for updates
        client.updateResource(id, resource);
        
        client.setData(id, xml);
        
        RequestDispatcher rd = request.getRequestDispatcher("LayerShow?id=" + id);
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
