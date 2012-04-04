package net.kaczmarzyk.moose.core.document;



public abstract class AbstractDataObject implements DataObject {

	protected Sheet sheet;
	
	
	public AbstractDataObject(Sheet sheet) { // FIXME change to sheet reference to handle implicit sheet coordinate!
		this.sheet = sheet;
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
