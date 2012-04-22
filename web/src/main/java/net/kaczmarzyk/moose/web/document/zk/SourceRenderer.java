package net.kaczmarzyk.moose.web.document.zk;

import net.kaczmarzyk.moose.core.document.Scalar;

import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Textbox;


public class SourceRenderer implements PropertyRenderer {

	@Override
	public Component render(Scalar<?> value) {
		return new Textbox("todo");
	}

}
