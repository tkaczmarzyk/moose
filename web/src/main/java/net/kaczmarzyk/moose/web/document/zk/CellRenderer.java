package net.kaczmarzyk.moose.web.document.zk;

import java.util.HashMap;
import java.util.Map;

import net.kaczmarzyk.moose.core.document.Cell;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;


public class CellRenderer {

	private DataObjectRenderer dataRenderer;
	
	public CellRenderer() {
		this.dataRenderer = new DataObjectRenderer(); // TODO
	}

	public Component render(Cell cell) {
		Map<String, Object> map = new HashMap<>();
		map.put("valueString", cell.getValue().toString());
		Component component = cell.getValue().accept(dataRenderer);
		component.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<MouseEvent>() {
			@Override
			public void onEvent(MouseEvent event) throws Exception {
				event.getTarget().detach();
			}
		});
		return component;
	}
}
