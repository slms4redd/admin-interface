package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.unredd.services.data.CategoryPOJO;
import it.geosolutions.unredd.services.data.ModelDomainNames;
import it.geosolutions.unredd.services.data.ResourcePOJO;
import it.geosolutions.unredd.services.data.utils.ResourceDecorator;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.fao.unredd.Util;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class LayerUpdatePublish extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1615840247422612058L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws JAXBException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long   layerUpdateId = Long.parseLong(request.getParameter("layerUpdateId"));
        String action  = request.getParameter("action");
        
        ResourcePOJO unreddLayerUpdateRes = manager.getResource(layerUpdateId, false);
        if(!CategoryPOJO.LAYERUPDATE.equals(unreddLayerUpdateRes.getCategory())){
            throw new IOException("The requested resource with LayerUpdate id '" + layerUpdateId + "' is not a LayerUpdate resource as expected... this should never happen...");
        }
        ResourceDecorator unreddLayerUpdate = new ResourceDecorator(unreddLayerUpdateRes);  
        
        String layerName = unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_LAYER);
        
        ResourcePOJO unreddLayerRes;
        try {
            unreddLayerRes = manager.searchLayer(layerName);
            if(!CategoryPOJO.LAYER.equals(unreddLayerRes.getCategory())){
                throw new IOException("A resource with name = '" + layerName + "' has been found but it is not a Layer resource... this should never happen...");
            }
        } catch (JAXBException e) {
            throw new IOException(e.getCause());
        }
        ResourceDecorator unreddLayer = new ResourceDecorator(unreddLayerRes);
        
        String format    = unreddLayer.getFirstAttributeValue(ModelDomainNames.LAYER_LAYERTYPE);
        String year      = unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_YEAR);
        String month     = unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_MONTH);
        String day       = unreddLayerUpdate.getFirstAttributeValue(ModelDomainNames.LAYERUPDATE_DAY);
        
        boolean publish   = "publish".equals(action);
        //boolean unpublish = "republish".equals(action); // unpublish action not yet implemented
        
        if(!unreddLayerUpdate.updateTextAttribute(ModelDomainNames.LAYERUPDATE_PUBLISHED, "" + (publish))){
        	unreddLayerUpdate.addTextAttribute(ModelDomainNames.LAYERUPDATE_PUBLISHED, "" + (publish));
        }
        
        unreddLayerUpdate.setCategory(null); // Category needs to be null for updates
        
        String xml;
        if (publish) { // only publish action is implemented in GeoBatch for now
            xml = getPublishXml(layerName, format, year, month, day);
            Util.saveReprocessFile(getServletContext(), xml, Util.getGeostoreFlowSaveDir(getServletContext()) + File.separator + "publish");
            manager.updateResource(layerUpdateId, unreddLayerUpdateRes);
            
            response.sendRedirect("LayerUpdateList?layer=" + layerName);
        }
                
    }

    private String getPublishXml(String layerName, String format, String year, String month, String day) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        xml.append("<PublishLayer>\n");
        xml.append("\t<layername>").append(layerName).append("</layername>\n");
        xml.append("\t<format>").append(format.toLowerCase()).append("</format>\n");
        if (day != null)   xml.append("\t<day>").append(day).append("</day>\n");
        if (month != null) xml.append("\t<month>").append(month).append("</month>\n");
        xml.append("\t<year>").append(year).append("</year>\n");
        xml.append("</PublishLayer>");
        
        return xml.toString();
    }
}
