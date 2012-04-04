package net.kaczmarzyk.moose.core.document;

import java.util.List;

import net.kaczmarzyk.moose.core.expression.Expression;
import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class Formula extends AbstractDataObject {

	private Expression expression;
	private DataObject cachedResult;
	
	private boolean visited; // TODO use it
	private boolean upToDate;// TODO and this as well
	
	
	public Formula(Document doc, Expression expression) {
		super(doc);
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
	public void refresh(Sheet sheet, Coordinates coords) {
		cachedResult = expression.evaluate(sheet, coords); // FIXME use flags
	}
}
