package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Objects;

public class Coordinate<T> {

	private Dimension<T> dimension;
	private boolean absolute;
	private int shift;
	
	
	public Coordinate(Dimension<T> dimension, int value) {
		this.shift = value;
		this.dimension = dimension;
	}
	
	public Coordinate(Dimension<T> dimension, int value, boolean absolute) {
		this(dimension, value);
		this.absolute = absolute;
	}
	
	public Coordinate<T> absolute(Coordinate<T> reference) {
		if (!reference.absolute) {
			throw new IllegalArgumentException();
		}
		return absolute ? this : abs(dimension, reference.shift + shift);
	}
	
	public T value() {
		if (!absolute) {
			throw new IllegalStateException();
		}
		return dimension.get(shift);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Coordinate) {
			Coordinate<?> that = (Coordinate<?>) obj;
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
	
	public static <K> Coordinate<K> rel(Dimension<K> dimension, int shift) {
		return new Coordinate<K>(dimension, shift);
	}
	
	public static <K> Coordinate<K> abs(Dimension<K> dimension, int shift) {
		return new Coordinate<K>(dimension, shift, true);
	}

	public Dimension<T> getDimension() {
		return dimension;
	}
	
	@Override
	public String toString() {
		return String.format("Coord[%s]%s", dimension, absolute ? "[" + shift +"]" : shift);
	}

	public List<Coordinate<T>> upTo(Coordinate<T> endInclusive) {
		List<Coordinate<T>> result = new ArrayList<>();
		for (int i = shift; i <= endInclusive.shift; i++) {
			result.add(new Coordinate<>(dimension, i, true)); // TODO verify it's ok to always return absolute here
		}
		return result;
	}
}
