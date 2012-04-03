package net.kaczmarzyk.moose.core.document;

public class RowDimension implements Dimension<Integer> {

	private int numRows = 10;
	
	@Override
	public Coordinate<Integer> getDefaultCoordinate(Sheet doc) {
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
				return current <= numRows;
			}
		};
	}

	@Override
	public void extend() {
		numRows++;
	}

}
