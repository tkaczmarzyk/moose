package net.kaczmarzyk.moose.core.document;

import com.google.common.base.Objects;


public class ObjectAddress {

	private CellAddress cellAddr;
	private Path path;
	
	
	public ObjectAddress(CellAddress cellAddr, Path path) {
		this.cellAddr = cellAddr;
		this.path = path;
	}

	public CellAddress getCellAddr() {
		return cellAddr;
	}

	public Path getPath() {
		return path;
	}

	public DataObject getObject() {
		return path.getValue(cellAddr.getCell().getValue());
	}

	public void put(DataObject obj) {
		cellAddr.getCell().put(obj, path);
	}

	public ObjectAddress withExtendedPath(String propName) {
		ObjectAddress copy = copy();
		copy.path = copy.path.with(propName);
		return copy;
	}

	private ObjectAddress copy() {
		return new ObjectAddress(cellAddr, path);
	}

	public ObjectAddress absolute(CellAddress referenceCellAddr) {
		ObjectAddress copy = copy();
		copy.cellAddr = cellAddr.absolute(referenceCellAddr);
		return copy;
	}
	
	@Override
	public String toString() {
		return cellAddr + "#" + path;
	}

	public static ObjectAddress inPlace(CellAddress cellAddr) { //TODO move to cellAddr as instance method
		return new ObjectAddress(cellAddr, Path.IN_PLACE);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ObjectAddress) {
			ObjectAddress that = (ObjectAddress) obj;
			return Objects.equal(this.path, that.path) 
					&& Objects.equal(this.cellAddr, that.cellAddr);
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(cellAddr, path);
	}
}
