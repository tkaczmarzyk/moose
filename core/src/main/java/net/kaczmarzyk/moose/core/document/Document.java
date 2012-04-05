package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;


public class Document { // TODO add currentSheet

	private String name;
	
	private List<Sheet> sheets;
	
	
	public Document(String name) {
		this.setName(name);
		this.sheets = new ArrayList<>();
		for (int i=0; i<2; i++) {
			this.sheets.add(new Sheet(this));
		}
	}

	public List<Sheet> getSheets() {
		return sheets;
	}

	public void addSheet() {
		sheets.add(new Sheet(this));
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sheet getSheet(final String sheetName) {
		return Collections2.filter(sheets, new Predicate<Sheet>() {
			@Override
			public boolean apply(Sheet input) {
				return input.getName().equals(sheetName);
			}
		}).iterator().next();
	}

}
