package mdnaseemashraf.yapapp;

import mdnaseemashraf.yapapp.models.YapBusinesses;
import mdnaseemashraf.yapapp.models.YapReviews;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Naseem Ashraf on 21-09-2017.
 */

public interface YapApiService {

    @GET("api")
    Call<YapBusinesses> getSearchedBusiness(@Query("app_id") int app_id,
                                            @Query("request_type") String request_type,
                                            @Query("form") String form,
                                            @Query("keywords") String keywords,
                                            @Query("city") String city,
                                            @Query("state") String state,
                                            @Query("type") String type);

    @GET("api")
    Call<YapReviews> getBusinessReviews(@Query("app_id") int app_id,
                                        @Query("request_type") String request_type,
                                        @Query("business_id") String business_id);

    @POST("api")
    @FormUrlEncoded
    Call<YapReviews> addReview(@Field("app_id") int app_id,
                               @Field("request_type") String request_type,
                               @Field("review") String review_text,
                               @Field("user_name") String user_name,
                               @Field("business_id") String business_id,
                               @Field("rating") int rating,
                               @Field("date") String date);

}
