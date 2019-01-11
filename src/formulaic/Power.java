package formulaic;
import java.util.*;

public class Power implements Expression {
    /*
     * Abstraction Function:
     *     A power operation with base base and exponent exp.
     * Representation Invariant:
     *     True
     * Safety from rep exposure:
     *     All fields are private, final and immutable.
     */
    
    private final Expression base;
    private final Number exp;
    
    
    /**
     * Creates a Power object from base and exp expressions.
     * @param base base Expression.
     * @param exp exponent Expression.
     */
    public Power(Expression base, Number exp) {
        this.base = base;
        this.exp = exp;
    }
    
    @Override
    public Expression simplify() {
        Expression zero = new Number(0);
        Expression identity = new Number(1);
        Expression baseSimplified = base.simplify();
        
        if (zero.equals(baseSimplified)) {
            return zero;
        }
        if (identity.equals(baseSimplified)) {
            return identity;
        }
        if (zero.equals(exp)) {
            return identity;
        }
        if (identity.equals(exp)) {
            return base;
        }
        return new Power(baseSimplified, exp);
    }
    
    @Override
    public Expression derivative(String wrt) {
        Expression baseDerivative = base.derivative(wrt);
        double exponent = exp.value();
        return new Product(new Product(exp , new Power(base, new Number(exponent - 1))), baseDerivative);
    }
    
    @Override
    public String toString() {
        return "(" + base.toString() + "^" + exp.toString() + ")";
    }
}