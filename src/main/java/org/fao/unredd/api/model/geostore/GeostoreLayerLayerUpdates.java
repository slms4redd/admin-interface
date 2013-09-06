package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.services.dto.search.AndFilter;
import it.geosolutions.geostore.services.dto.search.BaseField;
import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.FieldFilter;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.dto.search.SearchOperator;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;

import org.fao.unredd.api.model.LayerUpdates;

public class GeostoreLayerLayerUpdates extends AbstractGeostoreLayerUpdateList
		implements LayerUpdates {

	private GeoStoreClient geostoreClient;
	private long layerId;

	public GeostoreLayerLayerUpdates(Long layerId, GeoStoreClient geostoreClient) {
		this.layerId = layerId;
		this.geostoreClient = geostoreClient;
	}

	@Override
	protected GeoStoreClient getGeostoreClient() {
		return geostoreClient;
	}

	@Override
	protected SearchFilter getFilter() {
		return new AndFilter(
				new CategoryFilter(UNREDDCategories.LAYERUPDATE.getName(),
						SearchOperator.EQUAL_TO), new FieldFilter(BaseField.ID,
						Long.toString(layerId), SearchOperator.EQUAL_TO));
	}

}
