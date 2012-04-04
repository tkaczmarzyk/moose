package net.kaczmarzyk.moose.core.document;



public class Cell {

	private DataObject value;
	
	
	public Cell() {
	}
	
	public DataObject getValue() {
		return value;
	}
	
	public void clear() {
		value = null;
	}

	public void setValue(DataObject value) {
		this.value = value;
	}
}
