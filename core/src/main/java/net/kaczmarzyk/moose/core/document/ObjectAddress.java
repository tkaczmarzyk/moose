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
		return cellAddr.getCell().getValue().getProperty(path);
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

	public static ObjectAddress inPlace(CellAddress cellAddr) {
		return new ObjectAddress(cellAddr, Path.IN_PLACE);
	}
}
