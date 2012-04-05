package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ColumnDimension implements Dimension<String> {

	private List<String> columnNames = new ArrayList<>(Arrays.asList(new String[] {"A", "B", "C"}));
	

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

}
