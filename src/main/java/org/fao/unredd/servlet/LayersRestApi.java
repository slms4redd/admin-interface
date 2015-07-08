package org.fao.unredd.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.fao.unredd.Util;
import org.fao.unredd.portal.admin.Config;
import org.fao.unredd.portal.admin.serializers.ConfigGroupsDeserializer;
import org.fao.unredd.portal.admin.Layer;
import org.fao.unredd.portal.admin.serializers.LayerDeserializer;
import org.fao.unredd.portal.admin.serializers.LayersSerializer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by sgiaccio on 29/06/15.
 * Layers rest interface
 */
public class LayersRestApi extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(LayersRestApi.class);

    Gson gson;
    org.fao.unredd.portal.admin.Config config;

    public void init(ServletConfig config) throws ServletException {
        // TODO: this shouldn't be here
        String json;
        try {
            json = FileUtils.readFileToString(new File("/Users/sgiaccio/Documents/Code/admin-interface-new-new/src/test/resources/layers.json")); // TODO
        } catch (IOException e) {
            throw new ServletException("Can't open layers.json file");
        }

        GsonBuilder gb = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting();

        // Register type adapters
        gb.registerTypeAdapter(Config.class, new ConfigGroupsDeserializer.ConfigDeserializer());
        Type LayerListType = new TypeToken<List<Layer>>() {}.getType();
        gb.registerTypeAdapter(LayerListType, new LayersSerializer());
//        gb.registerTypeAdapter(Layer.class, new LayerSerializer());
        gb.registerTypeAdapter(Layer.class, new LayerDeserializer());

        gson = gb.create();

        this.config = gson.fromJson(json, org.fao.unredd.portal.admin.Config.class);
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        // Create the layer object
        String requestBody = Util.getBody(request);
        Layer layer = config.createLayer(gson.fromJson(requestBody, Layer.class));

        // Wrap the serialized layer json into {layer: {...}}
        JsonObject jo = new JsonObject();
        jo.add("layer", gson.toJsonTree(layer));

        response.setStatus(HttpServletResponse.SC_CREATED);
        out.write(gson.toJson(jo));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Type LayerListType = new TypeToken<List<Layer>>() {}.getType();
        out.write(gson.toJson(config.getLayers(), LayerListType));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String id = getRestId(request);

        if (id != null) {
            String requestBody = Util.getBody(request);
            config.editLayer(id, gson.fromJson(requestBody, Layer.class));
            out.write("{\"layers\":{\"id\":\"" + id + "\"}}");
        }
    }

    private static final Pattern regExIdPattern = Pattern.compile("/(.+)");

    private static String getRestId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();

        Matcher matcher = regExIdPattern.matcher(pathInfo);
        if (matcher.find())
            return matcher.group(1);

        return null;
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = getRestId(request);

        if (id != null) {
            config.deleteLayer(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

}
