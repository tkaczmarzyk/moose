package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;


public class Constant implements Expression {

	private DataObject value;
	
	
	public Constant(DataObject value) {
		this.value = value;
	}
	
	@Override
	public DataObject evaluate(CellAddress coords) {
		return value;
	}

	@Override
	public int hashCode() {
		return value.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Constant) {
			Constant that = (Constant) obj;
			return this.value.equals(that.value);
		} else {
			return false;
		}
	}
}
