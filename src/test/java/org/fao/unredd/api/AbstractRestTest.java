package org.fao.unredd.api;

import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.core.model.enums.DataType;
import it.geosolutions.geostore.services.dto.search.AndFilter;
import it.geosolutions.geostore.services.dto.search.CategoryFilter;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.ResourceList;
import it.geosolutions.unredd.geostore.model.UNREDDCategories;
import it.geosolutions.unredd.geostore.model.UNREDDLayer;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.fao.unredd.api.model.LayerType;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class AbstractRestTest extends JerseyTest {

	@Autowired
	protected GeoStoreClient geostoreClient;

	public AbstractRestTest() {
		super(
				new WebAppDescriptor.Builder()
						.contextParam("contextConfigLocation",
								"classpath:/adminTestApplicationContext.xml")
						.initParam("com.sun.jersey.config.property.packages",
								"org.fao.unredd.api.resources;org.codehaus.jackson.jaxrs")
						.contextPath("/admin")
						.servletClass(SpringServlet.class)
						.contextListenerClass(ContextLoaderListener.class)
						.requestListenerClass(RequestContextListener.class)
						.clientConfig(
								new DefaultClientConfig(
										JacksonJsonProvider.class)).build());

		TestInjector.wire(this);
	}

	protected Attribute newAttribute(String attributeName, String value) {
		Attribute attribute = new Attribute();
		attribute.setName(attributeName);
		attribute.setTextValue(value);
		attribute.setType(DataType.STRING);
		return attribute;
	}

	protected Attribute newAttribute(String attributeName, Double value) {
		Attribute attribute = new Attribute();
		attribute.setName(attributeName);
		attribute.setNumberValue(value);
		attribute.setType(DataType.NUMBER);
		return attribute;
	}

	protected void mockLayerUpdateSearchAnswer(ResourceList updatesResponse) {
		when(
				geostoreClient.searchResources(Matchers
						.argThat(new LayerCategorySearchMatcher(
								UNREDDCategories.LAYERUPDATE)), anyInt(),
						anyInt(), anyBoolean(), anyBoolean())).thenReturn(
				updatesResponse);
	}

	protected void mockLayerSearchAnswer(ResourceList layerResponse) {
		when(
				geostoreClient.searchResources(Matchers
						.argThat(new LayerCategorySearchMatcher(
								UNREDDCategories.LAYER)), anyInt(), anyInt(),
						anyBoolean(), anyBoolean())).thenReturn(layerResponse);
	}

	protected ResourceList mockResourceList(Resource... resources) {
		ResourceList resourceList = mock(ResourceList.class);
		ArrayList<Resource> list = new ArrayList<Resource>();
		for (Resource resource : resources) {
			list.add(resource);
		}
		when(resourceList.getList()).thenReturn(list);
		return resourceList;
	}

	protected Resource mockLayer(long id, String name, LayerType layerType) {
		Resource resource = mock(Resource.class);
		when(resource.getId()).thenReturn(id);
		when(resource.getName()).thenReturn(name);
		List<Attribute> attributes = createLayerAttributeList(layerType, 1, 2,
				1, 2, 1, 1, "OrigDataDestPath", "DissMosaicPath", "MosaicPath");
		when(resource.getAttribute()).thenReturn(attributes);
		return resource;
	}

	private List<Attribute> createLayerAttributeList(LayerType layerType,
			double x0, double x1, double y0, double y1, double pixelHeight,
			double pixelWidth, String origDataDestPath, String dissMosaicPath,
			String mosaicPath) {
		List<Attribute> ret = new ArrayList<Attribute>();
		ret.add(newAttribute(UNREDDLayer.Attributes.LAYERTYPE.getName(),
				layerType.toString()));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERY1.getName(), y1));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERY0.getName(), y0));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERX1.getName(), x1));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERX0.getName(), x0));
		ret.add(newAttribute(
				UNREDDLayer.Attributes.RASTERPIXELHEIGHT.getName(), pixelHeight));
		ret.add(newAttribute(UNREDDLayer.Attributes.RASTERPIXELWIDTH.getName(),
				pixelWidth));
		ret.add(newAttribute(UNREDDLayer.Attributes.ORIGDATADESTPATH.getName(),
				origDataDestPath));
		ret.add(newAttribute(UNREDDLayer.Attributes.DISSMOSAICPATH.getName(),
				dissMosaicPath));
		ret.add(newAttribute(UNREDDLayer.Attributes.MOSAICPATH.getName(),
				mosaicPath));

		return ret;
	}

	private final class LayerCategorySearchMatcher extends
			BaseMatcher<AndFilter> {
		private UNREDDCategories category;

		public LayerCategorySearchMatcher(UNREDDCategories category) {
			super();
			this.category = category;
		}

		@Override
		public boolean matches(Object item) {
			SearchFilter searchFilter = (SearchFilter) item;
			if (item instanceof AndFilter) {
				List<SearchFilter> filters = ((AndFilter) item).getFilters();
				for (SearchFilter filter : filters) {
					if (matches(filter)) {
						return true;
					}
				}
			} else if (searchFilter instanceof CategoryFilter) {
				if (category.getName().equals(
						((CategoryFilter) searchFilter).getName())) {
					return true;
				}
			}

			return false;
		}

		@Override
		public void describeTo(Description description) {
			description.appendText("layer search");
		}
	}

}
