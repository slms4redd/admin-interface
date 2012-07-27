/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd.servlet;

import it.geosolutions.geostore.core.model.Resource;
import java.util.List;
import it.geosolutions.unredd.geostore.model.UNREDDStatsDef;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.*;

/**
 *
 * @author sgiaccio
 */
public class StatsDefEditTest {
    private final static Logger LOGGER = LoggerFactory.getLogger(StatsDefEditTest.class);

    GeoStoreClient client;
    private long id;
    
    public StatsDefEditTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
//        client.deleteResource(id);
//        System.out.println("Resource[id = " + id + "] deleted.");
    }

    /**
     * Test of processRequest method, of class StatsDefEdit.
     */
    @Test
    public void testProcessRequest() throws Exception {
        System.out.println("processRequest");
                
        client = new GeoStoreClient();
        client.setGeostoreRestUrl("http://localhost:9191/geostore/rest");
        client.setUsername("admin");
        client.setPassword("admin");

        long id;

        // Insert a new unreddStatsDef
        {
            UNREDDStatsDef unreddStatsDef = new UNREDDStatsDef();
            unreddStatsDef.addReverseAttribute(UNREDDStatsDef.ReverseAttributes.LAYER, "dummy_layer");
            RESTResource statsDefRestResource = unreddStatsDef.createRESTResource();
            statsDefRestResource.setName("dummy_statsdef");
        
            //RESTStoredData rsd = new RESTStoredData();
            //rsd.setData("<xml />");
            //statsDefRestResource.setStore(rsd);

            id = client.insert(statsDefRestResource);
            LOGGER.info("Inserted resource id = " + id);
        }

        // Reload resource as UNREDDStatsDef and update
        {
            Resource res = client.getResource(id);
            UNREDDStatsDef loaded = new UNREDDStatsDef(res);
            List<String> layers = loaded.getReverseAttributesInternal(UNREDDStatsDef.ReverseAttributes.LAYER);

            // Test reloaded attrs
            assertEquals(1, layers.size());

            // Delete layer reverse attributes from unreddStatsDef
            List<String> toBeRemoved = loaded.getReverseAttributes(UNREDDStatsDef.ReverseAttributes.LAYER.getName());
            for (String attrName : toBeRemoved) {
                loaded.removeReverseAttribute(UNREDDStatsDef.ReverseAttributes.LAYER, attrName);
            }

            // Test updated attrs
            layers = loaded.getReverseAttributesInternal(UNREDDStatsDef.ReverseAttributes.LAYER);
            assertTrue(layers.isEmpty());

            RESTResource toBeUpdated = loaded.createRESTResource(); // only creates category and attributes
            toBeUpdated.setName(res.getName());
//            loaded.setCategory(null); // Category needs to be null for updates

            client.updateResource(id, toBeUpdated);
            //client.setData(id, "<xml />");
        }
        
        // Reload the resource
        {
            UNREDDStatsDef loaded = new UNREDDStatsDef(client.getResource(id));
            List<String> layers = loaded.getReverseAttributes(UNREDDStatsDef.ReverseAttributes.LAYER.getName());

            // Test reloaded attrs
            for (String lname : layers) {
                LOGGER.info("layer -> "+lname);
            }

            assertTrue(layers.isEmpty());
        }
    }

}
