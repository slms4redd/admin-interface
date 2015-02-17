package org.fao.unredd.servlet;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import it.geosolutions.unredd.services.data.ResourcePOJO;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
public class ChartDataList extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1924639843118908694L;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get HTTP parameters
        String chartScriptName = request.getParameter("chart_script");

        try {
            List<ResourcePOJO> relatedStatsDef = manager.searchChartDataByChartScript(chartScriptName);
            request.setAttribute("chartData", relatedStatsDef);
            request.setAttribute("geostoreURL", Util.getGeostoreRestURL(getServletContext()));

            RequestDispatcher rd = request.getRequestDispatcher("chart-data-list.jsp");
            rd.forward(request, response);
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
