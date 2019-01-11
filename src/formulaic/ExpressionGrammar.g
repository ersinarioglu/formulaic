@skip whitespace {
    expression ::= subtraction;
    subtraction ::= summation ('-' summation)*;
    summation ::= division ('\+' division)*;
    division ::= product ('/' product)*;
    product ::= exponent ('*'? exponent)*;
    exponent ::= primitive ('\^' primitive)*;
    primitive ::= variable | number | '(' expression ')';
}

variable ::= [a-zA-Z];
number ::= '-'? [0-9]* '\.'? [0-9]+;
whitespace ::= [ \t\r\n]+;