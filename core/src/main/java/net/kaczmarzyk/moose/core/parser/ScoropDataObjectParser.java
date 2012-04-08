package net.kaczmarzyk.moose.core.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import net.kaczmarzyk.moose.core.document.DataObject;
import net.kaczmarzyk.moose.core.document.Sheet;
import net.kaczmarzyk.moose.core.function.FunctionRegistry;


@Component
public class ScoropDataObjectParser implements DataObjectParser {

	@Autowired
	FunctionRegistry funRegistry;
	
	
	@Override
	public DataObject parse(Sheet sheet, String input) {
		ScoropGrammarLexer lexer = new ScoropGrammarLexer(new ANTLRStringStream(input));
		ScoropGrammarParser parser = new ScoropGrammarParser(new CommonTokenStream(lexer));
		parser.sheet_ = sheet;
		parser.doc_ = sheet.getDoc();
		parser.functions_ = funRegistry;
		
		try {
			return parser.formula();
		} catch (RecognitionException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
