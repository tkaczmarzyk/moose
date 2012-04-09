package net.kaczmarzyk.moose.core.document;


public abstract class AbstractDataObject implements DataObject { // TODO remove this class if not required

	protected ObjectAddress addr;
	
	
	public AbstractDataObject(ObjectAddress addr) { // FIXME should not be required in constructor
		this.addr = addr;
	}
	
	@Override
	public void refresh(CellAddress addr) {
	}
	
}
