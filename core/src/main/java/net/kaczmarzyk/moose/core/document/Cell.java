package net.kaczmarzyk.moose.core.document;


public class Cell {

	private DataObject value;
	private CellAddress address;
	
	
	public Cell(CellAddress coords) {
		this.address = coords;
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
	
	public CellAddress getAddress() {
		return address;
	}
}
