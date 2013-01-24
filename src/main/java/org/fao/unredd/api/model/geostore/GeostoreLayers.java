package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.dto.search.AndFilter;
import it.geosolutions.geostore.services.dto.search.BaseField;
import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.FieldFilter;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.dto.search.SearchOperator;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.RESTResource;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;
import it.geosolutions.unredd.geostore.model.UNREDDLayer;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.Layers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;

@Component
public class GeostoreLayers implements Layers {

	@Autowired
	private GeoStoreClient geostoreClient;

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
	public long addLayer(AddLayerRequest addLayerRequest) {

		UNREDDLayer unreddLayer = new UNREDDLayer();

		unreddLayer.setAttribute(UNREDDLayer.Attributes.LAYERTYPE,
				addLayerRequest.getType().name());
		unreddLayer.setAttribute(UNREDDLayer.Attributes.MOSAICPATH,
				addLayerRequest.getStgMosaicPath());
		unreddLayer.setAttribute(UNREDDLayer.Attributes.DISSMOSAICPATH,
				addLayerRequest.getDissMosaicPath());
		unreddLayer.setAttribute(UNREDDLayer.Attributes.ORIGDATADESTPATH,
				addLayerRequest.getDestOrigAbsPath());
		unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELHEIGHT,
				Integer.toString(addLayerRequest.getPixelHeight()));
		unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERPIXELWIDTH,
				Integer.toString(addLayerRequest.getPixelWidth()));
		unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX0,
				Double.toString(addLayerRequest.getMinx()));
		unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERX1,
				Double.toString(addLayerRequest.getMaxx()));
		unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY0,
				Double.toString(addLayerRequest.getMiny()));
		unreddLayer.setAttribute(UNREDDLayer.Attributes.RASTERY1,
				Double.toString(addLayerRequest.getMaxy()));

		RESTResource layerRestResource = unreddLayer.createRESTResource();
		layerRestResource.setName(addLayerRequest.getName());

		long id = geostoreClient.insert(layerRestResource);

		return id;
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
