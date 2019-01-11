package formulaic;
import java.io.File;
import java.io.IOException;
import java.util.*;

import edu.mit.eecs.parserlib.ParseTree;
import edu.mit.eecs.parserlib.Parser;
import edu.mit.eecs.parserlib.UnableToParseException;
import edu.mit.eecs.parserlib.Visualizer;

public class ExpressionParser {
    
    
    private static final Parser<Grammar> PARSER = makeParser();
    
    private static enum Grammar {
        NUMBER, VARIABLE, PRIMITIVE, POWER, PRODUCT, DIVISION, SUMMATION, SUBTRACTION, EXPRESSION, WHITESPACE
    }
    
    /**
     * Compile the grammar into a parser.
     * 
     * @param grammarFilename <b>Must be in this class's Java package.</b>
     * @return parser for the grammar
     * @throws RuntimeException if grammar file can't be read or has syntax errors
     */
    private static Parser<Grammar> makeParser() {
        try {
            // read the grammar as a file, relative to the project root.
            final File grammarFile = new File("src/formulaic/ExpressionGrammar.g");
            return Parser.compile(grammarFile, Grammar.EXPRESSION);

            // A better way would read the grammar as a "classpath resource", which would allow this code 
            // to be packed up in a jar and still be able to find its grammar file:
            //
            // final InputStream grammarStream = IntegerExpression.class.getResourceAsStream("IntegerExpression.g");
            // return Parser.compile(grammarStream, IntegerGrammar.EXPRESSION);
            //
            // See http://www.javaworld.com/article/2077352/java-se/smartly-load-your-properties.html
            // for a discussion of classpath resources.
            

        // Parser.compile() throws two checked exceptions.
        // Translate these checked exceptions into unchecked RuntimeExceptions,
        // because these failures indicate internal bugs rather than client errors
        } catch (IOException e) {
            throw new RuntimeException("can't read the grammar file", e);
        } catch (UnableToParseException e) {
            e.printStackTrace();
            throw new RuntimeException("the grammar has a syntax error", e);
        }
    }
    
    /**
     * Parse a string into an expression.
     * @param string string to parse
     * @return IntegerExpression parsed from the string
     * @throws UnableToParseException if the string doesn't match the IntegerExpression grammar
     */
    public static Expression parse(final String string) throws UnableToParseException {
        // parse the example into a parse tree
        final ParseTree<Grammar> parseTree = PARSER.parse(string);
        //System.out.println("parse tree " + parseTree);

        // display the parse tree in a web browser, for debugging only
        //Visualizer.showInBrowser(parseTree);

        // make an AST from the parse tree
        final Expression expression = makeAbstractSyntaxTree(parseTree);
        //System.out.println("AST " + expression);
        
        return expression;
    }
    
    /**
     * Convert a parse tree into an abstract syntax tree.
     * 
     * @param parseTree constructed according to the grammar in IntegerExpression.g
     * @return abstract syntax tree corresponding to parseTree
     */
    private static Expression makeAbstractSyntaxTree(final ParseTree<Grammar> parseTree) {
        switch (parseTree.name()) {
        case EXPRESSION: // expression ::= subtraction;
        {
            final ParseTree<Grammar> child = parseTree.children().get(0);
            return makeAbstractSyntaxTree(child);
        }
        
        case SUBTRACTION: // subtraction ::= summation ('-' summation)*;
        {
            final List<ParseTree<Grammar>> children = parseTree.children();
            if (children.size() == 1) {
                return makeAbstractSyntaxTree(children.get(0)); 
            }
            
            Expression subtraction = makeAbstractSyntaxTree(children.get(0));
            
            for (int i = 1; i < children.size(); i++) {
                subtraction = new Subtraction(subtraction,makeAbstractSyntaxTree(children.get(i)));
            }
            return subtraction;        
        }
        
        case SUMMATION: // summation ::= division ('\+' division)*;
        {
            final List<ParseTree<Grammar>> children = parseTree.children();
            if (children.size() == 1) {
                return makeAbstractSyntaxTree(children.get(0)); 
            }
            
            Expression summation = makeAbstractSyntaxTree(children.get(0));
            
            for (int i = 1; i < children.size(); i++) {
                summation = new Summation(summation, makeAbstractSyntaxTree(children.get(i)));
            }
            return summation; 
        }
        
        case DIVISION: // division ::= product ('/' product)*;
        {
            final List<ParseTree<Grammar>> children = parseTree.children();
            if (children.size() == 1) {
                return makeAbstractSyntaxTree(children.get(0)); 
            }
            
            Expression division = makeAbstractSyntaxTree(children.get(0));
            
            for (int i = 1; i < children.size(); i++) {
                division = new Division(division, makeAbstractSyntaxTree(children.get(i)));
            }
            return division; 
        }
        
        case PRODUCT: // product ::= exponent ('*'? exponent)*;
        {
            final List<ParseTree<Grammar>> children = parseTree.children();
            if (children.size() == 1) {
                return makeAbstractSyntaxTree(children.get(0)); 
            }
            
            Expression product = makeAbstractSyntaxTree(children.get(0));
            
            for (int i = 1; i < children.size(); i++) {
                product = new Product(product, makeAbstractSyntaxTree(children.get(i)));
            }
            return product; 
        }
        
        
        
        case PRIMITIVE: // primitive ::= variable | number | '(' expression ')';
        {
            final List<ParseTree<Grammar>> children = parseTree.children();
            return makeAbstractSyntaxTree(children.get(0));
        }
        
        case NUMBER: // number ::= '-'? [0-9]* '\.'? [0-9]+;
        {
            return new Number(Double.parseDouble(parseTree.text()));
        }
        
        case VARIABLE: // variable ::= [a-zA-Z];
        {
            return new Variable(parseTree.text());
        }
        
        case POWER: // power ::= primitive ('\^' number)?;
        {
            final List<ParseTree<Grammar>> children = parseTree.children();
            if (children.size() == 1) {
                return makeAbstractSyntaxTree(children.get(0)); 
            }          
            return new Power(makeAbstractSyntaxTree(children.get(0)), new Number(Double.parseDouble(children.get(1).text())));           
        }
        
        default:
            throw new AssertionError("Should never get here.");     
        
        }
    }
    
    
    
}