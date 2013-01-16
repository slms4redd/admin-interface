package org.fao.unredd.resources;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlRootElement;

public class BadRequestException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(String[] errors) {
		super(Response.status(Response.Status.BAD_REQUEST)
				.entity(new JaxbList(errors)).type(MediaType.APPLICATION_JSON)
				.build());
	}

	@XmlRootElement
	protected static class JaxbList {
		private String[] list;

		public JaxbList() {
		}

		public JaxbList(String[] list) {
			this.list = list;
		}

		public String[] getErrorList() {
			return list;
		}

		public void setErrorList(String[] list) {
			this.list = list;
		}
	}

}
