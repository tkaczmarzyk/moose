package net.kaczmarzyk.moose.core.document;

import java.util.List;


public class DataObjectReference implements DataObject {

	private Coordinates refCoords;
	private Document doc;	
	
	
	public DataObjectReference(Coordinates coords, Document doc) {
		this.refCoords = coords;
		this.doc = doc;
	}
	
	@Override
	public List<String> getPropertyNames() {
		return doc.getCell(refCoords).getValue().getPropertyNames();
	}

	@Override
	public Data<?> getProperty(String propName) {
		return new ImplicitData<>(doc.getCell(refCoords).getValue().getProperty(propName)); // TODO really need to wrap?
	}

}
