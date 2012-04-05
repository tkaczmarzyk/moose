package net.kaczmarzyk.moose.core.function;

import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.expression.Expression;

import org.springframework.stereotype.Component;


@Component
public class Add implements Function { // TODO

	@Override
	public String getName() {
		return "+";
	}

	@SuppressWarnings("unchecked")
	@Override
	public DataObject call(CellAddress address, List<Expression> args) {
		Scalar<Double> arg1 = (Scalar<Double>) args.get(0).evaluate(address);
		Scalar<Double> arg2 = (Scalar<Double>) args.get(1).evaluate(address);
		return new Scalar<>(address.getSheet(), arg1.getValue() + arg2.getValue());
	}

}
