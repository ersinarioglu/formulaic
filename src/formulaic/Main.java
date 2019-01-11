package formulaic;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import edu.mit.eecs.parserlib.UnableToParseException;

/**
 * Console interface to the expression system.
 */

public class Main {
    
    /**
     * Read expression and command inputs from the console and output results.
     * An empty input terminates the program.
     * @param args unused
     * @throws IOException if there is an error reading the input
     * @throws UnableToParseException 
     */
    public static void main(String[] args) throws IOException, UnableToParseException {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        Expression currentExpression = new Number(1);

        while (true) {
            System.out.print("> ");
            final String input = in.readLine();
            
            if (input.isEmpty()) {
                System.exit(0); // exits the program
            }

            try {
                String[] splitted = input.split("\\s+");
                
                if (input.equals(SIMPLIFY_COMMAND)) {
                    System.out.println(currentExpression.simplify());
                } else if (splitted[0].equals(DERIVE_COMMAND)) {
                    Expression derivative = currentExpression.derivative(splitted[1]);
                    System.out.println(derivative.simplify());
                    
                } else {
                    final Expression expression = ExpressionParser.parse(input);
                    final String output = expression.toString();
                    currentExpression = expression;
                    System.out.println(output);
                }
                
            } catch (NoSuchElementException nse) {
                // currentExpression was empty
                System.out.println("must enter an expression before using this command");
            } catch (RuntimeException re) {
                System.out.println(re.getClass().getName() + ": " + re.getMessage());
            }
        }
    }
    
    private static final String SIMPLIFY_COMMAND = "!simple";
    private static final String DERIVE_COMMAND = "!derive";
    

}