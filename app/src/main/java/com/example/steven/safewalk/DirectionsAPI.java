package com.example.steven.safewalk;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Steven on 12/19/2016.
 */

public interface DirectionsAPI {

    @GET("maps/api/distancematrix/json?key=AIzaSyD18TI6iLEXzmhbzyhFY-awBFXWA-kqGAM&traffic_model=best_guess")
    Call<ResponseBody> getDirections (@Query("origins") String origin, @Query("destinations") String destination, @Query("mode") String mode, @Query("departure_time") String time);
}
