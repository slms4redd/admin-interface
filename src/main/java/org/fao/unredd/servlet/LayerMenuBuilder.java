package org.fao.unredd.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * This controller is responsible for the creation/update of the layer.json. 
 * the layer.json file is a json file which is parsed from the portal to build the layers menu.
 * 
 * GET requests accept one request parameter called <b>operation</b>.
 * <ol>
 * <li>operation = null</li> returns the section of the LayerMenuBuilder in the context of the admin app. this is called by the link on the main menu</li>
 * <li>operation = 'LOAD' (case insensitive) returns the current layer.json the user wants to modify.<br>It is called using an AJAX request and the result is added as startval (https://github.com/jdorn/json-editor#options) in the json-editor.</li>
 * </ol>
 * 
 * POST requests search for a valid json in the body to replace the previous one.<br>Please note that the former layer.json is always backed up with the name layers.jsonbkp
 * 
 * @author DamianoG
 *
 */
public class LayerMenuBuilder extends AdminGUIAbstractServlet {

    private static final long serialVersionUID = 1879798L;

    private final static Logger LOGGER = Logger.getLogger(LayerMenuBuilder.class);

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String operation = request.getParameter("op");
        LOGGER.info("GET request with operation = '" + operation + "'");
        if("LOAD".equalsIgnoreCase(operation)){
            
            OutputStream os = null;
            InputStream is = null;
            try {
                os = response.getOutputStream();
                LOGGER.debug("trying to load the properties file");
                PropertiesLoader pl = new PropertiesLoader();
                LOGGER.debug("trying to load the layer.json as input stream");
                is = pl.getLayersJsonInputStream();
                LOGGER.debug("copy in the servlet's output stream");
                IOUtils.copy(is, os);
                os.flush();
                response.setContentType("application/json");
                LOGGER.info("layer.json successfully loaded!");
            } catch (IOException e) {
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e1) {
                        is = null;
                    }
                }
                LOGGER.error("Problems occurred while loading layer.json...");
            }
            finally{
                if(is != null){
                    is.close();
                }
            }
        }else if(operation == null){
            String outputPage = getServletConfig().getInitParameter("outputPage");
            RequestDispatcher rd = request.getRequestDispatcher(outputPage);
            rd.forward(request, response);
        }else{
            LOGGER.error("operation '" + operation + "' is not a valid...");
            throw new ServletException("operation '" + operation + "' is not a valid...");
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        LOGGER.info("POST request");
        String layersjson = IOUtils.toString(request.getReader());
        if(StringUtils.isBlank(layersjson)){
            LOGGER.error("Error a null or empty layer.json has been sent!");
            throw new IOException("Error a null or empty layer.json has been sent!");
        }
        try {
            Gson gson = new Gson();
            gson.fromJson(layersjson, Object.class); 
        } catch(JsonSyntaxException e) { 
            LOGGER.error("Error parsing JSON request string");
            throw new IOException("Error parsing JSON request string");
        }
        LOGGER.debug("trying to load the properties file");
        PropertiesLoader pl = new PropertiesLoader();
        LOGGER.debug("trying to load the layer.json as a file");
        File f = pl.getLayersJson();
        String layersjson_path = f.getAbsolutePath();
        File fout = new File(f.getAbsolutePath() + "bkp");
        fout.createNewFile();
        LOGGER.info("trying to create the layer.json backup...");
        FileUtils.copyFile(f, fout);
        LOGGER.info("...layer.json backup created!");
        try{
            LOGGER.info("trying to create the new version of layer.json");
            LOGGER.debug("delete the old one...");
            FileUtils.forceDelete(f);
            f = new File(layersjson_path);
            LOGGER.debug("create a new file...");
            f.createNewFile();
            LOGGER.debug("write content in the new file...");
            FileUtils.writeStringToFile(f, layersjson);
            LOGGER.info("layer.json successfuly updated!");
        }catch(Exception e){
            LOGGER.error("Something WRONG trying to create the new version of layer.json");
            f = new File(layersjson_path);
            f.createNewFile();
            FileUtils.copyFile(fout, f);
            LOGGER.info("BAckup restored");
        }
    }
    
    class PropertiesLoader{
        
        public final String LAYER_JSON_PATH = "path";
        Configuration prop;
        
        public PropertiesLoader() throws IOException{
            try {
                File f = new File(LayerMenuBuilder.class.getResource("layer_json_path.properties").toURI());
                f.getAbsolutePath();
                prop = new PropertiesConfiguration(f);
            } catch (ConfigurationException | URISyntaxException e) {
                throw new IOException("Unable to load properties file");
            }
        }
        
        public InputStream getLayersJsonInputStream() throws IOException{
            return new FileInputStream(getLayersJson());
        }
        
        public File getLayersJson() throws IOException{
            String path = (String)prop.getProperty(LAYER_JSON_PATH);
            File f = new File(path);
            if(f==null || !f.exists() || f.isDirectory() || !f.canWrite()){
                throw new IOException("the layers.json path is wrong");
            }
            return f;
        }
    }

}
