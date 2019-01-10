package formulaic;


/**
 * The immutable type that represents an algebraic formula.
 */

public interface Expression {
    /*
     * Datatype definition:
     * Expression = Number(double) + 
     * 
     */
    
    
    /**
     * Returns a simplified version of the formula given.
     * @param exp Expression object to be simplified.
     * @return Expression representing the simplified version of the formula.
     */
    public Expression simplify(Expression exp);
    
    
    /**
     * Returns the derivative of the formula given.
     * @param exp Expression object to be derived.
     * @return Expression representing the simplified version of the formula.
     */
    public Expression derivative(Expression exp);
    
}
