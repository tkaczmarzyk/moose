package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.Coordinates;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Sheet;


public class ObjectReferenceExpression implements Expression {

	private Coordinates refCoords;
	
	
	public ObjectReferenceExpression(Coordinates refCoords) {
		this.refCoords = refCoords;
	}
	
	@Override
	public DataObject evaluate(Sheet sheet, Coordinates coords) {
		return sheet.getCell(refCoords).getValue(); // FIXME take sheet from coords //FIXME handle relative coords
	}

}
