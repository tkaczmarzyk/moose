package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Sheet;


public interface Expression {

	DataObject evaluate(Sheet sheet, CellAddress coords); // FIXME Cell or obj address?
}
