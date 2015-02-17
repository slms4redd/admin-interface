/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fao.unredd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author sgiaccio
 */
public class Util {
    public static String getGeostoreRestURL(ServletContext servletContext) {
        ApplicationContext beanFactory =
            WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);

        String url =
             (String)beanFactory
                  .getBean("geostoreRestUrl", String.class);
        
        return url;
    }

    public static String getGeostoreUsername(ServletContext servletContext) {
        ApplicationContext beanFactory =
            WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);

        String url =
             (String)beanFactory
                  .getBean("geostoreUsername", String.class);
        
        return url;
    }
    
    public static String getGeostorePassword(ServletContext servletContext) {
        ApplicationContext beanFactory =
            WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);

        String url =
             (String)beanFactory
                  .getBean("geostorePassword", String.class);
        
        return url;
    }
    
    public static void saveReprocessFile(ServletContext servletContext, String xml, String baseDir) throws IOException {
        BufferedWriter out = null;
        
        try {
            File f = File.createTempFile("flow_", ".xml", new File(baseDir));
            FileWriter fstream = new FileWriter(f);
            out = new BufferedWriter(fstream);
            out.write(xml);
        } catch (IOException e) {
            throw(e);
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
    
    public static String getGeostoreFlowSaveDir(ServletContext servletContext) {
        ApplicationContext beanFactory =
            WebApplicationContextUtils
                .getRequiredWebApplicationContext(servletContext);

        Configure conf =
             (Configure)beanFactory
                  .getBean("configure", Configure.class);
        
        return conf.getGeobatchFlowSaveDir();
    }
    
//    public static GeoStoreClient getGeostoreClient(ServletContext servletContext) {
//        ApplicationContext beanFactory =
//            WebApplicationContextUtils
//                .getRequiredWebApplicationContext(servletContext);
//
//        GeoStoreClient client =
//             (GeoStoreClient)beanFactory
//                  .getBean("geoStoreClient", GeoStoreClient.class);
//        
//        return client;
//    }

//    public static UNREDDPersistenceFacade getGeostoreManager(ServletContext servletContext) {
//        ApplicationContext beanFactory =
//            WebApplicationContextUtils
//                .getRequiredWebApplicationContext(servletContext);
//
//        UNREDDGeostoreManager manager =
//             (UNREDDGeostoreManager)beanFactory
//                  .getBean("unreddGeostoreManager", UNREDDGeostoreManager.class);
//        
//        return manager;
//    }
    
//    /*********
//     * Returns the list of all layers
//     * @return
//     */
//    public ArrayList<Resource> getStatsDefs() throws UnsupportedEncodingException, JAXBException {
//        SearchFilter filter = new CategoryFilter(UNREDDCategories.STATSDEF.getName(), SearchOperator.EQUAL_TO);
//        //ShortResourceList list = client.searchResources(filter);
//
//        //return getResourceList(list);
//        return null;
//    }
//    
//    /*********
//     * Converts a ShortResourceList to a list of Resource objects
//     * @return
//     */
//    private ArrayList getResourceList(ShortResourceList list) {
//        ArrayList resources = new ArrayList();
//        if (list.getList() != null && !list.getList().isEmpty()) {
//            for (ShortResource shortResource : list.getList()) {
//                //Resource resource = client.getResource(shortResource.getId());
//                //resources.add(resource);
//            }
//        } else {
//            System.out.println("No resource found");
//        }
//        
//        return resources;
//    }
}
