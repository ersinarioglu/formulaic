package formulaic;
import java.util.*;

public class Variable implements Expression {
    /*
     * Abstraction Function:
     *     A variable represented by a single letter is represented as a Variable object
     *     that has var field with String value of that single letter. 
     * Representation Invariant:
     *     var must be a single letter string, capital or non-capital.
     * Safety from rep exposure:
     *     Only field is private, final and immutable.
     */
    
    private final String var;
    
    
    
    /**
     * Creates a Variable object from a single letter String var.
     * @param var single letter String name of the variable.
     */
    public Variable(String var) {
        this.var = var;
        
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
            return new Number(1);
        }
        return this;
    }
    
    @Override
    public String toString() {
        return "(" + this.var + ")";
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