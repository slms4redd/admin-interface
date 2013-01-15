package org.fao.unredd.restapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.fao.unredd.resources.LayerListResource;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.JerseyTest;

public class RestTest extends JerseyTest {

	public RestTest() {
		super("org.fao.unredd.resources");
	}

	@Before
	public void setup() {
		LayerListResource.model.clear();
	}

	@Test
	public void testHelloWorld() {
		WebResource webResource = resource();
		ClientResponse responseMsg = webResource.path("layers").get(
				ClientResponse.class);
		assertEquals(ClientResponse.Status.OK,
				responseMsg.getClientResponseStatus());
		fail("Check that it actually contains some layers");
	}
}
