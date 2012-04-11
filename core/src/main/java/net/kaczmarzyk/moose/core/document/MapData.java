package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class MapData implements DataObject {

	private ObjectAddress address;
	private Map<String, DataObject> properties;

	
	public MapData() {
		this.properties = new LinkedHashMap<>();
	}
	
	@Override
	public List<String> getPropertyNames() {
		return new ArrayList<>(properties.keySet());
	}

	@Override
	public DataObject getProperty(String propName) {
		DataObject result = properties.get(propName);
		if (result == null) {
			result = new MapData();
			result.placedAtAddress(address.withExtendedPath(propName));
			properties.put(propName, result);
		}
		return result;
	}
	
	@Override
	public <T> T accept(DataProcessor<T> visitor) {
		return visitor.process(this);
	}
	
	public void put(String propName, DataObject value) {
		properties.put(propName, value);
	}

	@Override
	public DataObject copy() {
		MapData copy = new MapData();
		for (Map.Entry<String, DataObject> property : properties.entrySet()) {
			copy.put(property.getKey(), property.getValue().copy());
		}
		return copy;
	}

	@Override
	public void setProperty(Path path, DataObject obj) {
		if (path.isSingleProperty()) {
			put(path.getFirstProperty(), obj);
		}
		else {
			getProperty(path.getFirstProperty()).setProperty(path.step(), obj);
		}
	}

	@Override
	public void refresh(CellAddress addr) {
		for (Map.Entry<String, DataObject> property : properties.entrySet()) {
			property.getValue().refresh(addr);
		}
	}

	@Override
	public void placedAtAddress(ObjectAddress objAddr) {
		this.address = objAddr;
		for (Map.Entry<String, DataObject> property : properties.entrySet()) {
			property.getValue().placedAtAddress(new ObjectAddress(address.getCellAddr(), Path.of(property.getKey()))); // TODO factory method
		}
	}
	
	@Override
	public int hashCode() {
		return properties.hashCode(); // FIXME what about address?
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof MapData) {
			MapData that = (MapData) obj;
			return this.properties.equals(that.properties);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "MapData" + properties;
	}
}
