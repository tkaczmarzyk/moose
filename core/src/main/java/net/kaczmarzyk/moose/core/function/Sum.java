package net.kaczmarzyk.moose.core.function;

import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.expression.Expression;
import net.kaczmarzyk.moose.core.processor.DataProcessorWithoutResult;

import org.springframework.stereotype.Component;


@Component
public class Sum implements Function {

	@Override
	public String getName() {
		return "sum";
	}

	@Override
	public DataObject call(CellAddress address, List<Expression> args) {
		SumCalculator sumCalc = new SumCalculator();
		for (Expression arg : args) {
			arg.evaluate(address).accept(sumCalc);
		}
		return new Scalar<Double>(sumCalc.sum);
	}

	private static class SumCalculator extends DataProcessorWithoutResult {
		
		double sum = 0.0;

		@Override
		protected void doProcess(DataObject data) {
			throw new UnsupportedOperationException();
		}

		@Override
		protected void doProcess(Scalar<?> scalarData) {
			sum += (Double) scalarData.getValue();
		}
		
	}
}
