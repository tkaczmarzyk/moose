package net.kaczmarzyk.moose.core.document;

import java.util.Collections;
import java.util.List;

import net.kaczmarzyk.moose.core.processor.DataProcessor;


public class NullObject implements DataObject {

	private ObjectAddress addr;
	
	
	public NullObject(ObjectAddress addr) {
		this.addr = addr;
	}
	
	@Override
	public List<String> getPropertyNames() {
		return Collections.emptyList();
	}

	@Override
	public DataObject getProperty(String propName) {
		return null;
	}

	@Override
	public <T> T accept(DataProcessor<T> visitor) {
		return visitor.process(this);
	}

	@Override
	public void refresh(CellAddress addr) {
	}

	@Override
	public DataObject getProperty(Path path) {
		return null;
	}

	@Override
	public DataObject copy() {
		return null;
	}

	@Override
	public void setProperty(Path path, DataObject obj) { // FIXME avoid ifs // FIXME generally it's wrong -- cell/object address confusin
		if (path.isInPlace()) {
			addr.getCellAddr().getCell().put(obj);
		}
		else if (path.isSingleProperty()) {
			MapData map = new MapData(addr.getCellAddr().getSheet());
			map.put(path.getPropertyChain().get(0), obj); // FIXME resolving prop name
			addr.getCellAddr().getCell().put(map, Path.IN_PLACE);
		}
		else {
			// TODO
			throw new UnsupportedOperationException("not yet implemented");
		}
	}

	
}
