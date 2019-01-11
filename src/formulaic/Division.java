package formulaic;
import java.util.*;

public class Division implements Expression {
    /*
     * Abstraction Function:
     *     A division with left value represented by left and right value
     *     represented by right.
     * Representation Invariant:
     *     True
     * Safety from rep exposure:
     *     All fields are private, final and immutable.
     */
    
    private final Expression left;
    private final Expression right;
    
    
    /**
     * Creates a Division object from left and right expressions.
     * @param left left Expression.
     * @param right right Expression.
     */
    public Division(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Expression simplify() {
        Expression zero = new Number(0);
        Expression identity = new Number(1);
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();
        if (zero.equals(leftSimplified)) {
            return zero;
        }
        else if (identity.equals(rightSimplified)) {
            return leftSimplified;
        }
        return new Division(leftSimplified, rightSimplified);
    }
    
    @Override
    public Expression derivative(String wrt) {
        Expression leftDerivative = left.derivative(wrt); 
        Expression rightDerivative = right.derivative(wrt);       
        Expression numerator = new Subtraction(new Product(right, leftDerivative), new Product(left, rightDerivative));
        return new Division(numerator, new Product(right,right));
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }
}