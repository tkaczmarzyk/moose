package net.kaczmarzyk.moose.core.document;

import java.util.Collections;
import java.util.List;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class Scalar<T> extends AbstractDataObject {

	private T value;
	
	
	public Scalar(Document doc, T explicitValue) {
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
	public <K> K accept(DataProcessor<K> visitor) {
		return visitor.process(this);
	}
	
	public T getValue() {
		return value;
	}

	@Override
	public DataObject getProperty(Path path) {
		return path.getPropertyChain().isEmpty() ? this : null;
	}
}
