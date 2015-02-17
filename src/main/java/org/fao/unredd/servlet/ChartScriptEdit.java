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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class ChartScriptEdit extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -5173976375446651L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sId         = request.getParameter("id");
        String name        = request.getParameter("name");
        String scriptPath  = request.getParameter(ModelDomainNames.ATTRIBUTES_SCRIPTPATH.getName());
        String statsDefs[] = request.getParameterValues(ModelDomainNames.ATTRIBUTES_STATSDEF.getName());
        
        boolean newRecord = true;
        long id = 0;
        
        if (sId != null) {
            id = Long.parseLong(sId);
            newRecord = false;
        }
        
        ResourcePOJO unreddChartScriptRes = null;
        if (!newRecord){
            unreddChartScriptRes = manager.getResource(id, false);
        }
        else{
            unreddChartScriptRes = new ResourcePOJO();
            unreddChartScriptRes.setCategory(CategoryPOJO.CHARTSCRIPT);
        }
        ResourceDecorator unreddChartScript = new ResourceDecorator(unreddChartScriptRes); 
        
        // remove all previous statdefs
        boolean toBeRemoved = unreddChartScript.deleteAttributes(ModelDomainNames.ATTRIBUTES_STATSDEF);

        // add new statdefs
        if (statsDefs != null){
            unreddChartScript.addTextAttributes(ModelDomainNames.ATTRIBUTES_STATSDEF, statsDefs);
        }
        unreddChartScript.addTextAttribute(ModelDomainNames.ATTRIBUTES_SCRIPTPATH, scriptPath);
        
        if (!newRecord) {
            unreddChartScriptRes.setCategory(null); // Category needs to be null for updates
            manager.updateResource(id, unreddChartScriptRes);
        } else {
            unreddChartScriptRes.setName(name); // name can't be modified on the web interface
            id = manager.insert(unreddChartScriptRes);
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("ChartScriptShow?id=" + id);
        rd.forward(request, response);
    }
}
