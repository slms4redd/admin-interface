package org.fao.unredd.portal.admin;

import java.util.*;

/**
 * Created by sgiaccio on 11/02/15.
 *
 * Represents a layer.json configuration
 */
public class Config {
//    private final static Logger LOGGER = Logger.getLogger(AdminGUIAbstractServlet.class);

    // TODO make it thread safe

    public final Map<String, Layer> layers;
    public final Map<String, Context> contexts;
    public ContextGroup contextGroups;

    public Config() {
        layers = new LinkedHashMap(); // order matters
        contexts = new LinkedHashMap(); // order doesn't matter here, but necessary to pass the tests - better tests are required
    }

    @Override
    public String toString() {
        return "Config{" +
                "layers=" + layers +
                ", contexts=" + contexts +
                ", contextGroups=" + contextGroups +
                '}';
    }

    private int getMaxLayerId() {
        List<Layer> layerList = new ArrayList<Layer>(layers.values());
        List<String> ids = new ArrayList<>();
        for (Layer l : layerList) {
            ids.add(l.id);
        }
        return getMaxNumericId(ids);
    }

    private int getMaxNumericId(List<String> ids) {
        int maxId = 0;
        for (String id : ids) {
            try {
                maxId = Math.max(maxId, Integer.parseInt(id));
            } catch (NumberFormatException e) {
                // Do nothing, the id is not numeric
            }
        }
        return maxId;
    }

    public List<Layer> getLayers() {
        return new ArrayList<>(layers.values());
    }

    private synchronized String getNextLayerId() {
        int maxId = getMaxLayerId();
        return "" + (maxId + 1);
    }

    private synchronized int getNextLayerRank() {
        int maxRank = 0;
        for (Layer l : layers.values()) {
            maxRank = Math.max(maxRank, l.rank);
        }
        return maxRank + 1;
    }

    public Layer createLayer(Layer layer) {
        layer.id = getNextLayerId();
        layer.rank = getNextLayerRank();
        layers.put(layer.id, layer);

        return layer;
    }

    public void editLayer(String id, Layer layer) {
        layer.id = id;
        layers.put(id, layer);
    }

    public void deleteLayer(String id) {
        layers.remove(id);
    }

    public void deleteContext(String id) {
        contexts.remove(id);
    }

    public int getMaxContextId() {
        List<String> ids = new ArrayList<>();
        for (Context c : contexts.values()) {
            ids.add(c.id);
        }
        return getMaxNumericId(ids);
    }

    private synchronized String getNextContextId() {
        int maxId = getMaxContextId();
        return "" + (maxId + 1);
    }

    public Context createContext(Context context) {
        context.id = getNextContextId();
        contexts.put(context.id, context);

        return context;
    }

    public void editContext(String id, Context context) {
        context.id = id;
        contexts.put(id, context);
    }

    public List<Context> getContexts() {
        return new ArrayList<>(contexts.values());
    }
}
