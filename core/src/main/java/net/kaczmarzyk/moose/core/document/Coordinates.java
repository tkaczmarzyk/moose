package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Coordinates { // FIXME add sheet // FIXME handle relative coords //FIXME handle property paths

	private List<Coordinate<?>> coords;
	
	public Coordinates(List<Coordinate<?>> coords) {
		this.coords = coords;
	}
	
	public Coordinates(Coordinate<?>... coords) {
		this.coords = new ArrayList<>(Arrays.asList(coords));
	}
	
	public void add(Coordinate<?> coord) {
		coords.add(coord);
	}

	public int size() {
		return coords.size();
	}
	
	@Override
	public int hashCode() {
		return coords.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coordinates) {
			return coords.equals(((Coordinates)obj).coords);
		} else {
			return false;
		}
	}
}
