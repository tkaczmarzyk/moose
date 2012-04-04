package net.kaczmarzyk.moose.core.document;



public abstract class AbstractDataObject implements DataObject {

	protected Document doc;
	
	
	public AbstractDataObject(Document doc) { // FIXME change to sheet reference to handle implicit sheet coordinate!
		this.doc = doc;
	}
	
	public void refresh(Sheet sheet, Coordinates coords) {
	}
}
