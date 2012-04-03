package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.List;


public class Cell {

	private List<DataObject> values;

	
	public Cell() {
		this.values = new ArrayList<>();
	}
	
	public List<DataObject> getValues() {
		return values;
	}

	public void addValue(DataObject value) {
		values.add(value);
	}
	
	public void setValues(List<DataObject> values) {
		this.values = values;
	}
}
