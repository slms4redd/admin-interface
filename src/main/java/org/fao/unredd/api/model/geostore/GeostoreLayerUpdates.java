package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Resource;

import java.util.List;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.model.LayerUpdates;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class GeostoreLayerUpdates implements LayerUpdates {

	private List<Resource> resourceList;

	public GeostoreLayerUpdates(List<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	@Override
	public Iterable<LayerUpdateRepresentation> getJSON() {
		return Iterables.transform(resourceList,
				new Function<Resource, LayerUpdateRepresentation>() {
					public LayerUpdateRepresentation apply(Resource resource) {
						return new GeostoreLayerUpdate(resource).getJSON();
					}
				});
	}

}
