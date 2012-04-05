package net.kaczmarzyk.moose.core.document;



public abstract class AbstractDataObject implements DataObject {

	protected Sheet sheet;
	
	
	public AbstractDataObject(Sheet sheet) {
		this.sheet = sheet;
	}
	
	@Override
	public void refresh(CellAddress addr) {
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
