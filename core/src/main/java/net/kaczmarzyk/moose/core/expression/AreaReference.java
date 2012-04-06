package net.kaczmarzyk.moose.core.expression;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Dimension;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.processor.DataProcessor;
import net.kaczmarzyk.moose.support.utils.ListUtil;

import com.google.common.base.Function;
import com.google.common.collect.Lists;


public class AreaReference implements Expression {// TODO must be improved to support multiple dimensions

	private ObjectAddress leftUpAddress;
	private ObjectAddress rightDownAddress;
	
	
	public AreaReference(ObjectAddress leftUpAddress, ObjectAddress rightDownAddress) {
		if (!leftUpAddress.getPath().equals(rightDownAddress.getPath())) {
			throw new IllegalArgumentException("paths should be the same for both ends of area reference!");
		}
		this.leftUpAddress = leftUpAddress;
		this.rightDownAddress = rightDownAddress;
	}


	@Override
	public DataObject evaluate(CellAddress address) {
		ObjectAddress leftUpAbs = leftUpAddress.absolute(address);
		ObjectAddress rightDownAbs = rightDownAddress.absolute(address);
		
		return new DataObjectList(calculateObjectList(leftUpAbs.getCellAddr(), rightDownAbs.getCellAddr(), leftUpAbs.getPath()));
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private List<DataObject> calculateObjectList(final CellAddress leftUp, CellAddress rightDown, final Path path) {
		List<List<Coordinate<?>>> coords = new ArrayList<>();
		for (Dimension dimension : leftUp.getDimensions()) {
			coords.add(leftUp.getCoordinate(dimension).upTo(rightDown.getCoordinate(dimension)));
		}
		return Lists.transform(ListUtil.cartesianProduct(coords), new Function<List<Coordinate<?>>, DataObject>() {
			@Override
			public DataObject apply(List<Coordinate<?>> coords) {
				return new ObjectAddress(new CellAddress(leftUp.getSheet(), coords), path).getObject();
			}
		});
	}

	private static class DataObjectList implements DataObject { // TODO is it clean ?

		private List<DataObject> objects = new LinkedList<>();
		
		
		public DataObjectList(List<DataObject> objects) {
			this.objects = objects;
		}

		@Override
		public <T> T accept(DataProcessor<T> visitor) {
			for (DataObject object : objects) {
				object.accept(visitor);
			}
			return null;
		}
		
		@Override
		public List<String> getPropertyNames() {
			throw new UnsupportedOperationException();
		}

		@Override
		public DataObject getProperty(String propName) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void refresh(CellAddress addr) {
			throw new UnsupportedOperationException();
		}

		@Override
		public DataObject getProperty(Path path) {
			throw new UnsupportedOperationException();
		}

		@Override
		public DataObject copy() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void setProperty(Path path, DataObject obj) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void placedInCell(CellAddress address) {
			throw new UnsupportedOperationException();
		}
		
	}
}
