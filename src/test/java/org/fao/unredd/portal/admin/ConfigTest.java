package org.fao.unredd.portal.admin;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

public class ConfigTest {
    private static String json;

    @BeforeClass
    public static void beforeClass() throws IOException {
        InputStream inputStream = ConfigTest.class.getResourceAsStream("/layers.json");
        json = IOUtils.toString(inputStream, "UTF-8");
    }

    @Test
    public void testSerializeDeserialize() {
        // Deserialize
        GsonBuilder gb = new GsonBuilder()
                .disableHtmlEscaping()
                .setPrettyPrinting()
                .registerTypeAdapter(Config.class, new ConfigDeserializer());

        Gson gson = gb.create();
        Config config = gson.fromJson(json, Config.class);

        // Serialize
        gson = gb.registerTypeAdapter(Config.class, new ConfigSerializer()).create();
        String result = gson.toJson(config);


        JsonParser parser = new JsonParser();
        JsonElement o1 = parser.parse(json);
        JsonElement o2 = parser.parse(result);
        assertEquals(o1, o2);
    }
}