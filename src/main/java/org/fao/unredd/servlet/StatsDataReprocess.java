/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

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
public class StatsDataReprocess extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long   layerId   = Long.parseLong(request.getParameter("layerId"));
        String layerName = request.getParameter("name");
        String year      = request.getParameter("year");
        String month     = request.getParameter("month");
        
        Util.saveReprocessFile(getServletContext(), getXml(layerName, year, month), Util.getGeostoreFlowSaveDir(getServletContext()));
        
        RequestDispatcher rd = request.getRequestDispatcher("LayerUpdateList?id=" + layerId);
        rd.forward(request, response);
    }

    /*
    <ReprocessStats>
       <statsName>stats1</statsName>
       <statsName>stats2</statsName>
    </ReprocessStats>
    */
    private String getXml(String layerName, String year, String month) {
        StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\r");
        xml.append("<ReprocessLayer>\r");
        xml.append("\t<layerName>").append(layerName).append("</layerName>\r");
        xml.append("\t<month>").append(month).append("</month>\r");
        xml.append("\t<year>").append(year).append("</year>\r");
        xml.append("</ReprocessLayer>\r");
        
        return xml.toString();
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
