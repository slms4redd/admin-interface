package org.fao.unredd.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author DamianoG
 *
 */
public class LayerMenuBuilder extends AdminGUIAbstractServlet {

    private static final long serialVersionUID = 1879798L;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String outputPage = getServletConfig().getInitParameter("outputPage");
        
        RequestDispatcher rd = request.getRequestDispatcher(outputPage);
        rd.forward(request, response);
        
    }

}
