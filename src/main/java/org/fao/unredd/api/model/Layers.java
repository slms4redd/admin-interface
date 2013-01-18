package org.fao.unredd.api.model;

import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.json.LayerRepresentation;

public interface Layers {

	/**
	 * Return a list of the layers in the group
	 * 
	 * @return
	 */
	Iterable<LayerRepresentation> getJSON();

	/**
	 * Adds a layer to the backend accessed by this implementation and returns
	 * the created layer
	 * 
	 * @param addLayerRequest
	 * @return
	 */
	Layer addLayer(AddLayerRequest addLayerRequest);

	/**
	 * Get the layer with the specified ID
	 * 
	 * @param id
	 *            ID of the layer to be retrieved
	 * @return
	 * @throws IllegalArgumentException
	 *             If the specified id does not correspond to any layer
	 */
	public Layer getLayer(String id) throws IllegalArgumentException;

}
