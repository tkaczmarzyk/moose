package net.kaczmarzyk.moose.core.document;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;


public class CellAddress {

	private Sheet sheet;
	private List<Coordinate> coords;
	
	
	public CellAddress(Sheet sheet, List<Coordinate> coords) {
		this.coords = coords;
		this.sheet = sheet;
	}
	
	public CellAddress(Sheet sheet, Coordinate... coords) {
		this(sheet, Arrays.asList(coords));
	}

	public int size() {
		return coords.size();
	}
	
	public void add(Coordinate coord) {
		coords.add(coord);
	}
	
	public Sheet getSheet() {
		return sheet;
	}
	
	public Cell getCell() {
		return sheet.getCell(this);
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

	public CellAddress absolute(final CellAddress reference) { // TODO optimization ? (detecting absolute addresses)
		return new CellAddress(sheet, Lists.transform(coords, new Function<Coordinate, Coordinate>() {
			@Override
			public Coordinate apply(Coordinate originalCoord) {
				Coordinate referenceCoord = reference.getCoordinate(originalCoord.getDimension());
				return originalCoord.absolute(referenceCoord);
			}
		}));
	}
	
	public Coordinate getCoordinate(final Dimension<?> dimension) {
		Collection<Coordinate> coord = Collections2.filter(coords, new Predicate<Coordinate>() { // TODO introduce map dimension->coord
			@Override
			public boolean apply(Coordinate input) {
				return input.getDimension().equals(dimension);
			}
		});
		return coord.isEmpty() ? null : coord.iterator().next();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder(sheet.getName());
		result.append("(");
		result.append(Joiner.on("|").join(coords));
		result.append(")");
		return result.toString();
	}

	public DimensionsList getDimensions() { // TODO cache
		DimensionsList list = new DimensionsList();
		for (Coordinate coord : coords) {
			list.add(coord.getDimension());
		}
		return list;
	}

	public ObjectAddress objectAddress() {
		return new ObjectAddress(this, Path.IN_PLACE);
	}

	public ObjectAddress objectAddress(Path path) {
		return new ObjectAddress(this, path);
	}

	public Coordinate getCoordinate(int i) {
		return coords.get(i);
	}

	List<Coordinate> getCoordinates() {
		return coords;
	}

	private CellAddress copy() {
		return new CellAddress(sheet, coords);
	}
	
	public CellAddress withCoord(Coordinate coord) {
		CellAddress copy = copy();
		int index = copy.coords.indexOf(getCoordinate(coord.getDimension()));
		copy.coords.set(index, coord);
		return copy;
	}

}
