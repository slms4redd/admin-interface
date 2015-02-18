/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.unredd.services.data.ResourcePOJO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

/**
 *
 * @author sgiaccio
 * @author DamianoG (first revision v2.0)
 */
public class StatsDefDelete extends AdminGUIAbstractServlet {
    
    /**
     * 
     */
    private static final long serialVersionUID = 5839145704363160562L;

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, UnsupportedEncodingException {
        String name = request.getParameter("statsDefName");
        List<ResourcePOJO> chartScripts = null;
        try {
            chartScripts = manager.searchChartScriptByStatsDef(name);

            if (chartScripts != null && !chartScripts.isEmpty())
            {
                request.setAttribute("chartScripts", chartScripts);
                RequestDispatcher rd = request.getRequestDispatcher("chart-scripts-to-be-deleted.jsp");
                rd.forward(request, response);
            } else {
                manager.deleteStats(name);
                response.sendRedirect("StatsDefList");
            }
        } catch (JAXBException ex) {
            throw new ServletException(ex);
        }
    }
}
