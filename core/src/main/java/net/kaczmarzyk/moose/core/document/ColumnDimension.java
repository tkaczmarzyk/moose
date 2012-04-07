package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ColumnDimension implements Dimension<String> { // TODO proper impl

	private List<String> columnNames = new ArrayList<>(Arrays.asList(new String[] {"A", "B", "C", "D", "E"}));
	

	@Override
	public Coordinate<String> getDefaultCoordinate(Sheet doc) {
		return Coordinate.abs(this, 0);
	}

	@Override
	public void extend() {
		int lastColumnIdx = columnNames.size() - 1;
		String nextName = nextColumnName(columnNames.get(lastColumnIdx));
		if (nextName != null) {
			columnNames.add(nextName);
		}
	}

	private String nextColumnName(String column) {
		if (column.length() == 1) {
			char ch = column.charAt(0);
			if (ch < 'Z') {
				return new String(new char[] {ch++});
			}
		}
		return null;
	}

	@Override
	public String get(int shift) {
		return columnNames.get(shift);
	}

	@Override
	public String toString() {
		return "Col";
	}

	@Override
	public int hashCode() {
		return ColumnDimension.class.hashCode();
	};
	
	@Override
	public boolean equals(Object obj) {
		return obj.getClass() == ColumnDimension.class; // FIXME decide if such equality is ok, refactor dimensions access TODO ? relative dimensions (dimension index) while referncing?
	}
}