grammar ScoropGrammar;

options {
  language = Java;
}

@header {
  package net.kaczmarzyk.moose.core.parser;
  
  import net.kaczmarzyk.moose.core.document.CellAddress;
  import net.kaczmarzyk.moose.core.document.Dimension;
  import net.kaczmarzyk.moose.core.document.ObjectAddress;
  import net.kaczmarzyk.moose.core.document.Scalar;
  import net.kaczmarzyk.moose.core.document.Path;
  import net.kaczmarzyk.moose.core.document.Sheet;
  import net.kaczmarzyk.moose.core.document.Document;
  import net.kaczmarzyk.moose.core.document.Coordinate;
  
  import net.kaczmarzyk.moose.core.expression.FunctionCall;
  import net.kaczmarzyk.moose.core.expression.Expression;
  import net.kaczmarzyk.moose.core.expression.ObjectReference;
  import net.kaczmarzyk.moose.core.expression.AreaReference;
  import net.kaczmarzyk.moose.core.expression.Constant;
  
  import net.kaczmarzyk.moose.core.function.FunctionRegistry;
  import net.kaczmarzyk.moose.core.function.Function;
  
  import java.util.List;
  import java.util.ArrayList;
}

@lexer::header {
  package net.kaczmarzyk.moose.core.parser;
}

@members {
  Sheet sheet_;
  Document doc_;
  FunctionRegistry functions_;
  private Dimension dim_;
}


formula
  :
    '=' expression EOF
  ;


// EXPRESSIONS:

term returns [Expression result]
  : 
  | '(' e=expression ')' {result = e;}
  | r=ref {result = r;}
  | fc=funCall {result = fc;}
  | c=constant { result = c;}
  ;

funCall returns [FunctionCall result]
  : f=fun '(' a=args ')'
    {
      result = new FunctionCall(f, a);
    }
  ;

fun returns [Function result]
  : fname=(CHAR+) {result = functions_.get($fname.text);} // TODO functions with numbers in name?
  ;

args returns [Expression[\] result]
  :
    { List<Expression> temp = new ArrayList<Expression>();}
    e=expression {temp.add(e);} (',' e2=expression {temp.add(e2);} )*
    {result = temp.toArray(new Expression[temp.size()]);}
  ;

//negation
//  : ('~')* term
//  ;
//
//unary
//  : ('+' | '-')* negation
//  ;

mult returns [Expression result]
  : t1=term (('*' | '/') t2=term)?
    {
      if (t2 != null) {
        result = new FunctionCall(functions_.get("add"), t1, t2);
      } else {
        result = t1;
      }
    }
  ;
  
expression returns [Expression result] //add
  : m1=mult (op=('+' | '-') m2=mult)?
    {
      if (m2 != null) {
        result = new FunctionCall(functions_.get($op.text == "+" ? "add" : "sub"), m1, m2);
      } else {
        result = m1;
      }
    }
  ;

//relation
//  : add (('=' | '<>' | '<' | '<=' | '>' | '>=') add)*
//  ;
//  
//expression
//  : relation (('&&' | '||') relation)*
//  ;



// AREA or OBJECT REFERENCE:

ref returns [Expression result]
  :
    addr1=objAddr (':' addr2=objAddr)?
    {
      if (addr2 == null) {
        result = new ObjectReference(addr1);
      } else {
        result = new AreaReference(addr1, addr2);
      }
    }
  ;

objAddr returns [ObjectAddress result]
  :
    s=sheet? c=coords p=path?
    {
      result = new ObjectAddress(new CellAddress(sheet_, c), p != null ? p : Path.IN_PLACE);
    }
  ;

coord returns [Coordinate result]
  :
    dim shift=INT
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
    name '!' // TODO allow to use some (all?) of them (sheet in addres doesn't have to work for fancy names though)
    {
      sheet_ = doc_.getSheet($name.text); // not thread safe!
    }
  ;
  
name
  : (~(',' | '!' | '#' | '(' | ')' | '[' | ']' | '*' | '+' | '/' | '-' | '~' | '>' | '=' | '<' | '|' | '&' | ':')+)
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

constant returns [Constant result]
  : d=(INT '.' INT)
    {
      result = new Constant(new Scalar<Double>(Double.valueOf($d.text)));
    }
  ;

FORM: '=';
R: 'R';
C: 'C';
INT: '0'..'9'+;
CHAR: 'a'..'z' | 'A'..'Z';

WS: '\t'+ {$channel = HIDDEN;};