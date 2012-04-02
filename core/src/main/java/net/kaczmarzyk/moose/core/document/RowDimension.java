package net.kaczmarzyk.moose.core.document;

public class RowDimension implements Dimension<Integer> {

	@Override
	public Coordinate<Integer> getDefaultCoordinate(Document doc) {
		return Coordinate.of(1);
	}
	
	@Override
	public CoordinateGenerator<Integer> coordinateGenerator() {
		return new CoordinateGenerator<Integer>() {
			int current = 1;
			@Override
			public Coordinate<Integer> next() {
				return new Coordinate<Integer>(current++);
			}
			
			@Override
			public boolean hasNext() {
				return current < Integer.MAX_VALUE;
			}
		};
	}

}
