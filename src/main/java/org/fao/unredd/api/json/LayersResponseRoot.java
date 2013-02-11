package org.fao.unredd.api.json;

import java.util.ArrayList;

import com.google.common.collect.Iterables;

/**
 * This class is only used to get the desired output JSON format mapped
 * automatically by jackson
 * 
 * @author fergonco
 */
public class LayersResponseRoot {

	private ArrayList<LayerRepresentation> layers;

	/**
	 * Necessary for jackson
	 */
	LayersResponseRoot() {
	}

	public LayersResponseRoot(Iterable<LayerRepresentation> layers) {
		this.layers = new ArrayList<LayerRepresentation>();
		Iterables.addAll(this.layers, layers);
	}

	public Iterable<LayerRepresentation> getLayers() {
		return layers;
	}
}
