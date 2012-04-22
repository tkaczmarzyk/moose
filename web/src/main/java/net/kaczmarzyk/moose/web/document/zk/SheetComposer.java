package net.kaczmarzyk.moose.web.document.zk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.kaczmarzyk.moose.core.document.Cell;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.Dimension;
import net.kaczmarzyk.moose.core.document.Sheet;

import org.zkoss.web.util.resource.ServletRequestResolver;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.MouseEvent;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Button;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Div;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Messagebox.ClickEvent;
import org.zkoss.zul.Window;


@VariableResolver(ServletRequestResolver.class)
public class SheetComposer extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	@WireVariable Sheet sheet;
	
	@Wire Grid grid;
	@Wire Button btn;
	
	private CellRenderer cellRenderer = new CellRenderer();
	

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		new Column("").setParent(grid.getColumns());
		for (int i = 0; i < sheet.cols().getSize(); i++) {
			new Column(i + "").setParent(grid.getColumns());
		}
		
		grid.setRowRenderer(new RowRenderer<List<Cell>>() {
			@Override
			public void render(Row row, List<Cell> data, int index) throws Exception {
				new Label(index + "").setParent(row);
				for (Cell cell : data) {
					Component field;
					if (cell != null) {
						cellRenderer.render(cell).setParent(row);
					} else {
						field = new Div();
						field.setParent(row);
					}
				}
			}
		});
		
		grid.setModel(new ListModelList<List<Cell>>(getRows()));
	}
	
	private List<List<Cell>> getRows() {
		List<List<Cell>> result = new ArrayList<>();
		
		Dimension rows = sheet.rows();
		Dimension cols = sheet.cols();
		
		for (int r = 0; r < rows.getSize(); r++) {
			List<Cell> cells = new ArrayList<>();
			result.add(cells);
			for (int c = 0; c < cols.getSize(); c++) {
				cells.add(sheet.getCell(Coordinate.abs(cols, c), Coordinate.abs(rows, r)));
			}
		}
		
		return result;
	}

	@Listen("onClick=#btn")
	public void listen() {
		btn.setLabel("hej hej");
	}
	
	@Listen("onTestcik")
	public void listen2() {
		btn.setLabel("bubu");
	}
}
