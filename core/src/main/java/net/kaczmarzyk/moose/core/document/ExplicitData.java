package net.kaczmarzyk.moose.core.document;

public class ExplicitData<T> implements Data<T> {

	private T value;
	
	
	public ExplicitData(T value) {
		this.value = value;
	}
	
	@Override
	public T getExplicitValue(Document doc) {
		return value;
	}

}
