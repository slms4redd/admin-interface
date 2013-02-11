package org.fao.unredd.api;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.geosolutions.geostore.core.model.Attribute;
import it.geosolutions.geostore.core.model.Resource;
import it.geosolutions.geostore.core.model.enums.DataType;
import it.geosolutions.geostore.services.dto.search.SearchFilter;
import it.geosolutions.geostore.services.rest.GeoStoreClient;
import it.geosolutions.geostore.services.rest.model.ResourceList;

import java.util.ArrayList;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
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

	protected void mockGeostoreSearchAnswer(ResourceList resourceList) {
		when(
				geostoreClient.searchResources(any(SearchFilter.class),
						anyInt(), anyInt(), anyBoolean(), anyBoolean()))
				.thenReturn(resourceList);
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

}
