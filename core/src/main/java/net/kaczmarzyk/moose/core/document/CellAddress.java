package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Objects;


public class CellAddress { // FIXME handle relative coords

	private Sheet sheet;
	private List<Coordinate<?>> coords;
	
	
	public CellAddress(Sheet sheet, List<Coordinate<?>> coords) {
		this.coords = coords;
		this.sheet = sheet;
	}
	
	public CellAddress(Sheet sheet, Coordinate<String> columnCoord, Coordinate<Integer> rowCoord) {
		this(sheet, new ArrayList<Coordinate<?>>(Arrays.asList(columnCoord, rowCoord)));
	}

	public int size() {
		return coords.size();
	}
	
	public void add(Coordinate<?> coord) {
		coords.add(coord);
	}
	
	public Sheet getSheet() {
		return sheet;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(sheet, coords);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CellAddress) {
			CellAddress that = (CellAddress) obj;
			return Objects.equal(this.sheet, that.sheet) &&
					Objects.equal(this.coords, that.coords);
		} else {
			return false;
		}
	}
}
