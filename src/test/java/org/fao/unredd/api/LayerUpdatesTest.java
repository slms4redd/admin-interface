package org.fao.unredd.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.services.rest.model.ResourceList;
import it.geosolutions.unredd.geostore.model.UNREDDLayerUpdate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fao.unredd.api.json.LayerUpdateRepresentation;
import org.fao.unredd.api.json.LayerUpdatesResponseRoot;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class LayerUpdatesTest extends AbstractRestTest {

	@Test
	public void testGetLayerUpdate() {
		ResourceList resourceList = mockResourceList(
				mockLayerUpdate(0L, "layerupdate0", "forest_mask", "2000",
						null, null, "false"),
				mockLayerUpdate(1L, "layerupdate1", "forest_mask", "2010",
						null, null, "true"));
		mockGeostoreSearchAnswer(resourceList);

		ClientResponse response = getLayerUpdatesOk();

		LayerUpdatesResponseRoot root = response
				.getEntity(LayerUpdatesResponseRoot.class);
		Iterator<LayerUpdateRepresentation> layerIterator = root
				.getLayerUpdates().iterator();
		assertTrue(layerIterator.hasNext());
		LayerUpdateRepresentation layer = layerIterator.next();
		assertEquals("0", layer.getId());
		assertEquals("layerupdate0", layer.getName());

		assertTrue(layerIterator.hasNext());
		layer = layerIterator.next();
		assertEquals("1", layer.getId());
		assertEquals("layerupdate1", layer.getName());

		assertFalse(layerIterator.hasNext());
	}

	@Test
	@Ignore
	public void testGetLayerUpdatesFromNonExistentLayer() throws Exception {
		fail();
	}

	private Resource mockLayerUpdate(long id, String name, String layerName,
			String year, String month, String day, String published) {
		Resource resource = mock(Resource.class);
		when(resource.getId()).thenReturn(id);
		when(resource.getName()).thenReturn(name);
		List<Attribute> attributes = createAttributeList(layerName, year,
				month, day, published);
		when(resource.getAttribute()).thenReturn(attributes);
		return resource;
	}

	private List<Attribute> createAttributeList(String layerName, String year,
			String month, String day, String published) {
		List<Attribute> ret = new ArrayList<Attribute>();
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.LAYER.getName(),
				layerName));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.YEAR.getName(), year));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.MONTH.getName(),
				month));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.DAY.getName(), day));
		ret.add(newAttribute(UNREDDLayerUpdate.Attributes.PUBLISHED.getName(),
				published));
		return ret;
	}

	private ClientResponse getLayerUpdatesOk() {
		WebResource webResource = resource();
		ClientResponse response = webResource.path("layers/1/layerupdates")
				.get(ClientResponse.class);
		assertEquals(ClientResponse.Status.OK,
				response.getClientResponseStatus());
		return response;
	}

}
