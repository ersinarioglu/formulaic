package formulaic;
import java.util.*;

public class Variable implements Expression {
    /*
     * Abstraction Function:
     *     A variable represented by a single letter is represented as a Variable object
     *     that has var field with String value of that single letter. double exp represents
     *     its exponents.
     * Representation Invariant:
     *     var must be a single letter string, capital or non-capital.
     * Safety from rep exposure:
     *     Only field is private, final and immutable.
     */
    
    private final String var;
    private final double exp;
    
    
    /**
     * Creates a Variable object from a single letter String and String exp.
     * @param var single letter String name of the variable.
     * @param exp String exponent of variable.
     */
    public Variable(String var, String exp) {
        this.var = var;
        this.exp = Double.parseDouble(exp);
    }
    
    /**
     * Creates a Variable object from a single letter String and double exp.
     * @param var single letter String name of the variable.
     * @param exp double exponent of variable.
     */
    public Variable(String var, double exp) {
        this.var = var;
        this.exp = exp;
    }
    
    /**
     * Returns the name of the Variable object in String form.
     * @return String name of variable.
     */
    public String getName() {
        return this.var;
    }
    
    @Override
    public Expression simplify() {
        return this;
    }
    
    @Override
    public Expression derivative(String wrt) {
        if (this.var.equals(wrt)) {
            return new Product( new Number(exp), new Variable(this.var, this.exp - 1));
        }
        return this;
    }
    
    @Override
    public String toString() {
        return "(" + this.var + "^" + this.exp + ")";
    }
    
    @Override
    public boolean equals(Object that) {
        if (that instanceof Variable) {
            if (this.var.equals(((Variable)that).var)) {
                return true;
            }
        }
        return false;
    }
    
    
}