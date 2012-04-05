package net.kaczmarzyk.moose.core.document;

import java.util.List;

import net.kaczmarzyk.moose.core.expression.Expression;
import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class Formula extends AbstractDataObject {

	private Expression expression;
	private DataObject cachedResult;
	
	private boolean visited; // TODO use it
	private boolean upToDate;// TODO and this as well
	
	
	public Formula(Sheet sheet, Expression expression) {
		super(sheet);
		this.expression = expression;
	}

	@Override
	public List<String> getPropertyNames() {
		return cachedResult.getPropertyNames();
	}

	@Override
	public DataObject getProperty(String propName) {
		return cachedResult.getProperty(propName);
	}

	@Override
	public <T> T accept(DataProcessor<T> visitor) {
		return cachedResult.accept(visitor);
	}

	@Override
	public void refresh(CellAddress addr) {
		cachedResult = expression.evaluate(addr); // FIXME use flags
	}

	@Override
	public DataObject copy() {
		return new Formula(sheet, expression);
	}

	@Override
	public void setProperty(Path path, DataObject obj) {
		throw new UnsupportedOperationException("this is calculated object, cannot set property"); // TODO nicer way...
	}

}
