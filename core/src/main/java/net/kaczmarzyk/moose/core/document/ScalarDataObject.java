package net.kaczmarzyk.moose.core.document;

import java.util.Collections;
import java.util.List;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class ScalarDataObject extends AbstractDataObject {

	private Object value;
	
	
	public ScalarDataObject(Document doc, Object explicitValue) {
		super(doc);
		value = explicitValue;
	}
	
	@Override
	public List<String> getPropertyNames() {
		return Collections.emptyList();
	}

	@Override
	public DataObject getProperty(String propName) {
		return null;// TODO "value" property?
	}
	
	@Override
	public <T> T accept(DataProcessor<T> visitor) {
		return visitor.process(this);
	}
	
	public Object getValue() {
		return value;
	}
}
