package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.List;

public class DimensionsList {

	private List<Dimension<?>> dimensions = new ArrayList<>();

	
	public DimensionsList() {
		
	}
	
	public Dimension<?> get(int index) {
		return dimensions.get(index);
	}
	
	public int size() {
		return dimensions.size();
	}
	
	public void add(Dimension<?> dimension) {
		dimensions.add(dimension);
	}

	public List<Coordinate<?>> inferFullCoords(Coordinate<?>[] partialCoords, Document doc) {
		List<Coordinate<?>> coords = new ArrayList<Coordinate<?>>();
		for (int i = 0; i < dimensions.size(); i++) {
			coords.add(i < partialCoords.length ? partialCoords[i] : dimensions.get(i).getDefaultCoordinate(doc));
		}
		return coords;
	}
}
