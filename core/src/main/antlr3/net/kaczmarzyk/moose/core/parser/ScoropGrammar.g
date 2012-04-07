grammar ScoropGrammar;

options {
  language = Java;
}

@header {
  package net.kaczmarzyk.moose.core.parser;
  
  import net.kaczmarzyk.moose.core.document.CellAddress;
  import net.kaczmarzyk.moose.core.document.Dimension;
  import net.kaczmarzyk.moose.core.document.ObjectAddress;
  import net.kaczmarzyk.moose.core.document.Path;
  import net.kaczmarzyk.moose.core.document.Sheet;
  import net.kaczmarzyk.moose.core.document.Document;
  import net.kaczmarzyk.moose.core.document.Coordinate;
  
  import java.util.List;
  import java.util.ArrayList;
}

@lexer::header {
  package net.kaczmarzyk.moose.core.parser;
}

@members {
  Sheet sheet_;
  Document doc_;
  private Dimension dim_;
}


objAddr returns [ObjectAddress result]
  :
    s=sheet? c=coords p=path?
    {
      result = new ObjectAddress(new CellAddress(sheet_, c), p);
    }
  ;

coord returns [Coordinate result]
  :
    dim  shift=INT
    {
      result = Coordinate.rel(dim_, Integer.parseInt(shift.getText()));
    }
  |
    dim '[' shift=INT ']'
    {
      result = Coordinate.abs(dim_, Integer.parseInt(shift.getText()));
    }
  ;

dim
  :
    (R | C | ',')
  ;

coords returns [List<Coordinate<?>> coords]
  :
    {
      int i = 0;
      coords = new ArrayList<Coordinate<?>>();
    }
    (
      {
        dim_ = sheet_.getDimensions().get(i);
      }
      c=coord
	    {
	      coords.add(c);
	      i++;
	    }
	  )+
  ;

sheet
  :
    name
    {
      sheet_ = doc_.getSheet($name.text); // not thread safe!
    }
    '!'
  ;

name
  :
    (.)+
  ;

path returns [Path result]
  : {
      List<String> props = new ArrayList<>();
    }
    '#' firstProp=property {props.add($firstProp.text);} ('.' prop=property {props.add($prop.text);})*
    {
      result = new Path(props);
    }
  ;

property
  :
    CHAR (CHAR|INT)+
  ;

R: 'R';
C: 'C';
INT: '0'..'9'+;
CHAR: 'a'..'z' | 'A'..'Z';

WS: '\t'+ {$channel = HIDDEN;};