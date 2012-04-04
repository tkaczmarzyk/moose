package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class MapDataObject extends AbstractDataObject {

	private Map<String, DataObject> properties;

	
	public MapDataObject(Document doc) {
		super(doc);
		this.properties = new HashMap<>();
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

}
