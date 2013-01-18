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

	@Override
	public int hashCode() {
		return name.hashCode() + type.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AddLayerRequest) {
			AddLayerRequest that = (AddLayerRequest) obj;
			return that.name.equals(this.name) && that.type == this.type;
		}

		return false;
	}

	@Override
	public String toString() {
		return name + ": " + type;
	}
}
