package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.ObjectAddress;


public class ObjectReference implements Expression {

	private ObjectAddress refAddress;
	
	
	public ObjectReference(ObjectAddress refCoords) {
		this.refAddress = refCoords;
	}
	
	@Override
	public DataObject evaluate(CellAddress addr) {
		DataObject referencedObj = refAddress.getCellAddr().absolute(addr).getCell().getValue();
		return referencedObj.getProperty(refAddress.getPath());
	}

}
