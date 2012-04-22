package net.kaczmarzyk.moose.web.document;

import net.kaczmarzyk.moose.core.document.Document;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DocumentController {

	@RequestMapping("/doc")
	public String doc(Model model) {
		model.addAttribute("document", new Document("New_document"));
		return "doc/sheet";
	}
}
