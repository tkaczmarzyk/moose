package net.kaczmarzyk.moose.web.document.zk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.document.Document;

import org.zkoss.bind.annotation.Init;
import org.zkoss.web.util.resource.ServletRequestResolver;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;


@VariableResolver(ServletRequestResolver.class)
public class SheetViewModel {

	@WireVariable("document")
	private Document doc;
	
	
	@Init
	public void setup() {
	}
	
	public List<Element> getElements() {
		return new ArrayList<>(Arrays.asList(new Element(doc.toString()), new Element("s3")));
	}
	
	public Document getDoc() {
		return doc;
	}
	
}
