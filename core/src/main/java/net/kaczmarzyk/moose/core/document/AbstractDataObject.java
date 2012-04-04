package net.kaczmarzyk.moose.core.document;



public abstract class AbstractDataObject implements DataObject {

	protected Document doc;
	
	
	public AbstractDataObject(Document doc) { // FIXME change to sheet reference to handle implicit sheet coordinate!
		this.doc = doc;
	}
	
	@Override
	public void refresh(Sheet sheet, CellAddress addr) {
	}
	
	@Override
	public DataObject getProperty(Path path) {
		DataObject result = this;
		for (String property : path.getPropertyChain()) {
			result = result.getProperty(property);
		}
		return result;
	}
}
