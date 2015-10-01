package org.fao.unredd.portal.admin;

import static org.junit.Assert.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;

public class LayerTest {
    private static String json;

    @BeforeClass
    public static void beforeClass() throws IOException {
        InputStream inputStream = LayerTest.class.getResourceAsStream("/layer.json");
        json = IOUtils.toString(inputStream, "UTF-8");
    }

    @Ignore
    @Test
    public void testParse() throws IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Layer layer = gson.fromJson(json, Layer.class);

        String result = gson.toJson(layer);

        JsonParser parser = new JsonParser();
        JsonElement o1 = parser.parse(json);
        JsonElement o2 = parser.parse(result);
        assertEquals(o1, o2);
    }
}

