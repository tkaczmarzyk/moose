package net.kaczmarzyk.moose.core.document;

public interface Dimension<T> {

	CoordinateGenerator<T> coordinateGenerator();
	
	Coordinate<T> getDefaultCoordinate(Document doc);
}
