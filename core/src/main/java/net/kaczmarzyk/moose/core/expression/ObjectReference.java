package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Sheet;


public class ObjectReference implements Expression {

	private ObjectAddress refAddress;
	
	
	public ObjectReference(ObjectAddress refCoords) {
		this.refAddress = refCoords;
	}
	
	@Override
	public DataObject evaluate(Sheet sheet, CellAddress coords) {
		DataObject referencedObj = sheet.getCell(refAddress.getCellAddr()).getValue(); // FIXME take sheet from coords //FIXME handle relative coords
		return referencedObj.getProperty(refAddress.getPath());
	}

}
