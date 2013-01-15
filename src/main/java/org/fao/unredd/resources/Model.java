package org.fao.unredd.resources;

import java.util.HashMap;

public class Model {

	private HashMap<String, Layer> layerList = new HashMap<String, Layer>();

	public Layer[] getLayers() {
		return layerList.values().toArray(new Layer[layerList.size()]);
	}

	public Layer getLayer(String id) {
		return layerList.get(id);
	}

	public void addLayer(Layer layer) {
		layerList.put(layer.id, layer);
	}

	public void clear() {
		layerList.clear();
	}

}
