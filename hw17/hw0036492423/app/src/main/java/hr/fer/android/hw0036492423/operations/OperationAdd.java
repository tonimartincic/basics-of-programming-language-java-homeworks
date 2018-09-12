package hr.fer.android.hw0036492423.operations;

/**
 * Class implements {@link Operation} and represents operation which adds two numbers.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public class OperationAdd implements Operation {

    /**
     * Operation symbol.
     */
    private String symbol = "+";

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public int calculate(int first, int second) {
        return first + second;
    }
}
