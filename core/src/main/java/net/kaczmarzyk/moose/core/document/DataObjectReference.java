package net.kaczmarzyk.moose.core.document;

import java.util.List;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


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
	public DataObject getProperty(String propName) {
		return doc.getCell(refCoords).getValue().getProperty(propName); // TODO property name as part of coordinate?
	}

	@Override
	public <T> T accept(DataProcessor<T> visitor) {
		return visitor.process(this);
	}

	public DataObject getReferencedObject()	{
		return doc.getCell(refCoords).getValue();
	}
}
