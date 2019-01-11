package formulaic;
import java.util.*;

public class Number implements Expression {
    /*
     * Abstraction Function:
     *     The value stored in the double variable number, is the value
     *     number that the object represents.
     * Representation Invariant:
     *     True
     * Safety from Rep Exposure:
     *     The only field is private final and immutable.
     */
    
    private final double number;
    
    
    /**
     * Creates a new Number instance.
     * @param double number, the number that this object will represent.
     */
    public Number(double number) {
        this.number = number;
    }
    
    
    @Override
    public Expression simplify() {
        return this;
    }
    
    @Override
    public Expression derivative(String wrt) {
        return new Number(0);
    }
    
    @Override
    public String toString() {
        return Double.toString(number);
    }
    

    
}