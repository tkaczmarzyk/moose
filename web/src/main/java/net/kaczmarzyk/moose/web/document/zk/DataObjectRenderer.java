package net.kaczmarzyk.moose.web.document.zk;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.processor.DataProcessor;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;

public class DataObjectRenderer implements DataProcessor<Component> {

	private PropertyRenderer propRenderer;
	

	public DataObjectRenderer(PropertyRenderer propRenderer) {
		this.propRenderer = propRenderer;
	}
	
	@Override
	public Component process(Scalar<?> scalarData) {
		Component scalarComp = propRenderer.render(scalarData);
		scalarComp.setAttribute("dataObject", scalarData);
		return scalarComp;
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
		container.setAttribute("dataObject", data);
		return container;
	}

}
