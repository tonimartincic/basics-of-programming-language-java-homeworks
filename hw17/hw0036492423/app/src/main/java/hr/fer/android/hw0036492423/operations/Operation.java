package hr.fer.android.hw0036492423.operations;

/**
 * Class which implements this interface represents operation which accepts two integer numbers
 * and returns integer result.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public interface Operation {

    /**
     * Method accepts two integer numbers and returns integer result.
     *
     * @param first first number
     * @param second second number
     * @return result
     */
    public int calculate(int first, int second);

    /**
     * Method returns operation's symbol.
     *
     * @return operation's symbol
     */
    public String getSymbol();
}
