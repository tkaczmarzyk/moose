package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.ObjectAddress;


public class ObjectReference implements Expression {

	private ObjectAddress refferedAddress;
	
	
	public ObjectReference(ObjectAddress refferedAddress) {
		this.refferedAddress = refferedAddress;
	}
	
	@Override
	public DataObject evaluate(CellAddress addr) {
		return refferedAddress.absolute(addr).getObject();
	}

}
