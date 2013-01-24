package org.fao.unredd.api.json;

import org.fao.unredd.api.model.LayerType;

public class LayerRepresentation extends AddLayerRequest {

	protected String id;

	/**
	 * Necessary for jackson
	 */
	public LayerRepresentation() {
	}

	public LayerRepresentation(String id, String name, LayerType type,
			String stgMosaicPath, String dissMosaicPath,
			String destOrigAbsPath, Integer pixelWidth, Integer pixelHeight,
			Double minx, Double maxx, Double miny, Double maxy) {
		super(name, type, stgMosaicPath, dissMosaicPath, destOrigAbsPath,
				pixelWidth, pixelHeight, minx, maxx, miny, maxy);
		this.id = id;
	}

	public String getId() {
		return id;
	}

}