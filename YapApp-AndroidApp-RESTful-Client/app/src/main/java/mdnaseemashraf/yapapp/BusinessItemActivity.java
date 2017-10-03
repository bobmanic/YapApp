package mdnaseemashraf.yapapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mdnaseemashraf.yapapp.models.YapReviews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BusinessItemActivity extends AppCompatActivity {

    String business_name, neighbourhood, city, state, type;
    double blat, blong;
    float rating;

    //RETROFIT
    public static final String BASE_URL = "http://192.168.1.4:8080/Yap/"; //Internal Router IP
    private static Retrofit retrofit = null;

    private final static int APP_ID = 11;
    private static String BUSINESS_ID = "bid";
    private static String REQUEST_TYPE = "reviews";
    private String USER_NAME = "username placeholder";
    private String REVIEW = "review placeholder";
    private int RATING = 0;

    AppCompatDialog alertDialog;
    RecyclerView reviewRecyclerView;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_item);

        Intent incomingIntent = getIntent();
        business_name = incomingIntent.getStringExtra("bname");
        neighbourhood = incomingIntent.getStringExtra("bneigh");
        blat = incomingIntent.getDoubleExtra("blat", 0);
        blong = incomingIntent.getDoubleExtra("blong", 0);
        city = incomingIntent.getStringExtra("bcity");
        state = incomingIntent.getStringExtra("bstate");
        type = incomingIntent.getStringExtra("btype");
        rating = incomingIntent.getFloatExtra("brating", 0);

        BUSINESS_ID = incomingIntent.getStringExtra("bid");

        TextView tvBusinessName = (TextView) findViewById(R.id.tvBusinessName);
        tvBusinessName.setText(business_name);
        TextView tvNeighbourhood = (TextView) findViewById(R.id.tvNeighbourhood);
        tvNeighbourhood.setText(neighbourhood);
        TextView tvCoordinates = (TextView) findViewById(R.id.tvCoordinates);
        tvCoordinates.setText("(" + blat + "," + blong + ")");
        TextView tvCity = (TextView) findViewById(R.id.tvCity);
        tvCity.setText(city);
        TextView tvState = (TextView) findViewById(R.id.tvState);
        tvState.setText(state);
        TextView tvType = (TextView) findViewById(R.id.tvType);
        tvType.setText(type);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setRating(rating);

        reviewRecyclerView = (RecyclerView) findViewById(R.id.review_recycler_view);

        RecyclerView.LayoutManager businesslayoutManager = new LinearLayoutManager(getApplicationContext());
        reviewRecyclerView.setLayoutManager(businesslayoutManager);

        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());

        connectAndGetApiData();

        fab = (FloatingActionButton) findViewById(R.id.fab);

        //Dialog Creation
        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.dialog_add_review, null, false);

        AlertDialog.Builder alertDialogBuilder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            alertDialogBuilder = new AlertDialog.Builder(BusinessItemActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            alertDialogBuilder = new AlertDialog.Builder(BusinessItemActivity.this, R.style.AppCompatAlertDialogStyle);
        }

        alertDialogBuilder.setView(promptsView);
        alertDialogBuilder.setTitle("Add Review");

        final EditText userNameInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserName);

        final EditText userReviewInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserReview);

        final Spinner mspin = (Spinner) promptsView.findViewById(R.id.spinner1);
        Integer[] items = new Integer[]{1, 2, 3, 4, 5};
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_item, items);
        mspin.setAdapter(adapter);

        // set dialog message
        alertDialog = alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                USER_NAME = userNameInput.getText().toString();
                                REVIEW = userReviewInput.getText().toString();
                                RATING = Integer.parseInt(mspin.getSelectedItem().toString());

                                dialog.dismiss();

                                if ((REVIEW != null) & (RATING != 0) & (USER_NAME != null)) {
                                    Log.println(Log.ERROR, "In Dialog Status - ", "Invoking Retrofit POST Method!");
                                    connectAndPostApiData();
                                }
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create();
        //Dialog Creation Ends
    }

    @Override
    protected void onResume() {
        super.onResume();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        if (alertDialog != null)
            alertDialog.dismiss();
    }

    public void connectAndGetApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        YapApiService yapApiService = retrofit.create(YapApiService.class);

        Call<YapReviews> call = yapApiService.getBusinessReviews(APP_ID, REQUEST_TYPE, BUSINESS_ID);

        call.enqueue(new Callback<YapReviews>() {
            @Override
            public void onResponse(Call<YapReviews> call, Response<YapReviews> response) {
                List<YapReviews.YReview> reviewsIN = response.body().getReviews();
                reviewRecyclerView.setAdapter(new ReviewsRecyclerAdapter(reviewsIN));
                reviewRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<YapReviews> call, Throwable throwable) {
                Log.e("Retrofit Data Error", throwable.toString());
            }
        });
    }

    public void connectAndPostApiData() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        YapApiService yapApiService = retrofit.create(YapApiService.class);

        //Ignored in the grand scheme of things in lieu of Server Time Stamp
        Date d = Calendar.getInstance().getTime(); // Current time
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Set your date format
        String currentData = sdf.format(d);
        String DATE = currentData;
        REQUEST_TYPE = "post_review";
        //

        /*Log.println(Log.ERROR,"RetroFit Status ", "Dataset to POST");
        Log.println(Log.ERROR,"API APPID ", Integer.toString(APP_ID));
        Log.println(Log.ERROR,"API BUSINESSID ", BUSINESS_ID);
        Log.println(Log.ERROR,"API RATING ", Integer.toString(RATING));
        Log.println(Log.ERROR,"API DATE ", DATE);
        Log.println(Log.ERROR,"API USER ", USER_NAME);
        Log.println(Log.ERROR,"API REVIEW ", REVIEW);
        Log.println(Log.ERROR,"API REQUEST ", REQUEST_TYPE);*/

        Call<YapReviews> call = yapApiService.addReview(APP_ID, REQUEST_TYPE, REVIEW, USER_NAME, BUSINESS_ID, RATING, DATE);

        call.enqueue(new Callback<YapReviews>() {
            @Override
            public void onResponse(Call<YapReviews> call, Response<YapReviews> response) {

                List<YapReviews.YReview> reviewsIN = response.body().getReviews();

                reviewRecyclerView.setAdapter(new ReviewsRecyclerAdapter(reviewsIN));
                reviewRecyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<YapReviews> call, Throwable throwable) {
                Log.e("RF Data Error on POST", throwable.toString());
            }
        });
    }
}