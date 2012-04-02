package net.kaczmarzyk.moose.core.document;

public class Coordinate<T> {

	private T value;
	
	
	public Coordinate(T value) {
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}

	public static <K> Coordinate<K> of(K value) {
		return new Coordinate<K>(value);
	}
	
}
