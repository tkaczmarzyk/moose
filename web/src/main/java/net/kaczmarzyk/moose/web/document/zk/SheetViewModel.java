package net.kaczmarzyk.moose.web.document.zk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.service.DocumentService;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.WireVariable;


public class SheetViewModel {

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
	
}
