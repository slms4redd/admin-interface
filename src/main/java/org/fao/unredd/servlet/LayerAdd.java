package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.geostore.services.rest.model.RESTStoredData;
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
public class LayerAdd extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String layerName = request.getParameter("LayerName");
        String layerType = request.getParameter("LayerType");
        
        String origDataDestPath     = request.getParameter(UNREDDLayer.Attributes.ORIGDATADESTPATH.getName());
        String mosaicPath           = request.getParameter(UNREDDLayer.Attributes.MOSAICPATH.getName());
        String dissMosaicPath       = request.getParameter(UNREDDLayer.Attributes.DISSMOSAICPATH.getName());
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

        UNREDDLayer unreddLayer = new UNREDDLayer();

        unreddLayer.setAttribute(UNREDDLayer.Attributes.LAYERTYPE, layerType); // “raster” | “vector”
        unreddLayer.setAttribute(UNREDDLayer.Attributes.MOSAICPATH, mosaicPath);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.DISSMOSAICPATH, dissMosaicPath);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.ORIGDATADESTPATH, origDataDestPath); // relative path where the orig/data has to be moved in

        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELHEIGHT, rasterPixelHeight);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELWIDTH, rasterPixelWidth);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX0, rasterX0);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX1, rasterX1);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY0, rasterY0);
        unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY1, rasterY1);
        
        // attributes for vector layers: rasterization
        if ("vector".equals(layerType))
        {
            unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERATTRIBNAME, rasterAttribName); // name of the numeric feature attribute to set in the raster
            unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERCQLFILTER, rasterCqlFilter); // optional CQL filter used to filter the features to be reported on the raster
            unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERNODATA, rasterNoData); // nodata value for the raster
        }
        RESTResource layerRestResource = unreddLayer.createRESTResource();
        layerRestResource.setName(layerName);

        //long id = client.insert(layerRestResource);
        RESTStoredData rsd = new RESTStoredData();
        rsd.setData(xml);
        layerRestResource.setStore(rsd);
        long id = client.insert(layerRestResource);

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
