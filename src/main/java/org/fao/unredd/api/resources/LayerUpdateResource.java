package org.fao.unredd.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.model.LayerUpdates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/layerupdates/{updateId}")
public class LayerUpdateResource extends AbstractLayerBasedResource {

	@Autowired
	private LayerUpdates layerUpdates;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public LayerUpdateRepresentation asJSON(
			@PathParam("updateId") String updateId) {
		try {
			return layerUpdates.getLayerUpdate(updateId).getJSON();
		} catch (IllegalArgumentException e) {
			throw new NotFoundException("No update with the ID: " + updateId);
		}
	}

}
