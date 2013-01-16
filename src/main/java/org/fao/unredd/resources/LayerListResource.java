package org.fao.unredd.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.binary.Hex;

@Path("/layers")
public class LayerListResource {

	public static Model model;

	@Context
	private UriInfo uriInfo;

	static {
		model = new Model();
		// model.addLayer(new Layer("0", "administrative areas",
		// LayerType.VECTOR));
		// model.addLayer(new Layer("1", "forest mask", LayerType.RASTER));
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Layer[] asJSON() {
		return model.getLayers();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLayer(Layer layer) {
		List<String> errors = new ArrayList<String>();
		if (layer.id != null) {
			errors.add("id is assigned by the server. Must be null");
		}
		if (layer.name == null) {
			errors.add("name cannot be null");
		}
		if (layer.type == null) {
			errors.add("type cannot be null");
		}
		if (errors.size() > 0) {
			throw new BadRequestException(errors);
		}

		String id = model.addLayer(layer);
		UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
		uriBuilder.path("{id}");
		char[] chars = Hex.encodeHex(layer.getETag().getBytes());
		String etagString = new String(chars);
		EntityTag etag = new EntityTag(etagString);
		return Response.created(uriBuilder.build(id)).tag(etag).entity(layer)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
