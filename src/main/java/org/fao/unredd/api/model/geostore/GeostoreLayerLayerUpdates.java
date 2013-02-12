package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.dto.search.AndFilter;
import it.geosolutions.geostore.services.dto.search.BaseField;
import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.FieldFilter;
import it.geosolutions.geostore.services.dto.search.SearchOperator;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;

import java.util.List;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.model.LayerUpdates;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class GeostoreLayerLayerUpdates implements LayerUpdates {

	private GeoStoreClient geostoreClient;
	private long layerId;

	public GeostoreLayerLayerUpdates(Long layerId, GeoStoreClient geostoreClient) {
		this.layerId = layerId;
		this.geostoreClient = geostoreClient;
	}

	@Override
	public Iterable<LayerUpdateRepresentation> getJSON() {
		AndFilter filter = new AndFilter(
				new CategoryFilter(UNREDDCategories.LAYERUPDATE.getName(),
						SearchOperator.EQUAL_TO), new FieldFilter(BaseField.ID,
						Long.toString(layerId), SearchOperator.EQUAL_TO));

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
