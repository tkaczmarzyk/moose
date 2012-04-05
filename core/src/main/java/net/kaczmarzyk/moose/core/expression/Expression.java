package net.kaczmarzyk.moose.core.expression;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;


public interface Expression {

	DataObject evaluate(CellAddress address); // cell address -- I assume that paths (so that object addresses) are always absolute
}
