package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.dto.search.SearchOperator;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;

import org.fao.unredd.api.model.LayerUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GeostoreLayerUpdates extends AbstractGeostoreLayerUpdateList
		implements LayerUpdates {

	@Autowired
	private GeoStoreClient geostoreClient;

	@Override
	protected GeoStoreClient getGeostoreClient() {
		return geostoreClient;
	}

	@Override
	protected SearchFilter getFilter() {
		return new CategoryFilter(UNREDDCategories.LAYERUPDATE.getName(),
				SearchOperator.EQUAL_TO);
	}

}
