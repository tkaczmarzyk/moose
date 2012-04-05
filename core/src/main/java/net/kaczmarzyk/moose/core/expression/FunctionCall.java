package net.kaczmarzyk.moose.core.expression;

import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.function.Function;


public class FunctionCall implements Expression {

	private Function function;
	private List<Expression> args;
	
	
	public FunctionCall(Function function, Expression... args) {
		this.function = function;
		this.args = Arrays.asList(args);
	}
	
	@Override
	public DataObject evaluate(CellAddress addr) {
		return function.call(addr, args);
	}

}
