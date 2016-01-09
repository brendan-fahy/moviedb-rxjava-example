package com.breadbin.moviedb_rxjava_example.model.api;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ConfigurationService {

    @GET("/3/configuration")
    Call<Configuration> getConfiguration(@Query("api_key") String apiKey);

    @GET("/3/configuration")
    Observable<Configuration> getConfigurationRx(@Query("api_key") String apiKey);
}
