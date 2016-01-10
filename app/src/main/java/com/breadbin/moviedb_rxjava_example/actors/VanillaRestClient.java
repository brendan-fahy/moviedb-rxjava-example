package com.breadbin.moviedb_rxjava_example.actors;

import com.breadbin.moviedb_rxjava_example.model.ActorResults;
import com.breadbin.moviedb_rxjava_example.model.api.ActorsService;
import com.breadbin.moviedb_rxjava_example.model.api.Configuration;
import com.breadbin.moviedb_rxjava_example.model.api.ConfigurationService;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by bfahy on 09/01/16.
 */
public class VanillaRestClient {

    private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/";
    public static final String MOVIEDB_API_KEY = "7a2fdb0a7c4e001a1941d1dde66e1f87";

    public static VanillaRestClient instance;

    private ConfigurationService configService;
    private ActorsService actorsService;

    public static VanillaRestClient getInstance() {
        if (instance == null) {
            instance = new VanillaRestClient();
        }
        return instance;
    }

    private VanillaRestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIEDB_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        configService = retrofit.create(ConfigurationService.class);
        actorsService = retrofit.create(ActorsService.class);
    }

    public void getConfig(Callback<Configuration> callback) {
        configService.getConfiguration(MOVIEDB_API_KEY).enqueue(callback);
    }

    public void getActors(String query, Callback<ActorResults> callback) {
        actorsService.getActorResults(MOVIEDB_API_KEY, query).enqueue(callback);
    }
}
