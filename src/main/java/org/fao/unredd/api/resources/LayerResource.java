package org.fao.unredd.api.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.fao.unredd.api.json.AddLayerRequest;
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

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLayer(@PathParam("id") String id,
			AddLayerRequest layerRequest) {
		List<String> errors = layerRequest.validate();
		if (errors.size() > 0) {
			throw new BadRequestException(errors);
		}
		try {
			layers.updateLayer(id, layerRequest);
		} catch (IllegalArgumentException e) {
			throw new NotFoundException(e.getMessage());
		}

		return Response.noContent().build();
	}

	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteLayer(@PathParam("id") String id) {
		try {
			layers.deleteLayer(id);
		} catch (IllegalArgumentException e) {
			throw new NotFoundException(e.getMessage());
		}
		return Response.noContent().build();
	}
}
