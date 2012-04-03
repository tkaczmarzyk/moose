package net.kaczmarzyk.moose.core.document;

import java.util.List;


public class Coordinates {

	private List<Coordinate<?>> coords;
	
	public Coordinates(List<Coordinate<?>> coords) {
		this.coords = coords;
	}
	
	public void add(Coordinate<?> coord) {
		coords.add(coord);
	}

	public int size() {
		return coords.size();
	}
}
