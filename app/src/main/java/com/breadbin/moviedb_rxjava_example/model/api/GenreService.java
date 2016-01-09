package com.breadbin.moviedb_rxjava_example.model.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface GenreService {

    @GET("/3/genre/movie/list")
    Call<Genres> getGenres(@Query("api_key") String apiKey);

    @GET("/3/genre/movie/list")
    Observable<Genres> getGenresRx(@Query("api_key") String apiKey);

}
