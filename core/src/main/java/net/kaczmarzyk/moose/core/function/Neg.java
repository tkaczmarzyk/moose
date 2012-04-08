package net.kaczmarzyk.moose.core.function;

import java.util.List;

import org.springframework.stereotype.Component;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.expression.Expression;


@Component
public class Neg implements Function {

	@Override
	public String getName() {
		return "neg";
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataObject call(CellAddress address, List<Expression> args) {
		Scalar<Double> arg = (Scalar<Double>) args.get(0).evaluate(address);
		return new Scalar<>(-arg.getValue());
	}

}
