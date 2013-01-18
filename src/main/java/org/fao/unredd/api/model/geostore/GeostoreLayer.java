package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;

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
						.getValue()), getAttribute("stgMosaicPath").getValue(),
				getAttribute("dissMosaicPath").getValue(), getAttribute(
						"DestOrigAbsPath").getValue(), getAttribute(
						"pixelWidth").getNumberValue().intValue(),
				getAttribute("pixelHeight").getNumberValue().intValue(),
				getAttribute("minx").getNumberValue(), getAttribute("maxx")
						.getNumberValue(), getAttribute("miny")
						.getNumberValue(), getAttribute("maxy")
						.getNumberValue(), getAttribute("data").getValue());
	}

	private Attribute getAttribute(String attributeName) {
		return Iterables.find(resource.getAttribute(), new AttributeFinder(
				attributeName));
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
