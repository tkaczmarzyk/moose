package net.kaczmarzyk.moose.core.document;

import java.util.List;


public class SpatialDimension implements Dimension<Integer> {

	private static enum Type {
		ROW,
		COLUMN
	}
	
	private int numValues = 20;
	private Type type;
	
	
	private SpatialDimension(Type type) {
		this.type = type;
	}
	
	@Override
	public Coordinate extend() {
		numValues++;
		return new Coordinate(this, numValues - 1);
	}

	@Override
	public void extend(List<Integer> values) {
		throw new UnsupportedOperationException(); //FIXME
	}

	@Override
	public Integer get(int shift) {
		return shift;
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) { //TODO is it ok to have such  equals?
		if (obj instanceof SpatialDimension) {
			return ((SpatialDimension) obj).type == this.type;
		} else {
			return false;
		}
	}
	
	public static SpatialDimension rows() {
		return new SpatialDimension(Type.ROW);
	}
	
	public static SpatialDimension cols() {
		return new SpatialDimension(Type.COLUMN);
	}
}
