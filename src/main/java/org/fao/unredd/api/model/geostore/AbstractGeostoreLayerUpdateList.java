package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.dto.search.AndFilter;
import it.geosolutions.geostore.services.dto.search.BaseField;
import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.FieldFilter;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.dto.search.SearchOperator;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.model.LayerUpdate;
import org.fao.unredd.api.model.LayerUpdates;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public abstract class AbstractGeostoreLayerUpdateList implements LayerUpdates {

	@Override
	public LayerUpdate getLayerUpdate(String updateId) {
		SearchFilter filter = new AndFilter(
				new CategoryFilter(UNREDDCategories.LAYERUPDATE.getName(),
						SearchOperator.EQUAL_TO), new FieldFilter(BaseField.ID,
						updateId, SearchOperator.EQUAL_TO));

		List<Resource> resourceList = getGeostoreClient().searchResources(
				filter, null, null, true, true).getList();
		if (CollectionUtils.isEmpty(resourceList)) {
			throw new IllegalArgumentException();
		}
		Resource layerUpdateResource = resourceList.get(0);
		return new GeostoreLayerUpdate(layerUpdateResource);
	}

	protected abstract GeoStoreClient getGeostoreClient();

	@Override
	public Iterable<LayerUpdateRepresentation> getJSON() {
		List<Resource> resourceList = getGeostoreClient().searchResources(
				getFilter(), null, null, true, true).getList();

		return Iterables.transform(resourceList,
				new Function<Resource, LayerUpdateRepresentation>() {
					public LayerUpdateRepresentation apply(Resource resource) {
						return new GeostoreLayerUpdate(resource).getJSON();
					}
				});
	}

	protected abstract SearchFilter getFilter();

}
