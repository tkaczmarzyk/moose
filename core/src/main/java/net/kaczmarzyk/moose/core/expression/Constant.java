package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;


public class Constant implements Expression {

	private DataObject value;
	
	
	@Override
	public DataObject evaluate(CellAddress coords) {
		return value;
	}

}
