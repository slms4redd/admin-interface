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

import javax.ws.rs.WebApplicationException;

import org.apache.commons.collections.CollectionUtils;
import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.Layers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.sun.jersey.api.client.UniformInterfaceException;

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

		RESTResource layerRestResource = toRESTResource(addLayerRequest);

		long id = geostoreClient.insert(layerRestResource);

		return id;
	}

	private RESTResource toRESTResource(AddLayerRequest addLayerRequest) {
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
		return layerRestResource;
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

	@Override
	public void updateLayer(String id, AddLayerRequest layer)
			throws IllegalArgumentException {
		try {
			geostoreClient.updateResource(Long.parseLong(id),
					toRESTResource(layer));
		} catch (UniformInterfaceException e) {
			/*
			 * To follow the contract of the Layers interface we need to throw
			 * IAE in case of an nonexistent id. Just propagate the exception to
			 * the client otherwise
			 */
			if (e.getResponse().getStatus() == 404) {
				throw new IllegalArgumentException("Layer not found: " + id);
			} else {
				throw new WebApplicationException(e, e.getResponse()
						.getStatus());
			}
		}
	}

}
