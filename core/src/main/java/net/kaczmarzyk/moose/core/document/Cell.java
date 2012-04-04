package net.kaczmarzyk.moose.core.document;



public class Cell {

	private DataObject value;
	private Coordinates coords;
	
	
	public Cell(Coordinates coords) {
		this.coords = coords;
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
	
	public Coordinates getCoordinates() {
		return coords;
	}
}
