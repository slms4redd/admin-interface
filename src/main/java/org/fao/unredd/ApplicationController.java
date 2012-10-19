/*
 * nfms4redd Portal Interface - http://nfms4redd.org/
 *
 * (C) 2012, FAO Forestry Department (http://www.fao.org/forestry/)
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation;
 * version 3.0 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 */
package org.fao.unredd;

//import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.UNREDDGeostoreManager;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import org.apache.log4j.Logger;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ApplicationController {
    
	private static Logger logger = Logger.getLogger(ApplicationController.class);
	
    //@Autowired
    //GeoStoreClient client;
    
    UNREDDGeostoreManager geostore = null;
    
    @RequestMapping(value="/feedbacks", method = RequestMethod.GET)
    public void feedbacks(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GeoStoreClient geoStoreClient = new GeoStoreClient("http://localhost:9191/geostore", "admin", "admin");
        
        geoStoreClient.getResources();
                
        //System.out.println(geostoreResponse.getResponseBody());
    }
    
    
    
    
    
    
    @RequestMapping(value="/feedbacks_", method = RequestMethod.GET)
    public void feedbacks_(HttpServletRequest request, HttpServletResponse response) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
		HttpClient httpClient = new HttpClient();
        
        httpClient.getState().setCredentials(
            new AuthScope("localhost", 9191, AuthScope.ANY_REALM),
            new UsernamePasswordCredentials("admin", "admin")
        );

        HttpMethod method = new GetMethod("http://localhost:9292/geostore/rest/resources");
        method.setDoAuthentication(true);
        method.addRequestHeader("accept", "application/json");
        
		try {
			// Execute the method
			httpClient.executeMethod(method);

			// Set the content type, as it comes from the server
			Header[] headers = method.getResponseHeaders();
            
			for (Header header : headers) {
				if ("Content-Type".equalsIgnoreCase(header.getName())) {
					response.setContentType(header.getValue());
				}
			}

			// Write the body, flush and close
			writer.write(method.getResponseBodyAsString());
			writer.flush();
			writer.close();
		} catch (HttpException e) {
			writer.write(e.toString());
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			writer.write(e.toString());
			throw e;
		}
    }
       
}
