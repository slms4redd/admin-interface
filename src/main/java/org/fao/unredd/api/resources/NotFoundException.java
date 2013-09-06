package org.fao.unredd.api.resources;

import java.util.Collections;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(Response.status(Response.Status.NOT_FOUND)
				.entity(Collections.singletonList(message))
				.type(MediaType.APPLICATION_JSON).build());
	}

}
