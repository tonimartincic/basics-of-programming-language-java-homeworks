package hr.fer.android.hw0036492423;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.android.hw0036492423.operations.Operation;
import hr.fer.android.hw0036492423.operations.OperationAdd;
import hr.fer.android.hw0036492423.operations.OperationDiv;
import hr.fer.android.hw0036492423.operations.OperationMul;
import hr.fer.android.hw0036492423.operations.OperationSub;

/**
 * Class extends {@link AppCompatActivity} and represents activity which gets from the user
 * two numbers and the information which operation is executed on accepted numbers and contains
 * button which executes the operation.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public class CalculusActivity extends AppCompatActivity {

    /**
     * Message key.
     */
    public static final String MESSAGE_KEY = "message";

    /**
     * {@link EditText} for the first number.
     */
    @BindView(R.id.firstInput)
    EditText firstInput;

    /**
     * {@link EditText} for the second number.
     */
    @BindView(R.id.secondInput)
    EditText secondInput;

    /**
     * {@link RadioGroup} which contains buttons for all the operations.
     */
    @BindView(R.id.operations)
    RadioGroup operationsRadioGroup;

    /**
     * Map contains all the operations.
     */
    Map<Integer, Operation> operationsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculus);

        ButterKnife.bind(this);

        operationsMap.put(R.id.addition, new OperationAdd());
        operationsMap.put(R.id.subtraction, new OperationSub());
        operationsMap.put(R.id.multiplication, new OperationMul());
        operationsMap.put(R.id.division, new OperationDiv());
    }

    /**
     * This method is called when the user clicks calculate button. Method calculates the operation
     * and calls {@link DisplayActivity} with the result of the operation.
     */
    @OnClick(R.id.calculate)
    void onClickCalculate() {
        Integer operationId = operationsRadioGroup.getCheckedRadioButtonId();
        Operation operation = operationsMap.get(operationId);

        String first = firstInput.getText().toString();
        String second = secondInput.getText().toString();

        int firstNumber = 0;
        try {
            firstNumber = Integer.parseInt(first);
        } catch (NumberFormatException exc) {
            sendErrorMessage(operation.getSymbol(), first, second, exc.getMessage());

            return;
        }

        int secondNumber = 0;
        try {
            secondNumber = Integer.parseInt(second);

            if(secondNumber == 0) {
                sendErrorMessage(operation.getSymbol(), first, second, "Can't divide with zero.");

                return;
            }
        } catch (NumberFormatException exc) {
            sendErrorMessage(operation.getSymbol(), first, second, exc.getMessage());

            return;
        }

        int result = operation.calculate(firstNumber, secondNumber);
        String message = String.format("Rezultat operacije %d %s % d je %d.", firstNumber, operation.getSymbol(), secondNumber, result);

        Intent intent = new Intent(CalculusActivity.this, DisplayActivity.class);
        intent.putExtra(MESSAGE_KEY, message);

        startActivity(intent);
    }

    /**
     * Method sends error message to the {@link DisplayActivity}.
     *
     * @param symbol operation symbol
     * @param first first number
     * @param second second number
     * @param error error
     */
    private void sendErrorMessage(String symbol, String first, String second, String error) {
        String message = String.format("Prilikom obavljanja operacije %s nad unosima %s i %s došlo je do sljedeće grešlke: %s.",
                symbol, first, second, error);

        Intent intent = new Intent(CalculusActivity.this, DisplayActivity.class);
        intent.putExtra(MESSAGE_KEY, message);

        startActivity(intent);
    }

}
