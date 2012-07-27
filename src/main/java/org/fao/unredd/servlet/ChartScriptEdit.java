package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.unredd.geostore.model.UNREDDChartScript;
import java.io.IOException;
import java.util.List;
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
public class ChartScriptEdit extends HttpServlet {

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
        String scriptPath  = request.getParameter(UNREDDChartScript.Attributes.SCRIPTPATH.getName());
        String statsDefs[] = request.getParameterValues(UNREDDChartScript.ReverseAttributes.STATSDEF.getName());
        
        /*
        ApplicationContext beanFactory =
            WebApplicationContextUtils
                .getRequiredWebApplicationContext(getServletContext());

        GeoStoreClient client =
             (GeoStoreClient)beanFactory
                  .getBean("geoStoreClient", GeoStoreClient.class);
        */
        
        GeoStoreClient client = Util.getGeostoreClient(getServletContext());
        
        boolean newRecord = true;
        long id = 0;
        
        if (sId != null) {
            id = Long.parseLong(sId);
            newRecord = false;
        }
        
        UNREDDChartScript unreddChartScript;
        if (newRecord)
            unreddChartScript = new UNREDDChartScript();
        else
            unreddChartScript = new UNREDDChartScript(client.getResource(id));

        
        // remove all previous statdefs
        List<String> toBeRemoved = unreddChartScript.getReverseAttributes(UNREDDChartScript.ReverseAttributes.STATSDEF.getName());
        for (String attrName : toBeRemoved) {
            unreddChartScript.removeReverseAttribute(UNREDDChartScript.ReverseAttributes.STATSDEF, attrName);
        }

        // add new statdefs
        if (statsDefs != null) unreddChartScript.addReverseAttribute(UNREDDChartScript.ReverseAttributes.STATSDEF, statsDefs);
            
        unreddChartScript.setAttribute(UNREDDChartScript.Attributes.SCRIPTPATH, scriptPath);
        RESTResource chartScriptRestResource = unreddChartScript.createRESTResource();

        if (!newRecord) {
            chartScriptRestResource.setCategory(null); // Category needs to be null for updates
            client.updateResource(id, chartScriptRestResource);
        } else {
            chartScriptRestResource.setName(name); // name can't be modified on the web interface
            id = client.insert(chartScriptRestResource);
        }
        
        RequestDispatcher rd = request.getRequestDispatcher("ChartScriptShow?id=" + id);
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
