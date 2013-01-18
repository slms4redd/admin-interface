package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;

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
		return new LayerRepresentation(Long.toString(resource.getId()),
				resource.getName(), LayerType.valueOf(getAttribute("LayerType")
						.getValue()), getAttribute("MosaicPath").getValue(),
				getAttribute("DissMosaicPath").getValue(), getAttribute(
						"OrigDataDestPath").getValue(), getAttribute(
						"RasterPixelWidth").getNumberValue().intValue(),
				getAttribute("RasterPixelHeight").getNumberValue().intValue(),
				getAttribute("RasterX0").getNumberValue(), getAttribute(
						"RasterX1").getNumberValue(), getAttribute("RasterY0")
						.getNumberValue(), getAttribute("RasterY1")
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
