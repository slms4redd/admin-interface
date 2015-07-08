package org.fao.unredd.portal.admin.serializers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.fao.unredd.portal.admin.Layer;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sgiaccio on 29/06/15.
 *
 * Serializes the layers for use by ember data
 */
public class LayersSerializer implements JsonSerializer<List<Layer>> {

    @Override
    public JsonElement serialize(List<Layer> layers, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject root = new JsonObject();
        root.add("layers", jsonSerializationContext.serialize(layers));

        return root;
    }
}
