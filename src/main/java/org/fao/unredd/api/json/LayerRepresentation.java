package org.fao.unredd.api.json;

import org.fao.unredd.api.model.LayerType;

public class LayerRepresentation extends AddLayerRequest {

	protected String id;

	public LayerRepresentation() {
		// Necessary for jackson
	}

	public LayerRepresentation(String id, String name, LayerType type) {
		super(name, type);
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getETag() {
		return id + name + type;
	}

}
