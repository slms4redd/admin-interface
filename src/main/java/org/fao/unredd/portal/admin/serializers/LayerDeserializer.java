package org.fao.unredd.portal.admin.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.fao.unredd.portal.admin.Config;
import org.fao.unredd.portal.admin.Layer;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by sgiaccio on 30/06/15.
 *
 * Deserializes a layers json object coming from ember-data
 */
public class LayerDeserializer implements JsonDeserializer<Layer> {
    @Override
    public Layer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Gson gson = new Gson();

        JsonObject layerJson = jsonElement.getAsJsonObject().getAsJsonObject("layer");

        return gson.fromJson(layerJson, Layer.class);
    }
}
