package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class MapData extends AbstractDataObject {

	private Map<String, DataObject> properties;

	
	public MapData(Sheet sheet) {
		super(sheet);
		this.properties = new LinkedHashMap<>();
	}
	
	@Override
	public List<String> getPropertyNames() {
		return new ArrayList<>(properties.keySet());
	}

	@Override
	public DataObject getProperty(String propName) {
		return properties.get(propName);
	}
	
	@Override
	public <T> T accept(DataProcessor<T> visitor) {
		return visitor.process(this);
	}
	
	public void put(String propName, DataObject value) {
		properties.put(propName, value);
	}

}
