package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MapDataObject implements DataObject {

	private Map<String, Data<?>> properties;

	public MapDataObject() {
		this.properties = new HashMap<>();
	}
	
	@Override
	public List<String> getPropertyNames() {
		return new ArrayList<>(properties.keySet());
	}

	@Override
	public Data<?> getProperty(String propName) {
		return properties.get(propName);
	}
}
