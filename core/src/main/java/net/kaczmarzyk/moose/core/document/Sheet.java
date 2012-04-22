package net.kaczmarzyk.moose.core.document;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class Sheet {

	private String name;
	
	private Document doc;
	
	private DimensionsList dimensions;
	
	private Map<CellAddress, Cell> cells;
	
	
	public Sheet(Document doc) {
		this.dimensions = new DimensionsList();
		this.dimensions.add(SpatialDimension.cols());
		this.dimensions.add(SpatialDimension.rows());
		this.cells = new HashMap<>();
		this.name = "Sheet" + (doc.getSheets().size() + 1);
		this.doc = doc;
	}
	
	public void addDimension(Dimension<?> dimension) {
		dimensions.add(dimension);
		Map<CellAddress, Cell> newCells = new HashMap<>();
		Coordinate currentCoordinate = dimension.extend();
		for (Map.Entry<CellAddress, Cell> entry : cells.entrySet()) {
			CellAddress newKey = entry.getKey();
			newKey.add(currentCoordinate);
			newCells.put(newKey, entry.getValue());
		}
		cells = newCells;
	}
	
	public Cell getCell(Coordinate...coords) {
		return getCell(new CellAddress(this, coords));
	}
	
	Cell getCell(CellAddress coords) {
		Cell result = cells.get(coords);
		if (result == null) {
			result = new Cell(coords);
			cells.put(coords, result);
		}
		return result;
	}
	
	boolean isEmpty(CellAddress address) {
		return cells.get(address) == null;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Document getDoc() {
		return doc;
	}
	
	public DimensionsList getDimensions() {
		return dimensions;
	}

	public Collection<Cell> getCells() {
		return cells.values();
	}
	
	@SuppressWarnings("unchecked")
	public Dimension<String> cols() {
		return (Dimension<String>) dimensions.get(0); // TODO move rows/cols outside collection, since they are not optional
	}
	
	@SuppressWarnings("unchecked")
	public Dimension<Integer> rows() {
		return (Dimension<Integer>) dimensions.get(1);
	}
	
	@Override
	public String toString() {
		return name;
	}

	void put(CellAddress cellAddress, DataObject obj) {
		CellAddress fullAddress = dimensions.inferFullAddress(cellAddress, this);
		getCell(fullAddress).put(obj);
	}

	public DataObject get(CellAddress address) {
		CellAddress visibleAddress = dimensions.inferVisibleAddress(address, this);
		return getCell(visibleAddress).getValue();
	}

}
