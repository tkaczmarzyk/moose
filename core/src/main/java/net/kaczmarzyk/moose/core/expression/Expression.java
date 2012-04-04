package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.Coordinates;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Sheet;


public interface Expression {

	DataObject evaluate(Sheet sheet, Coordinates coords);
}
