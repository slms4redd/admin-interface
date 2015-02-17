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
public class LayerAdd extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 4460201951432153117L;

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

        ResourcePOJO unreddLayerRes = new ResourcePOJO();
        unreddLayerRes.setCategory(CategoryPOJO.LAYER);
        ResourceDecorator unreddLayer = new ResourceDecorator(unreddLayerRes); 
        
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_LAYERTYPE, layerType); // raster | vector
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_MOSAICPATH, mosaicPath);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_DISSMOSAICPATH, dissMosaicPath);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_ORIGDATADESTPATH, origDataDestPath); // relative path where the orig/data has to be moved in

        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERPIXELHEIGHT, rasterPixelHeight);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERPIXELWIDTH, rasterPixelWidth);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERX0, rasterX0);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERX1, rasterX1);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERY0, rasterY0);
        unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERY1, rasterY1);
        
        // attributes for vector layers: rasterization
        if ("vector".equals(layerType))
        {
            unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERATTRIBNAME, rasterAttribName); // name of the numeric feature attribute to set in the raster
            unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERCQLFILTER, rasterCqlFilter); // optional CQL filter used to filter the features to be reported on the raster
            unreddLayer.addTextAttribute(ModelDomainNames.LAYER_RASTERNODATA, rasterNoData); // nodata value for the raster
        }
        unreddLayer.setName(layerName);

        //long id = client.insert(layerRestResource);
        StoredDataPOJO rsd = new StoredDataPOJO();
        rsd.setData(xml);
        unreddLayer.setData(rsd);
        long id = manager.insert(unreddLayerRes);

        RequestDispatcher rd = request.getRequestDispatcher("LayerShow?id=" + id);
        rd.forward(request, response);
    }
}
