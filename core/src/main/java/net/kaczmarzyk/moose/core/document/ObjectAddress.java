package net.kaczmarzyk.moose.core.document;

import net.kaczmarzyk.moose.core.common.Copyable;



public class ObjectAddress implements Copyable<ObjectAddress> {

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
		return cellAddr.getCell().getValue();
	}

	public void put(DataObject obj) {
		cellAddr.getCell().put(obj, path);
	}

	public ObjectAddress withExtendedPath(String propName) {
		ObjectAddress copy = copy();
		copy.path = copy.path.extended(propName);
		return copy;
	}

	@Override
	public ObjectAddress copy() {
		return new ObjectAddress(cellAddr, path);
	}

}
