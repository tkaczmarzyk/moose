grammar ScoropGrammar;

options {
  language = Java;
}

@header {
  package net.kaczmarzyk.moose.core.parser;
  
  import net.kaczmarzyk.moose.core.document.CellAddress;
  import net.kaczmarzyk.moose.core.document.ObjectAddress;
  import net.kaczmarzyk.moose.core.document.Path;
  import net.kaczmarzyk.moose.core.document.Sheet;
  
  import java.util.List;
  import java.util.ArrayList;
}

@lexer::header {
  package net.kaczmarzyk.moose.core.parser;
}

@members {
  Sheet sheet_;
}


objAddr returns [ObjectAddress result]
  : sheet=SHEET? (REL_COORD | ABS_COORD) (REL_COORD | ABS_COORD)+ p=path? 
    {
      result = new ObjectAddress(new CellAddress(sheet_, null), p);
    }
  ;

path returns [Path result]
  : {
      List<String> props = new ArrayList<>();
    }
    '#' firstProp=IDENT {props.add(firstProp.getText());} ('.' prop=IDENT {props.add(prop.getText());})*
    {
      result = new Path(props);
    }
  ;

SHEET: (CHAR | INT) (CHAR | INT | ' ')*'!';

REL_COORD: CHAR INT;
ABS_COORD: CHAR ABS_SHIFT;

ABS_SHIFT: ('[' INT+ ']');

IDENT: CHAR (CHAR|INT)+;
INT: '0'..'9';
CHAR: ('a'..'z' | 'A'..'Z');

WS: '\t'+ {$channel = HIDDEN;};