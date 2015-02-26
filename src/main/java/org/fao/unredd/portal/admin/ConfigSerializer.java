package org.fao.unredd.portal.admin;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Created by sgiaccio on 24/02/15.
 */
public class ConfigSerializer implements JsonSerializer<Config> {
    @Override
    public JsonElement serialize(Config config, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject root = new JsonObject();

        // Serialize the layers
        root.add("layers", jsonSerializationContext.serialize(config.layers));

        // Serialize the context
        JsonArray jContexts = new JsonArray();
        for (Context context : config.contexts) {
            JsonObject jContext = new JsonObject();

            jContext.addProperty("id", context.id);
            jContext.addProperty("label", context.label);
            jContext.addProperty("active", context.active);
            jContext.addProperty("infoFile", context.infoFile);
            jContext.addProperty("inlineLegendUrl", context.inlineLegendUrl);

            if (context.layers.size() > 0) {
                JsonArray jLayers = new JsonArray();
                for (Layer layer : context.layers) {
                    JsonPrimitive jLayerId = new JsonPrimitive(layer.id);
                    jLayers.add(jLayerId);
                }

                jContext.add("layers", jLayers);
            }
            jContexts.add(jContext);
        }
        root.add("contexts", jContexts);

        // Serialize the context
        JsonObject jContextGroups = ContextGroupSerializer.serialize(config.contextGroups);
        // Change the object name to "contextGroups" and add to the root
        root.add("contextGroups", jContextGroups.get("group"));

        return root;
    }


    private static class ContextGroupSerializer {
        /**
         *
         * @param r
         * @return Serialized Context or ContextGroup object
         */
        public static JsonObject serialize(Recursive r) {
            JsonObject ret;
            if (r instanceof Context)
                ret = serializeContext((Context) r);
            else if (r instanceof ContextGroup)
                ret = serializeContextGroup((ContextGroup) r);
            else
                ret = null; // TODO
            return ret;
        }

        /**
         *
         * @param context
         * @return Serialized ContextObject
         */
        public static JsonObject serializeContext(Context context) {
            JsonObject jContext = new JsonObject();
            jContext.addProperty("context", context.id);
            return jContext;
        }

        /**
         *
         * @param contextGroup
         * @return Serialized ContextGroup object
         */
        public static JsonObject serializeContextGroup(ContextGroup contextGroup) {
            JsonObject jGroup = new JsonObject();

            if (contextGroup.label != null)
                jGroup.addProperty("label", contextGroup.label);

            if (contextGroup.infoFile != null)
                jGroup.addProperty("infoFile", contextGroup.infoFile);

            if (contextGroup.items != null && contextGroup.items.size() > 0) {
                JsonArray jItems = new JsonArray();
                for (Recursive item : contextGroup.items) {
                    JsonObject jItem = serialize(item);
                    jItems.add(jItem);
                }
                jGroup.add("items", jItems);
            }

            JsonObject root = new JsonObject();
            root.add("group", jGroup);
            return root;
        }
    }
}
