package org.fao.unredd.resources;

import java.util.HashMap;

public class Model {

	private static int serial = 0;

	private HashMap<String, Layer> layerList = new HashMap<String, Layer>();

	public Layer[] getLayers() {
		return layerList.values().toArray(new Layer[layerList.size()]);
	}

	public Layer getLayer(String id) {
		return layerList.get(id);
	}

	public String addLayer(Layer layer) {
		layer.id = Integer.toString(serial++);
		layerList.put(layer.id, layer);

		return layer.id;
	}

	public void clear() {
		layerList.clear();
	}

}
