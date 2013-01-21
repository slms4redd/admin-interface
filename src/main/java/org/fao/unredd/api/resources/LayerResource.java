package org.fao.unredd.api.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.model.Layers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/layers/{id}")
public class LayerResource {

	@Autowired
	private Layers layers;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public LayerRepresentation asJSON(@PathParam("id") String id) {
		try {
			return layers.getLayer(id).getJSON();
		} catch (IllegalArgumentException e) {
			throw new NotFoundException("No layer with the ID: " + id);
		}
	}

}
