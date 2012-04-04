package net.kaczmarzyk.moose.core.document;



public class ObjectAddress { // FIXME add sheet // FIXME handle relative coords //FIXME handle property paths

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

}
