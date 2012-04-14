package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DimensionsList implements Iterable<Dimension<?>> {

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

	public CellAddress inferAddress(Coordinate[] partialCoords, Sheet sheet) {
		List<Coordinate> coords = new ArrayList<Coordinate>();
		for (int i = 0; i < dimensions.size(); i++) {
			coords.add(i < partialCoords.length ? partialCoords[i] : dimensions.get(i).extend());
		}
		return new CellAddress(sheet, coords);
	}

	@Override
	public Iterator<Dimension<?>> iterator() {
		return dimensions.iterator();
	}
}
