package org.fao.unredd.api.resources;

import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.Layers;

public class AbstractLayerBasedResource {

	/**
	 * Returns the layer with the specific id
	 * 
	 * @param layers
	 * @param layerId
	 * @return the layer with the id
	 * @throws NotFoundException
	 *             If there is no layer with the specified id
	 */
	protected Layer getLayer(Layers layers, String layerId)
			throws NotFoundException {
		try {
			return layers.getLayer(layerId);
		} catch (IllegalArgumentException e) {
			throw new NotFoundException("No layer with the id: " + layerId);
		}
	}

}
