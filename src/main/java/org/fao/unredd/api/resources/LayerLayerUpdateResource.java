package org.fao.unredd.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.model.LayerUpdates;
import org.fao.unredd.api.model.Layers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/layers/{layerId}/layerupdates/{updateId}")
public class LayerLayerUpdateResource extends AbstractLayerBasedResource {

	@Autowired
	private Layers layers;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public LayerUpdateRepresentation asJSON(
			@PathParam("layerId") String layerId,
			@PathParam("updateId") String updateId) {
		LayerUpdates updates = getLayer(layers, layerId).getLayerUpdates();
		try {
			return updates.getLayerUpdate(updateId).getJSON();
		} catch (IllegalArgumentException e) {
			throw new NotFoundException("No layer with the ID: " + layerId);
		}
	}

}
