package org.fao.unredd.portal.admin;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;
import org.fao.unredd.servlet.AdminGUIAbstractServlet;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sgiaccio on 11/02/15.
 *
 * Represents a layer.json configuration
 */
public class Config {
    private final static Logger LOGGER = Logger.getLogger(AdminGUIAbstractServlet.class);

    List<Layer> layers;
    List<Context> contexts;
    ContextGroup contextGroups;

    @Override
    public String toString() {
        return "Config{" +
                "layers=" + layers +
                ", contexts=" + contexts +
                ", contextGroups=" + contextGroups +
                '}';
    }
}
