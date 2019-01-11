@skip whitespace {
    expression ::= subtraction;
    subtraction ::= summation ('-' summation)*;
    summation ::= division ('\+' division)*;
    division ::= product ('/' product)*;
    product ::= variable ('*'? variable)*;
    power ::= primitive ('\^' number)?;
    primitive ::= variable | number | '(' expression ')';
}

variable ::= [a-zA-Z];
number ::= '-'? [0-9]* '\.'? [0-9]+;
whitespace ::= [ \t\r\n]+;