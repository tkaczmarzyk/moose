package net.kaczmarzyk.moose.core.document;

public class ImplicitData<T> implements Data<T> {

	private T value;
	
	
	public ImplicitData(T value) {
		this.value = value;
	}
	
	@Override
	public T getImplicitValue() {
		return value;
	}

}
