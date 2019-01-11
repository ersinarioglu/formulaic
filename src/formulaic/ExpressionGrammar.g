@skip whitespace {
    expression ::= subtraction;
    subtraction ::= summation ('-' summation)*;
    summation ::= division ('\+' division)*;
    division ::= product ('/' product)*;
    product ::= variable ('*'? variable)*;
    variable ::= [a-zA-Z] ('\^' number)?;
    primitive ::= number | '(' expression ')';
}

number ::= '-'? [0-9]* '\.'? [0-9]+;
whitespace ::= [ \t\r\n]+;