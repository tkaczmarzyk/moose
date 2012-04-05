package net.kaczmarzyk.moose.core.function;

import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.expression.Expression;

import org.springframework.stereotype.Component;


@Component
public class Abs implements Function {

	@Override
	public String getName() {
		return "abs";
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataObject call(CellAddress address, List<Expression> args) {
		DataObject result = args.get(0).evaluate(address);
		return new Scalar<Double>(Math.abs(((Scalar<Double>) result).getValue()));
	}

}
