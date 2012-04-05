package net.kaczmarzyk.moose.core.document;

public interface Dimension<T> {

	Coordinate<T> getDefaultCoordinate(Sheet doc);
	
	void extend();

	T get(int shift);
	
}
