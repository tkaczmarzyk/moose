package net.kaczmarzyk.moose.web.document.zk;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.processor.DataProcessor;
import net.kaczmarzyk.moose.core.processor.MapDataProcessor;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

public class DataObjectRenderer implements DataProcessor<Component> {

	private DataProcessor<?> dataProcessor = new MapDataProcessor(); // TODO
	
	@Override
	public Label process(Scalar<?> scalarData) {
		return new Label(dataProcessor.process(scalarData).toString());
	}

	@Override
	public Component process(DataObject data) {
		Div container = new Div();
		for (String propName : data.getPropertyNames()) {
			Div propContainer = new Div();
			propContainer.appendChild(new Label(propName + ": "));
			propContainer.appendChild(data.getProperty(propName).accept(this));
			propContainer.setParent(container);
		}
		return container;
	}

}
