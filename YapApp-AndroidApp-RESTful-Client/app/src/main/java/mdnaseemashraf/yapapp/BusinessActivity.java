package mdnaseemashraf.yapapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import mdnaseemashraf.yapapp.models.YapBusinesses;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessActivity extends AppCompatActivity {

    private RecyclerView businessRecyclerView;

    //RETROFIT
    public static final String BASE_URL = "http://192.168.1.4:8080/Yap/"; //Internal Router IP
    private static Retrofit retrofit = null;

    private final static int APP_ID = 11;
    private final static String REQUEST_TYPE = "search";
    private final static String FORM = "lenient"; //"lenient" or "strict",
    private final static String KEYWORDS = "s";//"sta";
    private final static String CITY = "San";
    private final static String STATE = "C";
    private final static String TYPE = "restaurant"; //"restaurant", "shop", "hotel", "cafe" & "bar".
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        businessRecyclerView = (RecyclerView) findViewById(R.id.business_recycler_view);

        RecyclerView.LayoutManager businesslayoutManager = new LinearLayoutManager(getApplicationContext());
        businessRecyclerView.setLayoutManager(businesslayoutManager);

        businessRecyclerView.setItemAnimator(new DefaultItemAnimator());

        connectAndGetApiData();
    }

    public void connectAndGetApiData() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        YapApiService yapApiService = retrofit.create(YapApiService.class);

        Call<YapBusinesses> call = yapApiService.getSearchedBusiness(APP_ID, REQUEST_TYPE, FORM,
                KEYWORDS, CITY, STATE, TYPE);

        call.enqueue(new Callback<YapBusinesses>() {
            @Override
            public void onResponse(Call<YapBusinesses> call, Response<YapBusinesses> response) {
                List<YapBusinesses.YBusiness> businessesIN = response.body().getBusinesses();
                businessRecyclerView.setAdapter(new BusinessRecyclerAdapter(businessesIN));
                businessRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<YapBusinesses> call, Throwable throwable) {
                Log.e("Retrofit Data Error", throwable.toString());
            }
        });
    }
}
