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
import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.Layers;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

public class GeostoreLayers implements Layers {

	private GeoStoreClient geostoreClient;

	public GeostoreLayers(String geostoreRestUrl, String geostoreUsername,
			String geostorePassword) {
		geostoreClient = new GeoStoreClient();
		geostoreClient.setGeostoreRestUrl(geostoreRestUrl);
		geostoreClient.setUsername(geostoreUsername);
		geostoreClient.setPassword(geostorePassword);
	}

	@Override
	public Iterable<LayerRepresentation> getJSON() {
		SearchFilter filter = new CategoryFilter(
				UNREDDCategories.LAYER.getName(), SearchOperator.EQUAL_TO);
		List<Resource> resources = geostoreClient.searchResources(filter, null,
				null, true, true).getList();
		return Iterables.transform(resources,
				new Function<Resource, LayerRepresentation>() {
					public LayerRepresentation apply(Resource resource) {
						return new GeostoreLayer(resource).getJSON();
					}
				});
	}

	@Override
	public Layer addLayer(AddLayerRequest addLayerRequest) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Layer getLayer(String id) throws IllegalArgumentException {
		SearchFilter filter = new AndFilter(new CategoryFilter(
				UNREDDCategories.LAYER.getName(), SearchOperator.EQUAL_TO),
				new FieldFilter(BaseField.ID, id, SearchOperator.EQUAL_TO));

		List<Resource> resourceList = geostoreClient.searchResources(filter,
				null, null, true, true).getList();
		if (CollectionUtils.isEmpty(resourceList)) {
			throw new IllegalArgumentException();
		}
		Resource layerResource = resourceList.get(0);
		return new GeostoreLayer(layerResource);
	}

}
