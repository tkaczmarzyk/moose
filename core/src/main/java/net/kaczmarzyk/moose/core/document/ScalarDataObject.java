package net.kaczmarzyk.moose.core.document;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ScalarDataObject implements DataObject {

	private static final String PROP_NAME = "value";
	private static final List<String> propNames = Collections.unmodifiableList(Arrays.asList(PROP_NAME));
	
	private Data<?> value;
	
	
	public ScalarDataObject(Object implicitValue) {
		value = new ExplicitData<Object>(implicitValue);
	}
	
	@Override
	public List<String> getPropertyNames() {
		return propNames;
	}

	@Override
	public Data<?> getProperty(String propName) {
		if (PROP_NAME.equals(propName)) {
			return value;
		} else {
			return null;
		}
	}

}
