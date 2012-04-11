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
		return new NullObject(addr.withExtendedPath(propName));
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
	public void setProperty(Path path, DataObject obj) { // TODO avoid ifs
		if (path.isInPlace()) {
			if (addr.getPath().isInPlace()) {
				addr.getCellAddr().getCell().put(obj);
			}
			else {
				addr.put(obj);
			}
		}
		else {
			MapData map = new MapData();
			addr.put(map);
			map.setProperty(path, obj);
		}
	}

	@Override
	public void placedAtAddress(ObjectAddress objAddr) {
		this.addr = objAddr;
	}

	
}
