package hr.fer.android.hw0036492423.networking;

import hr.fer.android.hw0036492423.models.DataModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Class represents retrofit service.
 *
 * @author Toni Martinčić
 * @version 1.0
 */
public interface RetrofitService {

    @GET("{path}")
    Call<DataModel> getData(@Path("path") String path);

}
