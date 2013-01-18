package org.fao.unredd.api.resources;

import java.net.URI;
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
import org.fao.unredd.api.json.AddLayerRequest;
import org.fao.unredd.api.json.LayerRepresentation;
import org.fao.unredd.api.json.ResponseRoot;
import org.fao.unredd.api.model.Layer;
import org.fao.unredd.api.model.Layers;

@Path("/layers")
public class LayerListResource {

	public static Layers layers;

	@Context
	private UriInfo uriInfo;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseRoot asJSON() {
		return new ResponseRoot("layers", layers.getJSON());
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addLayer(AddLayerRequest layerRequest) {
		List<String> errors = new ArrayList<String>();
		if (layerRequest.getName() == null) {
			errors.add("name cannot be null");
		}
		if (layerRequest.getType() == null) {
			errors.add("type cannot be null");
		}
		if (errors.size() > 0) {
			throw new BadRequestException(errors);
		}

		Layer layer = layers.addLayer(layerRequest);
		UriBuilder uriBuilder = UriBuilder.fromUri(uriInfo.getRequestUri());
		uriBuilder.path("{id}");
		LayerRepresentation jsonLayer = layer.getJSON();
		char[] chars = Hex.encodeHex(jsonLayer.getETag().getBytes());
		String etagString = new String(chars);
		EntityTag etag = new EntityTag(etagString);
		URI location = uriBuilder.build(jsonLayer.getId());
		return Response.created(location).tag(etag).entity(jsonLayer)
				.type(MediaType.APPLICATION_JSON).build();
	}
}
