package net.kaczmarzyk.moose.web.document.zk;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;


@VariableResolver(DelegatingVariableResolver.class)
public class SheetComposer extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Wire
	Button btn;
	
	@Listen("onClick=#btn")
	public void listen() {
		btn.setLabel("hej hej");
	}
}
