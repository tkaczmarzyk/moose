package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class Coordinate {

	private Dimension<?> dimension;
	private boolean absolute;
	private int shift;
	
	
	public Coordinate(Dimension<?> dimension, int value) {
		this.shift = value;
		this.dimension = dimension;
	}
	
	public Coordinate(Dimension<?> dimension, int value, boolean absolute) {
		this(dimension, value);
		this.absolute = absolute;
	}
	
	public Coordinate absolute(Coordinate reference) {
		if (!reference.absolute) {
			throw new IllegalArgumentException();
		}
		return absolute ? this : abs(dimension, reference.shift + shift);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coordinate) {
			Coordinate that = (Coordinate) obj;
			return this.shift == that.shift
					&& this.absolute == that.absolute
					&& Objects.equal(this.dimension, that.dimension);
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(shift, dimension, absolute);
	}
	
	public static Coordinate rel(Dimension<?> dimension, int shift) {
		return new Coordinate(dimension, shift);
	}
	
	public static Coordinate abs(Dimension<?> dimension, int shift) {
		return new Coordinate(dimension, shift, true);
	}

	public Dimension<?> getDimension() {
		return dimension;
	}
	
	@Override
	public String toString() {
		return String.format("Coord[%s]%s", dimension, absolute ? "[" + shift +"]" : shift);
	}

	public List<Coordinate> upTo(Coordinate endInclusive) {
		List<Coordinate> result = new ArrayList<>();
		for (int i = shift; i <= endInclusive.shift; i++) {
			result.add(new Coordinate(dimension, i, true)); // TODO verify it's ok to always return absolute here
		}
		return result;
	}

	public Object getValue() {
		return dimension.get(shift);
	}

	public Coordinate lower() {
		if (absolute) {
			if (shift == 0) {
				return null;
			}
			return new Coordinate(dimension, shift -1, true);
		} else {
			throw new UnsupportedOperationException("not implemented yet"); // FIXME
		}
	}
}
