package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Resource;

import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layer;

public class GeostoreLayer implements Layer {

	private Resource resource;

	public GeostoreLayer(Resource resource) {
		this.resource = resource;
	}

	@Override
	public LayerRepresentation getJSON() {
		LayerRepresentation ret = new LayerRepresentation(
				Long.toString(resource.getId()), resource.getName(), null);
		throw new UnsupportedOperationException("Fix the null");
	}
}
