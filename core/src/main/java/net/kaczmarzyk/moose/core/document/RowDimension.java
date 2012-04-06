package net.kaczmarzyk.moose.core.document;


public class RowDimension implements Dimension<Integer> {

	private int numRows = 10;
	
	
	@Override
	public Coordinate<Integer> getDefaultCoordinate(Sheet doc) {
		return Coordinate.abs(this, 0);
	}

	@Override
	public void extend() {
		numRows++;
	}


	@Override
	public Integer get(int shift) {
		return shift;
	}

	@Override
	public String toString() {
		return "Row";
	}
	
}
