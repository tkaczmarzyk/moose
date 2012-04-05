package net.kaczmarzyk.moose.core.document;

import java.util.Collections;
import java.util.List;

import net.kaczmarzyk.moose.core.processor.DataProcessor;
import net.kaczmarzyk.moose.support.utils.ObjectUtil;


public class Scalar<T> implements DataObject {

	private T value;
	
	
	public Scalar(T explicitValue) {
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

	@Override
	public Scalar<T> copy() {
		return new Scalar<T>(ObjectUtil.copy(value));
	}

	@Override
	public void setProperty(Path path, DataObject obj) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void refresh(CellAddress addr) {
	}

	@Override
	public void placedInCell(CellAddress address) {
	}
}
