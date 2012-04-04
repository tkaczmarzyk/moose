package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.List;


public class Document {

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

	public Cell getCell(CellAddress addr) {
		return sheets.get(0).getCell(addr); //FIXME handle multiple sheets
	}
}
