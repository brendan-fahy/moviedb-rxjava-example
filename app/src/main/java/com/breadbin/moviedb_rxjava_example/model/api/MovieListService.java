package com.breadbin.moviedb_rxjava_example.model.api;

import com.breadbin.moviedb_rxjava_example.model.MovieResults;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface MovieListService {

  @GET("/3/discover/movie?sort_by=popularity.desc")
  Observable<MovieResults> getMoviesReleasedBetweenDates(
          @Query("api_key") String apiKey,
          @Query("primary_release_date.gte") String primaryReleaseDateGte,
          @Query("primary_release_date.lte") String primaryReleaseDateLte);
}
