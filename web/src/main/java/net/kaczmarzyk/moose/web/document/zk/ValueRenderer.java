package net.kaczmarzyk.moose.web.document.zk;

import net.kaczmarzyk.moose.core.document.Scalar;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Label;


public class ValueRenderer implements PropertyRenderer {

	@Override
	public Component render(Scalar<?> value) {
		return new Label(value.getValue().toString());
	}

}
