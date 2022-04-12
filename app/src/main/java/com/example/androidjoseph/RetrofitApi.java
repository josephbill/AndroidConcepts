package com.example.androidjoseph;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitApi {

    // as we are making a post request to post a data
    // so we are annotating it with post
    // and along with that we are passing a parameter as users
    @POST("users")

    //on below line we are creating a method to post our data.
    Call<JobModal> createPost(@Body JobModal jobModal);


}
