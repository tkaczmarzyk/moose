package net.kaczmarzyk.moose.core.document;



public abstract class AbstractDataObject implements DataObject {

	protected Document doc;
	
	
	public AbstractDataObject(Document doc) {
		this.doc = doc;
	}
	
}
