package net.kaczmarzyk.moose.core.document;


public class Cell { // FIXME do i really need cells? maybe DataObject is enough

	private DataObject value;
	private CellAddress address;
	
	
	public Cell(CellAddress coords) {
		this.address = coords;
	}
	
	public DataObject getValue() {
		if (value == null) {
			value = new NullObject(new ObjectAddress(address, Path.IN_PLACE)); //TODO factory method for inplace address
		}
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

	public void put(DataObject obj, Path path) {
		getValue().setProperty(path, obj);
	}

	public void put(DataObject obj) {
		value = obj;
	}
}
