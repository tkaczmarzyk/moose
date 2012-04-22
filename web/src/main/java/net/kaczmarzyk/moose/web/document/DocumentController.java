package net.kaczmarzyk.moose.web.document;

import net.kaczmarzyk.moose.core.document.CellAddress;
import net.kaczmarzyk.moose.core.document.Coordinate;
import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.core.document.MapData;
import net.kaczmarzyk.moose.core.document.Scalar;
import net.kaczmarzyk.moose.core.document.Sheet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DocumentController {

	@RequestMapping("/doc")
	public String doc(Model model) {
		Document doc = new Document("New_document");
		Sheet sheet = doc.getSheets().get(0);
		new CellAddress(sheet, Coordinate.abs(sheet.getDimensions().get(0), 0),
				Coordinate.abs(sheet.getDimensions().get(1), 0)).objectAddress().put(new Scalar<>(7.0));
		
		MapData mapData = new MapData();
		mapData.put("title", new Scalar<>("rendering cell value and source"));
		mapData.put("estimate", new Scalar<>(7.0));
		
		new CellAddress(sheet, Coordinate.abs(sheet.getDimensions().get(0), 10),
				Coordinate.abs(sheet.getDimensions().get(1), 1)).objectAddress().put(mapData);
		
		new CellAddress(sheet, Coordinate.abs(sheet.getDimensions().get(0), 11),
				Coordinate.abs(sheet.getDimensions().get(1), 1)).objectAddress().put(mapData.copy());
		
		model.addAttribute("sheet", sheet);
		return "doc/sheet";
	}
}
