package org.fao.unredd.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/layers/{id}")
public class LayerResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Layer asJSON(@PathParam("id") String id) {
		try {
			return LayerListResource.model.getLayer(id);
		} catch (IllegalArgumentException e) {
			throw new NotFoundException("No layer with the id: " + id);
		}
	}

}
