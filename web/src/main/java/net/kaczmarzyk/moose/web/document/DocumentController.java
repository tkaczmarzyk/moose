package net.kaczmarzyk.moose.web.document;

import java.util.Arrays;

import net.kaczmarzyk.moose.core.document.Document;
import net.kaczmarzyk.moose.web.document.zk.Element;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DocumentController {

	@RequestMapping("/doc")
	public String doc(Model model) {
		model.addAttribute("doc", new Document("New_document"));
		model.addAttribute("model", Arrays.asList(new Element("s2"), new Element("s3")));
		return "doc/sheet";
	}
}
