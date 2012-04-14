package net.kaczmarzyk.moose.core.document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.kaczmarzyk.moose.support.utils.ListUtil;

import org.springframework.util.CollectionUtils;


public class DimensionsList implements Iterable<Dimension<?>> {

	private List<Dimension<?>> dimensions = new ArrayList<>();

	
	public DimensionsList() {
		
	}
	
	public Dimension<?> get(int index) {
		return dimensions.get(index);
	}
	
	public int size() {
		return dimensions.size();
	}
	
	public void add(Dimension<?> dimension) {
		dimensions.add(dimension);
	}

	public CellAddress inferFullAddress(CellAddress partialAddress, Sheet sheet) {
		if (hasAllDimensions(partialAddress)) {
			return partialAddress;
		}
		List<Coordinate> coords = new ArrayList<Coordinate>();
		for (int i = 0; i < dimensions.size(); i++) { // FIXME use dimensions instead of index
			coords.add(i < partialAddress.getCoordinates().size() ? partialAddress.getCoordinates().get(i) : dimensions.get(i).extend());
		}
		return new CellAddress(sheet, coords); // TODO use sheet from partialAddress?
	}

	private boolean hasAllDimensions(CellAddress partialAddress) {
		for (Dimension<?> dimension : dimensions) {
			if (partialAddress.getCoordinate(dimension) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Iterator<Dimension<?>> iterator() {
		return dimensions.iterator();
	}

	public CellAddress inferVisibleAddress(CellAddress address, Sheet sheet) {
		CellAddress fullAddress = inferFullAddress(address, sheet);
		if (sheet.isEmpty(fullAddress)) {
			for (Dimension<?> dimension : ListUtil.reversedOrder(dimensions)) {
				if (dimension.isTransparent()) {
					CellAddress lowerAddr = findLowerNotEmpty(fullAddress, sheet, dimension);
					if (lowerAddr != null) {
						return lowerAddr;
					}
				}
			}
		}
		return fullAddress;
	}

	private CellAddress findLowerNotEmpty(CellAddress address, Sheet sheet, Dimension<?> dimension) {
		Coordinate coord = address.getCoordinate(dimension);
		while ((coord = coord.lower()) != null) {
			CellAddress lowerAddr = address.withCoord(coord);
			if (!sheet.isEmpty(lowerAddr)) {
				return lowerAddr;
			}
		}
		return null;
	}

}
