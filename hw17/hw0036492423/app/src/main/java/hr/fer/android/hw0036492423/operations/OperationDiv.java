package hr.fer.android.hw0036492423.operations;

/**
 * Class implements {@link Operation} and represents operation which divides two numbers.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public class OperationDiv implements Operation {

    /**
     * Operation symbol.
     */
    private String symbol = "/";

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public int calculate(int first, int second) {
        if(second == 0) {
            throw new IllegalArgumentException("Second argument can not be 0.");
        }

        return first / second;
    }
}
