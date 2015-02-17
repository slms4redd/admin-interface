/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ModelDomainNames;
import it.geosolutions.unredd.services.data.ResourcePOJO;
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
public class LayerEdit extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4651349210114363589L;

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
        
        String layerType            = request.getParameter(ModelDomainNames.LAYER_LAYERTYPE.getName());
        String origDataDestPath     = request.getParameter(ModelDomainNames.LAYER_ORIGDATADESTPATH.getName());
        String mosaicPath           = request.getParameter(ModelDomainNames.LAYER_MOSAICPATH.getName());
        String dissMosaicPath       = request.getParameter(ModelDomainNames.LAYER_DISSMOSAICPATH.getName());
        String rasterAttribName     = request.getParameter(ModelDomainNames.LAYER_RASTERATTRIBNAME.getName());
        String rasterCqlFilter      = request.getParameter(ModelDomainNames.LAYER_RASTERCQLFILTER.getName());
        String rasterNoData         = request.getParameter(ModelDomainNames.LAYER_RASTERNODATA.getName());
        String rasterPixelHeight    = request.getParameter(ModelDomainNames.LAYER_RASTERPIXELHEIGHT.getName());
        String rasterPixelWidth     = request.getParameter(ModelDomainNames.LAYER_RASTERPIXELWIDTH.getName());
        String rasterX0             = request.getParameter(ModelDomainNames.LAYER_RASTERX0.getName());
        String rasterX1             = request.getParameter(ModelDomainNames.LAYER_RASTERX1.getName());
        String rasterY0             = request.getParameter(ModelDomainNames.LAYER_RASTERY0.getName());
        String rasterY1             = request.getParameter(ModelDomainNames.LAYER_RASTERY1.getName());
        
        String xml                  = request.getParameter("xml");

        ResourcePOJO unreddLayerRes = manager.getResource(id, false);
        if(!CategoryPOJO.LAYER.equals(unreddLayerRes.getCategory())){
            throw new IOException("The requested resource with Layer id '" + id + "' is not a Layer resource as expected... this should never happen...");
        }
        ResourceDecorator unreddLayer = new ResourceDecorator(unreddLayerRes); 
        
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_LAYERTYPE, layerType); // raster | vector
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_ORIGDATADESTPATH, origDataDestPath); // relative path where the geotiff has to be copied in
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_MOSAICPATH, mosaicPath); // relative path where the orig/data has to be moved in
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_DISSMOSAICPATH, dissMosaicPath);

        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERPIXELHEIGHT, rasterPixelHeight);
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERPIXELWIDTH, rasterPixelWidth);
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERX0, rasterX0);
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERX1, rasterX1);
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERY0, rasterY0);
        unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERY1, rasterY1);
        
        if ("vector".equals(layerType)) {
            // attributes for vector layers: rasterization
            unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERATTRIBNAME, rasterAttribName); // name of the numeric feature attribute to set in the raster
            unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERCQLFILTER, rasterCqlFilter); // optional CQL filter used to filter the features to be reported on the raster
            unreddLayer.updateTextAttribute(ModelDomainNames.LAYER_RASTERNODATA, rasterNoData); // nodata value for the raster
        }

        unreddLayer.setCategory(null); // Category needs to be null for updates
        unreddLayerRes.setId(null);
        manager.updateResource(id, unreddLayerRes);
        
        manager.setData(id, xml);
        
        RequestDispatcher rd = request.getRequestDispatcher("LayerShow?id=" + id);
        rd.forward(request, response);
    }
}
