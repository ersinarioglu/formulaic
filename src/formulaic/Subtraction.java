package formulaic;
import java.util.*;

public class Subtraction implements Expression {
    /*
     * Abstraction Function:
     *     A subtraction with left value represented by left and right value
     *     represented by right.
     * Representation Invariant:
     *     True
     * Safety from rep exposure:
     *     All fields are private, final and immutable.
     */
    
    private final Expression left;
    private final Expression right;
    
    
    /**
     * Creates a Subtraction object from left and right expressions.
     * @param left left Expression.
     * @param right right Expression.
     */
    public Subtraction(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Expression simplify() {
        Expression zero = new Number(0);
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();
        if (zero.equals(rightSimplified)) {
            return leftSimplified;
        }
        return new Subtraction(leftSimplified, rightSimplified);
    }
    
    @Override
    public Expression derivative(String wrt) {
        Expression leftDerivative = left.derivative(wrt);
        Expression rightDerivative = right.derivative(wrt);
        return new Subtraction(leftDerivative, rightDerivative);
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + "-" + right.toString() + ")";
    }
}