package org.fao.unredd.api.json;

import org.fao.unredd.api.model.LayerType;

public class AddLayerRequest {

	protected String name;
	protected LayerType type;

	public AddLayerRequest() {
		// Necessary for jackson
	}

	public AddLayerRequest(String name, LayerType type) {
		super();
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public LayerType getType() {
		return type;
	}
}
