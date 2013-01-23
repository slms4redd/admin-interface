package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.geostore.model.UNREDDLayer;

import java.util.NoSuchElementException;

import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.LayerType;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class GeostoreLayer implements Layer {

	private Resource resource;

	public GeostoreLayer(Resource resource) {
		this.resource = resource;
	}

	@Override
	public LayerRepresentation getJSON() {
		return new LayerRepresentation(
				Long.toString(resource.getId()),
				resource.getName(),
				LayerType.valueOf(getAttribute(
						UNREDDLayer.Attributes.LAYERTYPE.getName()).getValue()),
				getAttribute(UNREDDLayer.Attributes.MOSAICPATH.getName())
						.getValue(), getAttribute(
						UNREDDLayer.Attributes.DISSMOSAICPATH.getName())
						.getValue(), getAttribute(
						UNREDDLayer.Attributes.ORIGDATADESTPATH.getName())
						.getValue(), getAttribute(
						UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName())
						.getNumberValue().intValue(), getAttribute(
						UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName())
						.getNumberValue().intValue(), getAttribute(
						UNREDDLayer.Attributes.RASTERX0.getName())
						.getNumberValue(), getAttribute(
						UNREDDLayer.Attributes.RASTERX1.getName())
						.getNumberValue(), getAttribute(
						UNREDDLayer.Attributes.RASTERY0.getName())
						.getNumberValue(), getAttribute(
						UNREDDLayer.Attributes.RASTERY1.getName())
						.getNumberValue());
	}

	private Attribute getAttribute(String attributeName) {
		try {
			return Iterables.find(resource.getAttribute(), new AttributeFinder(
					attributeName));
		} catch (NoSuchElementException e) {
			/*
			 * Should never ask for an non existing attribute
			 */
			throw new RuntimeException(attributeName);
		}
	}

	private class AttributeFinder implements Predicate<Attribute> {

		private String attributeName;

		public AttributeFinder(String attributeName) {
			this.attributeName = attributeName;
		}

		public boolean apply(Attribute at) {
			return at.getName().equals(attributeName);
		}
	}
}
