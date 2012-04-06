package net.kaczmarzyk.moose.core.document;

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
			return shift == ((Coordinate<?>)obj).shift;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return shift;
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
}
