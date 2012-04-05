package net.kaczmarzyk.moose.core.function;

import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.expression.Expression;


public interface Function {

	String getName();

	DataObject call(CellAddress address, List<Expression> args);

}
