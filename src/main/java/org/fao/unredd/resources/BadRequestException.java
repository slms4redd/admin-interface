package org.fao.unredd.resources;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class BadRequestException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(List<String> errors) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(errors)
				.type(MediaType.APPLICATION_JSON).build());
	}

}
