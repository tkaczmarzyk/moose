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

	@Override
	public DataObject copy() {
		MapData copy = new MapData(sheet);
		for (Map.Entry<String, DataObject> property : properties.entrySet()) {
			copy.put(property.getKey(), property.getValue().copy());
		}
		return copy;
	}

	@Override
	public void setProperty(Path path, DataObject obj) {
		DataObject target = this;
		for (String property : path.getObjectPath().getPropertyChain()) {
			target = target.getProperty(property);
		}
		((MapData)target).properties.put(path.getLastProperty().getPropertyChain().get(0), obj); // FIXME casting // FIXME resolving last propName
	}

	@Override
	public void refresh(CellAddress addr) {
		for (Map.Entry<String, DataObject> property : properties.entrySet()) {
			property.getValue().refresh(addr);
		}
	}
}
