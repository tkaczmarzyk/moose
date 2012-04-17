package net.kaczmarzyk.moose.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ReferenceController {

	@RequestMapping("test")
	public String helloWorld2() {
		return "test2";
	}
}
