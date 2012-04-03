package net.kaczmarzyk.moose.core.document;

import java.util.HashMap;
import java.util.Map;


public class Sheet {

	private String name;
	
	private Document doc;
	
	private DimensionsList dimensions;
	
	private Map<Coordinates, Cell> cells;
	
	
	public Sheet(Document doc) {
		this.dimensions = new DimensionsList();
		this.dimensions.add(new ColumnDimension());
		this.dimensions.add(new RowDimension());
		this.cells = new HashMap<>();
		this.name = "Sheet " + (doc.getSheets().size() + 1);
		this.doc = doc;
	}
	
	public <T> void addDimension(Dimension<T> dimension, Coordinate<T> currentCoordinate) {
		dimensions.add(dimension);
		Map<Coordinates, Cell> newCells = new HashMap<>();
		for (Map.Entry<Coordinates, Cell> entry : cells.entrySet()) {
			Coordinates newKey = entry.getKey();
			newKey.add(currentCoordinate);
			newCells.put(newKey, entry.getValue());
		}
		cells = newCells;
	}
	
	public Cell getCell(Coordinates coords) {
		if (coords.size() != dimensions.size()) {
			throw new IllegalArgumentException("invalid number of coordinates, required: "
					+ dimensions.size()	+ " was: " + coords.size());
		}
		Cell result = cells.get(coords);
		if (result == null) {
			result = new Cell(doc);
			cells.put(coords, result);
		}
		return result;
	}
	
	public Cell getCell(Coordinate<?>... partialCoords) {
		Coordinates coords = dimensions.inferFullCoords(partialCoords, this);
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
}
