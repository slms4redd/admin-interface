package org.fao.unredd.api.json;

import java.util.HashMap;

/**
 * This class is only used to get the desired output JSON format mapped
 * automatically by jackson
 * 
 * @author fergonco
 */
public class ResponseRoot extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;

	public ResponseRoot(String attributeName, Object value) {
		super(1);
		put(attributeName, value);
	}
}
