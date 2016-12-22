package com.example.steven.safewalk;

import android.location.Location;
import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Steven on 12/21/2016.
 */

class DirectionsService extends AsyncTask<TripRequest, Void, TrafficResponse> {


    TrafficResponse doSomething(TripRequest tripRequest){
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://maps.googleapis.com/")
                    .build();
            DirectionsAPI service = retrofit.create(DirectionsAPI.class);
            Call<ResponseBody> stuff = service.getDirections(tripRequest.getOrigin().getLatitude() + "," + tripRequest.getOrigin().getLongitude(), tripRequest.getDestination(), tripRequest.getMode().toLowerCase(), String.valueOf(System.currentTimeMillis()));
            Response<ResponseBody> responseBody = stuff.execute();
            return new Gson().fromJson(responseBody.body().string(), TrafficResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected TrafficResponse doInBackground(TripRequest... params) {
        return doSomething(params[0]);
    }
}
