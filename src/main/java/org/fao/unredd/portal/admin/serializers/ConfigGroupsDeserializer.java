package org.fao.unredd.portal.admin.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.fao.unredd.portal.admin.*;
import org.fao.unredd.servlet.AdminGUIAbstractServlet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sgiaccio on 24/02/15.
 */
public class ConfigGroupsDeserializer implements JsonDeserializer<Config> {
    private final static Logger LOGGER = Logger.getLogger(AdminGUIAbstractServlet.class);

    public Config deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Config config = new Config();

        Gson gson = new Gson();

        // Read the whole JSON into memory via GSON_DOM
        JsonArray layersJson = json.getAsJsonObject().getAsJsonArray("layers");

        // map the Layer class via GSON_BIND
        Type listType = new TypeToken<ArrayList<Layer>>(){}.getType();
        List<Layer> layers = gson.fromJson(layersJson, listType);
        int rank = 1;
        for (Layer layer : layers) {
            layer.rank = rank++;
            config.layers.put(layer.id, layer);
        }

        // Fill map with layer as key and layer as value
//        Map<String, Layer> layersMap = new HashMap<>();
//        for (Layer l : config.layers) layersMap.put(l.id, l);

        // Deserialize the contexts
        JsonArray contextsJson = json.getAsJsonObject().getAsJsonArray("contexts");
//        List<Context> contexts = new ArrayList<>();
        for (JsonElement je : contextsJson) {
            Context c = new Context();

            JsonObject jo = je.getAsJsonObject();
            c.id = getValue(jo, "id");
            c.label = getValue(jo, "label");
            JsonPrimitive t = jo.getAsJsonPrimitive("active");
            if (t != null) c.active = t.getAsBoolean();

            c.layers = new ArrayList<>();
            if (jo.getAsJsonArray("layers") != null) {
                for (JsonElement element : jo.getAsJsonArray("layers")) {
                    String layerId = element.getAsString();
                    c.layers.add(config.layers.get(layerId));
                }
            }

            c.infoFile = getValue(jo, "infoFile");
            c.inlineLegendUrl = getValue(jo, "inlineLegendUrl");

            config.contexts.put(c.id, c);
        }

        // Fill map with layer as key and layer as value
//        Map<String, Context> contextsMap = new HashMap<>();
//        for (Context c : config.contexts) contextsMap.put(c.id, c);

        // Deserialize the context groups
        JsonElement contextGroups = json.getAsJsonObject().getAsJsonObject("contextGroups");
        // Attach the contextGroup to a dummy group to make the recursive method deserializeContextGroups work
        JsonObject dummyGroup = new JsonObject();
        dummyGroup.add("group", contextGroups);
        config.contextGroups = (ContextGroup)deserializeContextGroups(dummyGroup, config.contexts);

        return config;
    }

    /**
     *
     * @param jsonElement
     * @param contextsMap
     * @return a ContextGroup object
     */
    private static Recursive deserializeContextGroups(JsonElement jsonElement, Map<String, Context> contextsMap) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Recursive r;
        if (jsonObject == null)
            r = null;
        else {
            Boolean isGroup = jsonObject.has("group");
            if (isGroup) {
                LOGGER.debug("Group found: " + jsonObject);
                JsonElement e = jsonObject.get("group");

                ContextGroup g = new ContextGroup();
                g.label = getValue(e, "label");
                g.infoFile = getValue(e, "infoFile");

                // Recursively set the group contexts/groups
                JsonArray items = jsonObject.get("group").getAsJsonObject().get("items").getAsJsonArray();
                for (JsonElement jEl : items) {
                    g.items.add(deserializeContextGroups(jEl, contextsMap));
                }
                r = g;
            } else {
                LOGGER.debug("Context found: " + jsonObject);
                String contextId = jsonObject.get("context").getAsString();
                r = contextsMap.get(contextId);
            }
        }
        return r;
    }

    private static String getValue(JsonElement el, String name) {
        JsonElement val = el.getAsJsonObject().get(name);
        if (val != null)
            return val.getAsString();
        return null;
    }

    /**
     * Created by sgiaccio on 24/02/15.
     */
    public static class ConfigDeserializer implements JsonDeserializer<Config> {
        private final static Logger LOGGER = Logger.getLogger(AdminGUIAbstractServlet.class);

        public Config deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            Config config = new Config();

            Gson gson = new Gson();

            // Read the whole JSON into memory via GSON_DOM
            JsonArray layersJson = json.getAsJsonObject().getAsJsonArray("layers");

            // map the Layer class via GSON_BIND
            Type listType = new TypeToken<ArrayList<Layer>>(){}.getType();
            List<Layer> layers = gson.fromJson(layersJson, listType);
            int rank = 1;
            for (Layer layer : layers) {
                layer.rank = rank++;
                config.layers.put(layer.id, layer);
            }

            // Fill map with layer as key and layer as value
//            Map<String, Layer> layersMap = new HashMap<>();
//            for (Layer l : config.layers) layersMap.put(l.id, l);

            // Deserialize the contexts
            JsonArray contextsJson = json.getAsJsonObject().getAsJsonArray("contexts");
//            List<Context> contexts = new ArrayList<>();
            for (JsonElement je : contextsJson) {
                Context c = new Context();

                JsonObject jo = je.getAsJsonObject();
                c.id = getValue(jo, "id");
                c.label = getValue(jo, "label");
                JsonPrimitive t = jo.getAsJsonPrimitive("active");
                if (t != null) c.active = t.getAsBoolean();

                c.layers = new ArrayList<>();
                if (jo.getAsJsonArray("layers") != null) {
                    for (JsonElement element : jo.getAsJsonArray("layers")) {
                        String layerId = element.getAsString();
                        c.layers.add(config.layers.get(layerId));
                    }
                }

                c.infoFile = getValue(jo, "infoFile");
                c.inlineLegendUrl = getValue(jo, "inlineLegendUrl");

                config.contexts.put(c.id, c);
            }

            // Fill map with layer as key and layer as value
//            Map<String, Context> contextsMap = new HashMap<>();
//            for (Context c : config.contexts) contextsMap.put(c.id, c);

            // Deserialize the context groups
            JsonElement contextGroups = json.getAsJsonObject().getAsJsonObject("contextGroups");
            // Attach the contextGroup to a dummy group to make the recursive method deserializeContextGroups work
            JsonObject dummyGroup = new JsonObject();
            dummyGroup.add("group", contextGroups);
            config.contextGroups = (ContextGroup)deserializeContextGroups(dummyGroup, config.contexts);

            return config;
        }

        /**
         *
         * @param jsonElement
         * @param contextsMap
         * @return a ContextGroup object
         */
        private static Recursive deserializeContextGroups(JsonElement jsonElement, Map<String, Context> contextsMap) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            Recursive r;
            if (jsonObject == null)
                r = null;
            else {
                Boolean isGroup = jsonObject.has("group");
                if (isGroup) {
                    LOGGER.debug("Group found: " + jsonObject);
                    JsonElement e = jsonObject.get("group");

                    ContextGroup g = new ContextGroup();
                    g.label = getValue(e, "label");
                    g.infoFile = getValue(e, "infoFile");

                    // Recursively set the group contexts/groups
                    JsonArray items = jsonObject.get("group").getAsJsonObject().get("items").getAsJsonArray();
                    for (JsonElement jEl : items) {
                        g.items.add(deserializeContextGroups(jEl, contextsMap));
                    }
                    r = g;
                } else {
                    LOGGER.debug("Context found: " + jsonObject);
                    String contextId = jsonObject.get("context").getAsString();
                    r = contextsMap.get(contextId);
                }
            }
            return r;
        }

        private static String getValue(JsonElement el, String name) {
            JsonElement val = el.getAsJsonObject().get(name);
            if (val != null)
                return val.getAsString();
            return null;
        }
    }
}
