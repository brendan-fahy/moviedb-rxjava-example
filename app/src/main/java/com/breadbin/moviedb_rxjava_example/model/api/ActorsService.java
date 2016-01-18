package com.breadbin.moviedb_rxjava_example.model.api;

import com.breadbin.moviedb_rxjava_example.model.ActorResults;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by bfahy on 09/01/16.
 */
public interface ActorsService {

    @GET("/3/search/person")
    Observable<ActorResults> getActorResults(
            @Query("api_key") String apiKey,
            @Query("query") String query);
}
