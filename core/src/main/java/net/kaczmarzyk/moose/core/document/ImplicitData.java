package net.kaczmarzyk.moose.core.document;


public class ImplicitData<T> implements Data<T> {

	private Data<T> explicitData;
	
	
	public ImplicitData(Data<T> explicitData) {
		this.explicitData = explicitData;
	}
	
	@Override
	public T getExplicitValue(Document doc) {
		return explicitData.getExplicitValue(doc);
	}

}
