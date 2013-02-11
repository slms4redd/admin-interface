package org.fao.unredd.api.model.geostore;

import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate;

import org.fao.unredd.api.json.LayerUpdateRepresentation;

public class GeostoreLayerUpdate extends AbstractGeostoreElement {

	public GeostoreLayerUpdate(Resource resource) {
		super(resource);
	}

	public LayerUpdateRepresentation getJSON() {
		return new LayerUpdateRepresentation(Long.toString(resource.getId()),
				resource.getName(), getAttribute(
						UNREDDLayerUpdate.Attributes.LAYER.getName())
						.getTextValue(), getAttribute(
						UNREDDLayerUpdate.Attributes.YEAR.getName())
						.getTextValue(), getAttribute(
						UNREDDLayerUpdate.Attributes.MONTH.getName())
						.getTextValue(), getAttribute(
						UNREDDLayerUpdate.Attributes.DAY.getName())
						.getTextValue(), getAttribute(
						UNREDDLayerUpdate.Attributes.PUBLISHED.getName())
						.getTextValue());
	}

}
