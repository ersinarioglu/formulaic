package formulaic;
import java.util.*;

public class Product implements Expression {
    /*
     * Abstraction Function:
     *     Represents a product of two values in which the left multiplicand is in left
     *     and right multiplicand is in right.
     * Representation Invariant:
     *     True
     * Safety from rep exposure:
     *     All fields are private, final and immutable.   
     */
    
    private final Expression left;
    private final Expression right;
    
    /**
     * Creates a Product object from left and right expressions.
     * @param left left Expression
     * @param right right Expression
     */
    public Product(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public Expression simplify() {
        Expression leftSimplified = left.simplify();
        Expression rightSimplified = right.simplify();
        Expression identity = new Number(1);
        if (identity.equals(leftSimplified)) {
            return rightSimplified;
        }
        else if (identity.equals(rightSimplified)) {
            return leftSimplified;
        }
        
        return new Product(leftSimplified, rightSimplified);
    }
    
    @Override
    public Expression derivative(String wrt) {
        Expression leftDerivative = left.derivative(wrt);
        Expression rightDerivative = right.derivative(wrt);
        return new Summation(new Product(left, rightDerivative), new Product(leftDerivative, right));
    }
    
    @Override
    public String toString() {
        return "(" + left.toString() + "*" + right.toString() + ")";
    }
}