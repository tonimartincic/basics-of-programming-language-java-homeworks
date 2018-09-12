package hr.fer.android.hw0036492423;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hr.fer.android.hw0036492423.models.DataModel;
import hr.fer.android.hw0036492423.networking.RetrofitService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Class extends {@link AppCompatActivity} and represents activity which contains one {@link EditText}
 * which accepts relative path and contains button which loads data from the accepted path and
 * displays the loaded data.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public class FormActivity extends AppCompatActivity {

    /**
     * {@link EditText} which accepts the relative path.
     */
    @BindView(R.id.inputRelativePath)
    EditText inputRelativePath;

    /**
     * Instance of {@link ImageView}.
     */
    @BindView(R.id.image)
    ImageView image;

    /**
     * First name.
     */
    @BindView(R.id.firstName)
    TextView firstName;

    /**
     * Last name.
     */
    @BindView(R.id.lastName)
    TextView lastName;

    /**
     * Phone number.
     */
    @BindView(R.id.phoneNo)
    TextView phoneNo;

    /**
     * Email.
     */
    @BindView(R.id.emailSknf)
    TextView emailSknf;

    /**
     * Spouse.
     */
    @BindView(R.id.spouse)
    TextView spouse;

    /**
     * Age.
     */
    @BindView(R.id.age)
    TextView age;

    /**
     * Instance of {@link RetrofitService}.
     */
    RetrofitService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://m.uploadedit.com")
                .build();

        service = retrofit.create(RetrofitService.class);

        image.setVisibility(View.INVISIBLE);
        firstName.setText("First name: ");
        lastName.setText("Last name: ");
        phoneNo.setText("Phone number: ");
        emailSknf.setText("Email: ");
        spouse.setText("Spouse: ");
        age.setText("Age: ");
    }

    /**
     * This method is called when user clicks load button. Method loads data from the accepted path and
     * displays the loaded data.
     */
    @OnClick(R.id.load)
    void onClickCalculate() {
        String relativePath = inputRelativePath.getText().toString();

        if(relativePath.isEmpty()) {
            Toast.makeText(FormActivity.this, "Input relative path", Toast.LENGTH_SHORT).show();

            return;
        }

        service.getData(relativePath).enqueue(new Callback<DataModel>() {
            @Override
            public void onResponse(Call<DataModel> call, Response<DataModel> response) {
                Gson gson = new Gson();
                String json = gson.toJson(response.body());

                DataModel dataModel = gson.fromJson(json, DataModel.class);

                if(dataModel == null) {
                    Toast.makeText(FormActivity.this, "Not found", Toast.LENGTH_SHORT).show();

                    return;
                }

                if(dataModel.getAvatarLocation() != null) {
                    Glide.with(FormActivity.this).load(dataModel.getAvatarLocation()).into(image);

                    image.setVisibility(View.VISIBLE);
                }

                firstName.setText("First name: " + dataModel.getFirstName());
                lastName.setText("Last name: " + dataModel.getLastName());
                phoneNo.setText("Phone number: " + dataModel.getPhoneNo());
                emailSknf.setText("Email: " + dataModel.getEmailSknf());
                spouse.setText("Spouse: " + dataModel.getSpouse());
                age.setText("Age: " + dataModel.getAge());
            }

            @Override
            public void onFailure(Call<DataModel> call, Throwable t) {
                Toast.makeText(FormActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * This method is called when user clicks phoneNo text view. Method calls the
     * accepted number.
     */
    @OnClick(R.id.phoneNo)
    void onClick() {
        if(phoneNo.getText().toString().isEmpty()) {
            return;
        }

        Intent intent = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:0991234567"));
        try {
            startActivity(intent);
        } catch(SecurityException exc) {
            Toast.makeText(FormActivity.this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when user clicks emailSknf text view. Method sends the email to the
     * accepted email address.
     */
    @OnClick(R.id.emailSknf)
    void onClickSendReport() {
        if(emailSknf.getText().toString().isEmpty()) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{emailSknf.getText().toString()});

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(FormActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
