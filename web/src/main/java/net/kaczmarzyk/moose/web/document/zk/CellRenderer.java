package net.kaczmarzyk.moose.web.document.zk;

import java.util.HashMap;
import java.util.Map;

import net.kaczmarzyk.moose.core.document.Cell;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zul.Textbox;


public class CellRenderer {

	private DataObjectRenderer valueRenderer;
	
	
	public CellRenderer() {
		this.valueRenderer = new DataObjectRenderer(new ValueRenderer()); // TODO
	}

	public Component renderSource(Cell cell) {
		Textbox box = new Textbox(cell.getSource());
		return box;
	}
	
	public Component renderValue(Cell cell) {
		return render(cell, valueRenderer);
	}

	private Component render(Cell cell, DataObjectRenderer dataRenderer) {
		Map<String, Object> map = new HashMap<>();
		map.put("valueString", cell.getValue().toString());
		Component component = cell.getValue().accept(dataRenderer);
		component.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<MouseEvent>() {
			@Override
			public void onEvent(MouseEvent event) throws Exception {
				Component cellContainer = event.getTarget().getParent();
				cellContainer.getChildren().clear();
				cellContainer.appendChild(renderSource((Cell) cellContainer.getAttribute("cell")));
			}
		});
		return component;
	}
}
