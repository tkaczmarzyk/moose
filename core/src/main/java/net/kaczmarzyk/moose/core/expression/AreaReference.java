package net.kaczmarzyk.moose.core.expression;

import java.util.Arrays;
import java.util.List;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Path;
import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class AreaReference implements Expression {

	private ObjectAddress leftUpAddress; // TODO probably must be improved to support multiple dimensions
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
		
		return new DataObjectList(Arrays.asList(leftUpAbs.getObject(), rightDownAbs.getObject()));// FIXME temp
	}

	private static class DataObjectList implements DataObject { // FIXME is it clean ?

		private List<DataObject> objects;
		
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
