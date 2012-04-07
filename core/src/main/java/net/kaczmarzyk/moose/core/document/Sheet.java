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
		this.dimensions.add(new ColumnDimension());
		this.dimensions.add(new RowDimension());
		this.cells = new HashMap<>();
		this.name = "Sheet" + (doc.getSheets().size() + 1);
		this.doc = doc;
	}
	
	public <T> void addDimension(Dimension<T> dimension, Coordinate<T> currentCoordinate) {
		dimensions.add(dimension);
		Map<CellAddress, Cell> newCells = new HashMap<>();
		for (Map.Entry<CellAddress, Cell> entry : cells.entrySet()) {
			CellAddress newKey = entry.getKey();
			newKey.add(currentCoordinate);
			newCells.put(newKey, entry.getValue());
		}
		cells = newCells;
	}
	
	public Cell getCell(CellAddress coords) {
		if (coords.size() != dimensions.size()) {
			throw new IllegalArgumentException("invalid number of coordinates, required: "
					+ dimensions.size()	+ " was: " + coords.size());
		}
		Cell result = cells.get(coords);
		if (result == null) {
			result = new Cell(coords);
			cells.put(coords, result);
		}
		return result;
	}
	
	public Cell getCell(Coordinate<?>... partialCoords) {
		CellAddress coords = dimensions.inferAddress(partialCoords, this);
		return getCell(coords);
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
}
