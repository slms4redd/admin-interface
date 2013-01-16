package org.fao.unredd.resources;

import java.util.HashMap;

public class Model {

	private static int serial = 0;

	private HashMap<String, Layer> layerList = new HashMap<String, Layer>();

	public Layer[] getLayers() {
		return layerList.values().toArray(new Layer[layerList.size()]);
	}

	/**
	 * Get the layer with the specified ID
	 * 
	 * @param id
	 *            ID of the layer to be retrieved
	 * @return
	 * @throws IllegalArgumentException
	 *             If the specified id does not correspond to any layer
	 */
	public Layer getLayer(String id) throws IllegalArgumentException {
		if (!layerList.containsKey(id)) {
			throw new IllegalArgumentException();
		}
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
