package net.kaczmarzyk.moose.core.document;

public class ColumnDimension implements Dimension<String> {

	@Override
	public CoordinateGenerator<String> coordinateGenerator() {
		return new CoordinateGenerator<String>() {
			
			final String[] values = {"A", "B", "C"};
			int i = 0;
			
			@Override
			public Coordinate<String> next() {
				return new Coordinate<String>(values[i++]);
			}
			
			@Override
			public boolean hasNext() {
				return i < values.length;
			}
		};
	}

	@Override
	public Coordinate<String> getDefaultCoordinate(Document doc) {
		return Coordinate.of("A");
	}

}
