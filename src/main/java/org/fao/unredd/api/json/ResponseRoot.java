package org.fao.unredd.api.json;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Iterables;

/**
 * This class is only used to get the desired output JSON format mapped
 * automatically by jackson
 * 
 * @author fergonco
 */
public class ResponseRoot extends
		HashMap<String, ArrayList<LayerRepresentation>> {

	private static final long serialVersionUID = 1L;

	/**
	 * Necessary for jackson
	 */
	public ResponseRoot() {
	}

	private ResponseRoot(String attributeName,
			Iterable<LayerRepresentation> value) {
		super(1);
		ArrayList<LayerRepresentation> list = new ArrayList<LayerRepresentation>();
		Iterables.addAll(list, value);
		put(attributeName, list);
	}

	public static ResponseRoot newLayers(Iterable<LayerRepresentation> json) {
		return new ResponseRoot("layers", json);
	}

	public Iterable<LayerRepresentation> getLayers() {
		return get("layers");
	}
}
