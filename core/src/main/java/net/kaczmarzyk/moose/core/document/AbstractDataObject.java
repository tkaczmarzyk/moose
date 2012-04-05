package net.kaczmarzyk.moose.core.document;




public abstract class AbstractDataObject implements DataObject {

	protected ObjectAddress addr;
	
	
	public AbstractDataObject(ObjectAddress addr) {
		this.addr = addr;
	}
	
	@Override
	public void refresh(CellAddress addr) {
	}
	
}
