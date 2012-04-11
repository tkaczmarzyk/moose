package net.kaczmarzyk.moose.core.document;


public class Cell { // FIXME do i really need cells? maybe DataObject is enough

	private DataObject value;
	private CellAddress address;
	
	
	public Cell(CellAddress coords) {
		this.address = coords;
	}
	
	public DataObject getValue() {
		if (value == null) {
			value = new MapData();
			value.placedAtAddress(address.objectAddress());
		}
		return value;
	}
	
	public void clear() {
		value = null;
	}

	public CellAddress getAddress() {
		return address;
	}

	public void put(DataObject obj, Path path) {
		obj.placedAtAddress(address.objectAddress(path));
		if (path.isInPlace()) {
			value = obj;
		} else {
			getValue().setProperty(path, obj);
		}
	}

	public void put(DataObject obj) {
		put(obj, Path.IN_PLACE);
	}
}
