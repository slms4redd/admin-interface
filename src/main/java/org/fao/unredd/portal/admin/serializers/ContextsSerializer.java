package org.fao.unredd.portal.admin.serializers;

import com.google.gson.*;
import org.fao.unredd.portal.admin.Context;
import org.fao.unredd.portal.admin.Layer;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sgiaccio on 02/07/15.
 *
 * Serializes the layers for use by ember data
 */
public class ContextsSerializer implements JsonSerializer<List<Context>> {
    @Override
    public JsonElement serialize(List<Context> contexts, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jContexts = new JsonArray();
        for (Context context : contexts) {
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

        JsonObject root = new JsonObject();
        root.add("contexts", jContexts);

        return root;
    }
}
