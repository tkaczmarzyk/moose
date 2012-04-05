package net.kaczmarzyk.moose.core.document;



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
		return cellAddr.getCell().getValue();
	}

	public void put(DataObject obj) {
		cellAddr.getCell().put(obj, path);
	}

}
