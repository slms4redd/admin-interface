package org.fao.unredd.api.json;

import java.util.ArrayList;

import com.google.common.collect.Iterables;

/**
 * This class is only used to get the desired output JSON format mapped
 * automatically by jackson
 * 
 * @author fergonco
 */
public class LayerUpdatesResponseRoot {

	private ArrayList<LayerUpdateRepresentation> layerUpdates;

	/**
	 * Necessary for jackson
	 */
	LayerUpdatesResponseRoot() {
	}

	public LayerUpdatesResponseRoot(Iterable<LayerUpdateRepresentation> layers) {
		this.layerUpdates = new ArrayList<LayerUpdateRepresentation>();
		Iterables.addAll(this.layerUpdates, layers);
	}

	public ArrayList<LayerUpdateRepresentation> getLayerUpdates() {
		return layerUpdates;
	}
}
