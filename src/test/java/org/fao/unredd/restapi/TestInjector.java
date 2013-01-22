package org.fao.unredd.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Class used to inject dependencies in test cases. The purpose is to obtain an
 * instance to a {@link AutowireCapableBeanFactory} so this class is a
 * {@link Component} that will be autowired. Any of the instances of this class
 * (typically one) is kept as a singleton in order to keep the bean factory and
 * be able to expose the injecting method
 * 
 * @author fergonco
 */
@Component
public class TestInjector {

	private static TestInjector singleton;

	@Autowired
	private AutowireCapableBeanFactory beanFactory;

	public TestInjector() {
		singleton = this;
	}

	/**
	 * Injects the dependencies on the test case
	 * 
	 * @param obj
	 */
	public static void wire(Object obj) {
		singleton.beanFactory.autowireBean(obj);
	}
}
