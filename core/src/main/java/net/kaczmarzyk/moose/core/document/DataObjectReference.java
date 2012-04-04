package net.kaczmarzyk.moose.core.document;

import java.util.List;


public class DataObjectReference extends AbstractDataObject {

	private Coordinates refCoords;
	
	
	public DataObjectReference(Document doc, Coordinates coords) {
		super(doc);
		this.refCoords = coords;
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
