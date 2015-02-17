/*
 *  Copyright (C) 2007 - 2011 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 * 
 *  GPLv3 + Classpath exception
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fao.unredd.servlet;

import it.geosolutions.unredd.services.interfaces.UNREDDPersistenceFacade;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * All the servlets should extends this abstract class since it contains the reference to the UNREDDPersistenceFacade manager in order to intercat with the underlying persistence system
 * 
 * @author DamianoG
 *
 */
public abstract class AdminGUIAbstractServlet extends HttpServlet{

    /**
     * 
     */
    private static final long serialVersionUID = -787406658990108978L;
    
    private final static Logger LOGGER = Logger.getLogger(AdminGUIAbstractServlet.class);
    
    @Autowired()
    protected UNREDDPersistenceFacade manager;
    
    /**
     * The call to the {@link SpringBeanAutowiringSupport#processInjectionBasedOnServletContext(Object, javax.servlet.ServletContext)} method is mandatory for each servlet
     * since we want to use the Spring Autowiring system into the Servlets that, of course, aren't declared in the Spring ApplicationContext  
     * 
     * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
     */
    @Override
    public void init(ServletConfig config) {
        try {
            super.init(config);
        } catch (ServletException e) {
            throw new IllegalStateException("An exception occurred while executing servlet init method... this should never happen...");
        }
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                config.getServletContext());
        LOGGER.info("Loading Servlet '" + this.getServletName() + "'");
    }
    
    /**
     * This abstract method is the only one that all the concrete servlets should implement to handle both GET and POST requests. 
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected abstract void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
    
    
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
}
