package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;


public class CellAddress {

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

	public CellAddress absolute(final CellAddress reference) {
		return new CellAddress(sheet, Lists.transform(coords, new Function<Coordinate<?>, Coordinate<?>>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			@Override
			public Coordinate<?> apply(Coordinate originalCoord) {
				Coordinate<?> referenceCoord = reference.getCoordinate(originalCoord.getDimension());
				return originalCoord.absolute(referenceCoord);
			}
		}));
	}
	
	protected Coordinate<?> getCoordinate(final Dimension<?> dimension) {
		return Collections2.filter(coords, new Predicate<Coordinate<?>>() { // TODO introduce map dimension->coord
			@Override
			public boolean apply(Coordinate<?> input) {
				return input.getDimension().equals(dimension);
			}
		}).iterator().next();
	}

	public Cell getCell() { // TODO cache
		return sheet.getCell(this); // FIXME arg?
	}
}
