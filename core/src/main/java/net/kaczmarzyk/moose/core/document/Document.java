package net.kaczmarzyk.moose.core.document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Document {

	private DimensionsList dimensions;
	
	private Map<List<Coordinate<?>>, Cell> cells;
	
	
	public Document() {
		dimensions = new DimensionsList();
		cells = new HashMap<>();
	}
	
	public <T> void addDimension(Dimension<T> dimension, Coordinate<T> currentCoordinate) {
		dimensions.add(dimension);
		Map<List<Coordinate<?>>, Cell> newCells = new HashMap<>();
		for (Map.Entry<List<Coordinate<?>>, Cell> entry : cells.entrySet()) {
			List<Coordinate<?>> newKey = entry.getKey();
			newKey.add(currentCoordinate);
			newCells.put(newKey, entry.getValue());
		}
		cells = newCells;
	}
	
	public Cell getCell(List<Coordinate<?>> coords) {
		if (coords.size() != dimensions.size()) {
			throw new IllegalArgumentException("invalid number of coordinates, required: "
					+ dimensions.size()	+ " was: " + coords.size());
		}
		Cell result = cells.get(coords);
		if (result == null) {
			result = new Cell();
			cells.put(coords, result);
		}
		return result;
	}
	
	public Cell getCell(Coordinate<?>... partialCoords) {
		List<Coordinate<?>> coords = dimensions.inferFullCoords(partialCoords, this);
		return getCell(coords);
	}
	
}
