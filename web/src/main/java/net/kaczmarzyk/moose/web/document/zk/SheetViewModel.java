package net.kaczmarzyk.moose.web.document.zk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.service.DocumentService;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;


@VariableResolver(DelegatingVariableResolver.class)
public class SheetViewModel extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@Wire
	Button btn;
	
	@WireVariable
	DocumentService documentService;
	
	private Document doc;
	
	
	@Init
	public void setup() {
		this.doc = documentService.newDocument();
	}
	
	public List<Element> getElements() {
		return new ArrayList<>(Arrays.asList(new Element("e")));
	}
	
	public Document getDoc() {
		return doc;
	}
	
	@Listen("onClick=#btn")
	public void listen() {
		btn.setLabel("hej hej");
	}
}
