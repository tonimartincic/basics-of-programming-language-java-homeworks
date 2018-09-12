package hr.fer.android.hw0036492423;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Class extends {@link AppCompatActivity} and represents activity which displays the
 * result of the operation.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public class DisplayActivity extends AppCompatActivity {

    /**
     * {@link TextView} which contains message.
     */
    @BindView(R.id.message)
    TextView message;

    /**
     * Message as String.
     */
    String stringMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        ButterKnife.bind(this);

        stringMessage = getIntent().getStringExtra(CalculusActivity.MESSAGE_KEY);
        message.setText(stringMessage);
    }

    /**
     * Method calls the {@link CalculusActivity}.
     */
    @OnClick(R.id.ok)
    void onClickOk() {
        Intent intent = new Intent(DisplayActivity.this, CalculusActivity.class);
        startActivity(intent);
    }

    /**
     * Method sends the report to the ana@baotic.org.
     */
    @OnClick(R.id.sendReport)
    void onClickSendReport() {
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{"ana@baotic.org"});
        i.putExtra(Intent.EXTRA_SUBJECT, "0036492423: dz report");
        i.putExtra(Intent.EXTRA_TEXT, stringMessage);

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DisplayActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
