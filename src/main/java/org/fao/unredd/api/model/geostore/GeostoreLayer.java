package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;

import java.util.List;

import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.LayerType;

public class GeostoreLayer implements Layer {

	private Resource resource;

	public GeostoreLayer(Resource resource) {
		this.resource = resource;
	}

	@Override
	public LayerRepresentation getJSON() {
		List<Attribute> attributes = resource.getAttribute();
		LayerType layerType = null;
		for (Attribute attribute : attributes) {
			if ("LayerType".equals(attribute.getName())) {
				layerType = LayerType.valueOf(attribute.getValue());
			}
		}
		return new LayerRepresentation(Long.toString(resource.getId()),
				resource.getName(), layerType);
	}
}
