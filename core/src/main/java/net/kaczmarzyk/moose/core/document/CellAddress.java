package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CellAddress {

	private List<Coordinate<?>> coords;
	
	
	public CellAddress(List<Coordinate<?>> coords) {
		this.coords = coords;
	}
	
	public CellAddress(Coordinate<String> columnCoord, Coordinate<Integer> rowCoord) {
		this(new ArrayList<Coordinate<?>>(Arrays.asList(columnCoord, rowCoord)));
	}

	public int size() {
		return coords.size();
	}
	
	public void add(Coordinate<?> coord) {
		coords.add(coord);
	}
	
	@Override
	public int hashCode() {
		return coords.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CellAddress) {
			return coords.equals(((CellAddress)obj).coords);
		} else {
			return false;
		}
	}
}
