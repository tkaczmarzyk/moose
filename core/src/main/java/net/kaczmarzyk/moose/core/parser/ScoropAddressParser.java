package net.kaczmarzyk.moose.core.parser;

import net.kaczmarzyk.moose.core.document.ObjectAddress;
import net.kaczmarzyk.moose.core.document.Sheet;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.springframework.stereotype.Component;


@Component
public class ScoropAddressParser implements AddressParser {

	@Override
	public ObjectAddress parse(Sheet sheet, String input) { // TODO inject dataobject parser and delegate OR extract code below into util
		ScoropGrammarLexer lexer = new ScoropGrammarLexer(new ANTLRStringStream(input));
		ScoropGrammarParser parser = new ScoropGrammarParser(new CommonTokenStream(lexer));
		parser.sheet_ = sheet;
		parser.doc_ = sheet.getDoc();
		try {
			return parser.objAddr();
		} catch (RecognitionException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
