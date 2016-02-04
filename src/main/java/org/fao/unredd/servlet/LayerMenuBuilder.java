package org.fao.unredd.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

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
        String operation = request.getParameter("op");
        if("LOAD".equalsIgnoreCase(operation)){
            response.setContentType("application/json");      
            OutputStream os = null;
            InputStream is = null;
            try {
                os = response.getOutputStream();
                PropertiesLoader pl = new PropertiesLoader();
                is = pl.getLayersJsonInputStream();
                IOUtils.copy(is, os);
                os.flush();
            } catch (IOException e) {
                if(is != null){
                    try {
                        is.close();
                    } catch (IOException e1) {
                        is = null;
                    }
                }
            }
            finally{
                if(is != null){
                    is.close();
                }
            }
        }else{
            String outputPage = getServletConfig().getInitParameter("outputPage");
            RequestDispatcher rd = request.getRequestDispatcher(outputPage);
            rd.forward(request, response);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String layersjson = IOUtils.toString(request.getReader());
        try {
            Gson gson = new Gson();
            gson.fromJson(layersjson, Object.class); 
        } catch(JsonSyntaxException e) { 
            throw new IOException("Error parsing JSON request string");
        }
        PropertiesLoader pl = new PropertiesLoader();
        File f = pl.getLayersJson();
        String layersjson_path = f.getAbsolutePath();
        File fout = new File(f.getAbsolutePath() + "bkp");
        fout.createNewFile();
        FileUtils.copyFile(f, fout);
        try{
            FileUtils.forceDelete(f);
            f = new File(layersjson_path);
            f.createNewFile();
            FileUtils.writeStringToFile(f, layersjson);
        }catch(Exception e){
            // try at least to recover the backup
            f = new File(layersjson_path);
            f.createNewFile();
            FileUtils.copyFile(fout, f);
        }
    }
    
    class PropertiesLoader{
        
        public final String LAYER_JSON_PATH = "path";
        Configuration prop;
        
        public PropertiesLoader() throws IOException{
            try {
                prop = new PropertiesConfiguration("layer_json_path.properties");
            } catch (ConfigurationException e) {
                throw new IOException("Unable to load properties file");
            }
        }
        
        public InputStream getLayersJsonInputStream() throws IOException{
            return new FileInputStream(getLayersJson());
        }
        
        public File getLayersJson() throws IOException{
            String path = (String)prop.getProperty(LAYER_JSON_PATH);
            if(path == null || path.isEmpty()){
                try {
                    return new File(LayerMenuBuilder.class.getResource("layers_example.json").toURI());
                } catch (URISyntaxException e) {
                    throw new IOException("the layers.json path is wrong");
                }
            }
            File f = new File(path);
            if(f==null || !f.exists() || f.isDirectory() || !f.canWrite()){
                throw new IOException("the layers.json path is wrong");
            }
            return f;
        }
    }

}
