package org.fao.unredd.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.fao.unredd.Util;
import org.fao.unredd.portal.admin.Config;
import org.fao.unredd.portal.admin.Context;
import org.fao.unredd.portal.admin.Layer;
import org.fao.unredd.portal.admin.serializers.ConfigGroupsDeserializer;
import org.fao.unredd.portal.admin.serializers.ContextDeserializer;
import org.fao.unredd.portal.admin.serializers.ContextsSerializer;
import org.fao.unredd.portal.admin.serializers.LayerDeserializer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sgiaccio on 02/07/15.
 */
public class ContextsRestApi extends HttpServlet {
    private final static Logger LOGGER = Logger.getLogger(ContextsRestApi.class);

    private Gson gson;
    private org.fao.unredd.portal.admin.Config config;

    public void init(ServletConfig config) throws ServletException {
        // TODO: this shouldn't be here
        String json;
        try {
            json = FileUtils.readFileToString(new File("C:\\Users\\workstation\\git\\admin-interface\\src\\test\\resources\\layers.json")); // TODO
        } catch (IOException e) {
            throw new ServletException("Can't open layers.json file");
        }

        GsonBuilder gb = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting();

        // Register type adapters
        Type ContextListType = new TypeToken<List<Context>>() {}.getType();
        gb.registerTypeAdapter(ContextListType, new ContextsSerializer());
        gb.registerTypeAdapter(Config.class, new ConfigGroupsDeserializer.ConfigDeserializer());
//        gb.registerTypeAdapter(Layer.class, new LayerDeserializer());

        gson = gb.create();
        this.config = gson.fromJson(json, org.fao.unredd.portal.admin.Config.class);
        gb.registerTypeAdapter(Context.class, new ContextDeserializer(this.config));
        gson = gb.create(); // TODO already created, this doesn't look nice
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO
        PrintWriter out = response.getWriter();

        // Create the context object
        String requestBody = Util.getBody(request);
        Context context = config.createContext(gson.fromJson(requestBody, Context.class));

        // Wrap the serialized layer json into {context: {...}}
        JsonObject jo = new JsonObject();
        jo.add("context", gson.toJsonTree(context));

        response.setStatus(HttpServletResponse.SC_CREATED);
        out.write(gson.toJson(jo));

        LOGGER.info("Created context - id = " + context.id);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Type ContextListType = new TypeToken<List<Context>>() {}.getType();
        out.write(gson.toJson(config.getContexts(), ContextListType));
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        String id = getRestId(request);

        if (id != null) {
            String requestBody = Util.getBody(request);
            config.editContext(id, gson.fromJson(requestBody, Context.class));
            out.write("{\"contexts\":{\"id\":\"" + id + "\"}}");
        }

        LOGGER.info("Edited context - id = " + id);
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
            config.deleteContext(id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
        LOGGER.info("Deleted context - id = " + id);
    }
}
