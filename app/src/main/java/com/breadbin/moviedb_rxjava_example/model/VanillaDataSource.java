package com.breadbin.moviedb_rxjava_example.model;

import com.breadbin.moviedb_rxjava_example.model.api.Configuration;
import com.breadbin.moviedb_rxjava_example.model.api.Genres;
import com.breadbin.moviedb_rxjava_example.model.api.VanillaRestClient;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by bfahy on 09/01/16.
 */
public class VanillaDataSource {

    private VanillaRestClient restClient;
    private Configuration config;
    private Map<Integer, String> genres;

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
        getGenresFromNetwork();
    }

    private void getGenresFromNetwork() {
        restClient.getGenres(new Callback<Genres>() {
            @Override
            public void onResponse(Response<Genres> response, Retrofit retrofit) {
                Genres responseGenres = response.body();
                genres = new HashMap<>(responseGenres.getGenres().size());

                for (Genre genre: responseGenres.getGenres()) {
                    genres.put(genre.getId(), genre.getName());
                }

                if (config != null) {
                    initialised = true;
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    private void getConfigFromNetwork() {
        restClient.getConfig(new Callback<Configuration>() {
            @Override
            public void onResponse(Response<Configuration> response, Retrofit retrofit) {
                config = response.body();

                if (genres != null) {
                    initialised = true;
                }
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }

    public Map<Integer, String> getGenres() {
        return genres;
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
