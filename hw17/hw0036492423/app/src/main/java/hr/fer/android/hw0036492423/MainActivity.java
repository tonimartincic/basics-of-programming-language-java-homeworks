package hr.fer.android.hw0036492423;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Class extends {@link AppCompatActivity} and represents main activity which is shown when the app
 * is started.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    /**
     * Method calls the {@link CalculusActivity}.
     */
    @OnClick(R.id.mathematics)
    void onClickMathematics() {
        Intent intent = new Intent(MainActivity.this, CalculusActivity.class);
        startActivity(intent);
    }

    /**
     * Method calls the {@link FormActivity}.
     */
    @OnClick(R.id.statistics)
    void onClickStatistics() {
        Intent intent = new Intent(MainActivity.this, FormActivity.class);
        startActivity(intent);
    }

}
