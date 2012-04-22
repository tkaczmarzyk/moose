package net.kaczmarzyk.moose.web.document.zk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.document.Document;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;


public class SheetViewModel {

	private Document doc;
	
	
	@Init
	public void setup() {
		this.doc = (Document) Executions.getCurrent().getAttribute("document");
	}
	
	public List<Element> getElements() {
		return new ArrayList<>(Arrays.asList(new Element(doc.toString()), new Element("s3")));
	}
	
	public Document getDoc() {
		return doc;
	}
	
}
