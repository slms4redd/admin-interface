package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.dto.search.SearchOperator;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;

import java.util.List;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.model.LayerUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

@Component
public class GeostoreLayerUpdates implements LayerUpdates {

	@Autowired
	private GeoStoreClient geostoreClient;

	@Override
	public Iterable<LayerUpdateRepresentation> getJSON() {
		SearchFilter filter = new CategoryFilter(
				UNREDDCategories.LAYERUPDATE.getName(), SearchOperator.EQUAL_TO);

		List<Resource> resourceList = geostoreClient.searchResources(filter,
				null, null, true, true).getList();

		return Iterables.transform(resourceList,
				new Function<Resource, LayerUpdateRepresentation>() {
					public LayerUpdateRepresentation apply(Resource resource) {
						return new GeostoreLayerUpdate(resource).getJSON();
					}
				});
	}

}
