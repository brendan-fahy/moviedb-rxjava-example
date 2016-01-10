package com.breadbin.moviedb_rxjava_example.actors;

import com.breadbin.moviedb_rxjava_example.model.ActorResults;
import com.breadbin.moviedb_rxjava_example.model.api.Configuration;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by bfahy on 09/01/16.
 */
public class VanillaDataSource {

    private VanillaRestClient restClient;
    private Configuration config;

    private boolean initialised = false;

    private static VanillaDataSource instance;

    public static VanillaDataSource getInstance() {
        if (instance == null) {
            instance = new VanillaDataSource();
        }
        return instance;
    }

    private VanillaDataSource() {
        restClient = VanillaRestClient.getInstance();

        getConfigFromNetwork();
    }

    private void getConfigFromNetwork() {
        restClient.getConfig(new Callback<Configuration>() {
            @Override
            public void onResponse(Response<Configuration> response, Retrofit retrofit) {
                config = response.body();
                initialised = true;
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public Configuration getConfiguration() {
        return config;
    }

    public boolean isInitialised() {
        return initialised;
    }

    public void searchActors(String query, Callback<ActorResults> callback) {
        restClient.getActors(query, callback);
    }
}
