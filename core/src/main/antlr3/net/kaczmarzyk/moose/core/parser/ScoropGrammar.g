grammar ScoropGrammar;

options {
  language = Java;
}

@header {
  package net.kaczmarzyk.moose.core.parser;
  
  import net.kaczmarzyk.moose.core.document.CellAddress;
  import net.kaczmarzyk.moose.core.document.Dimension;
  import net.kaczmarzyk.moose.core.document.ObjectAddress;
  import net.kaczmarzyk.moose.core.document.DataObject;
  import net.kaczmarzyk.moose.core.document.MapData;
  import net.kaczmarzyk.moose.core.document.Scalar;
  import net.kaczmarzyk.moose.core.document.Path;
  import net.kaczmarzyk.moose.core.document.Sheet;
  import net.kaczmarzyk.moose.core.document.Document;
  import net.kaczmarzyk.moose.core.document.Formula;
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


parse returns [DataObject result]
  : f=formula EOF { result = f; }
  | m=map EOF {result = m;}
  | s=scalar2 EOF {result = s;}
  ;

formula returns [Formula formula]
  :
    '=' e=expression EOF { formula = new Formula(e);}
  ;

scalar2 returns [Scalar result]
  :
    str=string
    {
      String s = $str.text;
      if (s.matches("^-?\\d+$")) {
        result = new Scalar<>(Integer.valueOf(s));
      } else if (s.matches("^-?\\d+\\.\\d+$")) {
        result = new Scalar<>(Double.valueOf(s));
      } else {
        result = new Scalar<>(s);
      }
    }
  ;

string
  : ~('"' | '{' | '=') .*
  | quotedString
  ;

// EXPRESSIONS:

term returns [Expression result]
  : '(' e=expression ')' {result = e;}
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
  : fname=ident {result = functions_.get($fname.text);} // TODO functions with numbers in name?
  ;

args returns [Expression[\] result]
  :
    { List<Expression> temp = new ArrayList<Expression>();} //TODO linked list
    e=expression {temp.add(e);} (',' ' '* e2=expression {temp.add(e2);} )* // TODO suppress WS globally (but allow in sheet name)
    {result = temp.toArray(new Expression[temp.size()]);}
  ;

//negation
//  : ('~')* term
//  ;

unary returns [Expression result]
  : op=('+' | '-')? t=term // TODO ? suport multiple - or +
    {
      if (op == null || $op.text.equals("+")) {
        result = t;
      } else {
        result = new FunctionCall(functions_.get("neg"), t);
      }
    }
  ;

mult returns [Expression result]
  :
    {
      List<Expression> exprs = new ArrayList<Expression>();
    }
    t1=unary {exprs.add(t1);} (('*' t2=unary {exprs.add(t2);}) | ('/' t3=unary {exprs.add(new FunctionCall(functions_.get("inv"), t3));}))*
    {
      if (exprs.size() > 1) {
        result = new FunctionCall(functions_.get("mul"), exprs.toArray(new Expression[exprs.size()]));
      } else {
        result = exprs.get(0);
      }
    }
  ;
  
expression returns [Expression result] //add
  :
    {
      List<Expression> exprs = new ArrayList<Expression>();
    }
    m1=mult {exprs.add(m1);} (op=('+' | '-') m2=mult {exprs.add($op.text.equals("+") ? m2 : new FunctionCall(functions_.get("-"), m2));})*
    {
      if (exprs.size() > 1) {
        result = new FunctionCall(functions_.get("+"), exprs.toArray(new Expression[exprs.size()]));
      } else {
        result = exprs.get(0);
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
    addr=objAddr
    {
      result = new ObjectReference(addr);
    }
  |
    s=sheet? c=coords ':' addr2=objAddr
    {
      result = new AreaReference(new ObjectAddress(new CellAddress(sheet_, c), addr2.getPath()), addr2);
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
    dim i=integer
    {
      result = Coordinate.rel(dim_, i);
    }
  |
    dim '[' i=integer ']'
    {
      result = Coordinate.abs(dim_, i);
    }
  ;

integer returns [Integer result]
  : minus='-'? INT
    {
      Integer i = Integer.valueOf($INT.text);
      result = minus != null ? -i : i;
    }
  ;

dim
  :
    (R | C | ',')
  ;

coords returns [List<Coordinate> coords]
  :
    {
      int i = 0;
      coords = new ArrayList<Coordinate>();
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
  : (~('"' | ',' | '!' | '#' | '(' | ')' | '[' | ']' | '*' | '+' | '/' | '-' | '~' | '>' | '=' | '<' | '|' | '&' | ':')+)
  ;

path returns [Path result]
  : {
      List<String> props = new ArrayList<>();
    }
    '#' firstProp=ident {props.add($firstProp.text);} ('.' prop=ident {props.add($prop.text);})*
    {
      result = new Path(props);
    }
  ;

ident
  :
    CHARS (INT | CHARS)*
  ;

// constants, values

constant returns [Constant result]
  : o=object
    { result = new Constant(o); }
  ;

object returns [DataObject o]
  : s=scalar {o = s;}
  | m=map {o = m;}
  ;

map returns [MapData result]
  : { result = new MapData(); }
    '{' fprop=ident ':' o=object {result.setProperty(Path.of($fprop.text), o);}
    (',' prop=ident ':' o=object {result.setProperty(Path.of($prop.text), o);} )*
    '}'
  ;

scalar returns [Scalar result]
  : REAL
    {
      result = new Scalar<Double>(Double.valueOf($REAL.text));
    }
  | INT
    {
      result = new Scalar<Integer>(Integer.valueOf($INT.text));  // TODO parse as double ?
    }
  | s=quotedString
    {
      result = new Scalar<String>(s);
    }
  ;
  
quotedString returns [String str]
  :
    { str = "";}
    QUOTE (s=strWithoutQuotes {str = $s.text;})?  QUOTE  // FIXME _ inside quotes breaks everything // TODO escaped quotes
  ;
  
strWithoutQuotes
  : ~QUOTE+
  ;

FORM: '=';
R: 'R';
C: 'C';
REAL: INT '.' INT;
INT: '0'..'9'+;
CHARS: ('a'..'z' | 'A'..'Z')+;
QUOTE: '"';
WS: '\t'+ {$channel = HIDDEN;};
