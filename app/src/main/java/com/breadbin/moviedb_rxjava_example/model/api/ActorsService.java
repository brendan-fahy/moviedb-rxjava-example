package com.breadbin.moviedb_rxjava_example.model.api;

import com.breadbin.moviedb_rxjava_example.model.ActorResults;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by bfahy on 09/01/16.
 */
public interface ActorsService {

    @GET("/3/search/person")
    Call<ActorResults> getActorResults(
            @Query("api_key") String apiKey,
            @Query("query") String query);
}
