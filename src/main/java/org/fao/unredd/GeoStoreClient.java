/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.geostore.UNREDDGeostoreManager;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.xml.bind.JAXBException;

/**
 *
 * @author sgiaccio
 */
public class GeoStoreClient {
    private String baseUrl, username, password;
    
    public GeoStoreClient(String baseUrl, String username, String password) {
        this.baseUrl = baseUrl;
        this.username = username;
        this.password = password;
    }
    
    
    public void getFeedbacks() throws Exception {
        UNREDDGeostoreManager manager = new UNREDDGeostoreManager("", "admin", "admin");
        try {
            List<Resource> resources = manager.getFeedbacks();
            for (Resource res : resources) {
                getJson(res);
            }
        } catch (UnsupportedEncodingException ex) {
            throw new Exception(ex);
        } catch (JAXBException ex) {
            throw new Exception(ex);
        }
    }
    
    private String getJson(Resource resource) {
        
        
        return "";
    }
    
    public void getResources() {
        try {

            Client client = Client.create();

            WebResource webResource = client.resource(baseUrl + "/rest/resources");

            ClientResponse response = webResource.accept("application/json").
                    get(ClientResponse.class);

            if (response.getStatus() != 200) {
               throw new RuntimeException("Failed : HTTP error code : "
                       + response.getStatus());
            }

            String output = response.getEntity(String.class);

            System.out.println("Output from Server .... \n");
            System.out.println(output);

          } catch (Exception e) {
            e.printStackTrace();
          }
    }
    
    /*
    public GeoStoreResponse sendRequest(String resourceUrl) throws IOException {
		HttpClient httpClient = new HttpClient();
        
        // Setup authentication
        httpClient.getState().setCredentials(
            new AuthScope(AuthScope.ANY),
            new UsernamePasswordCredentials(username, password)
        );

        HttpMethod method = new GetMethod(baseUrl + ":" + port + "/" + resourceUrl);
        method.setDoAuthentication(true);
        method.addRequestHeader("accept", "application/json"); // this is meant to work as a proxy between geostore and the web client, so json.
        
        // Execute the method
        httpClient.executeMethod(method);

        // Create the GeoStoreResponse object
        GeoStoreResponse geoStoreResponse = new GeoStoreResponse();
        
        // Set response body, status code and headers
		geoStoreResponse.setResponseBody(method.getResponseBodyAsString());
        geoStoreResponse.setStatusCode(method.getStatusCode());
        
        // put headers in a HashMap
        HashMap<String, String> headers = new HashMap<String, String>();
        for (Header header : method.getResponseHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        geoStoreResponse.setResponseHeaders(headers);
        
        return geoStoreResponse;
    }
    */
    
    /*
    public class GeoStoreResponse {
        private String responseBody;
        private int statusCode;
        private HashMap<String, String> responseHeaders;

        public String getResponseBody() {
            return responseBody;
        }

        public void setResponseBody(String responseBody) {
            this.responseBody = responseBody;
        }

        public int getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public HashMap<String, String> getResponseHeaders() {
            return responseHeaders;
        }

        public void setResponseHeaders(HashMap<String, String> responseHeaders) {
            this.responseHeaders = responseHeaders;
        }
    }
    */
}
