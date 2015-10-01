package org.fao.unredd.portal.admin.serializers;

import com.google.gson.*;
import org.fao.unredd.portal.admin.Config;
import org.fao.unredd.portal.admin.Context;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by sgiaccio on 03/07/15.
 */
public class ContextDeserializer implements JsonDeserializer<Context> {
    private final Config config;

    public ContextDeserializer(Config config) {
        this.config = config;
    }

    @Override
    public Context deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Context context = new Context();

        JsonObject jo = jsonElement.getAsJsonObject().getAsJsonObject("context");
        context.id = getValue(jo, "id");
        context.label = getValue(jo, "label");
        JsonPrimitive t = jo.getAsJsonPrimitive("active");
        if (t != null) context.active = t.getAsBoolean();

        context.layers = new ArrayList<>();
        if (jo.getAsJsonArray("layers") != null) {
            for (JsonElement element : jo.getAsJsonArray("layers")) {
                String layerId = element.getAsString();
                context.layers.add(config.layers.get(layerId));
            }
        }

        return context;
    }

    private static String getValue(JsonElement el, String name) {
        JsonElement val = el.getAsJsonObject().get(name);
        if (val != null) return val.getAsString();
        return null;
    }

}
