package com.breadbin.moviedb_rxjava_example.actors;

import android.util.Log;

import com.breadbin.moviedb_rxjava_example.model.ActorResults;
import com.breadbin.moviedb_rxjava_example.model.api.Configuration;

import retrofit.Callback;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by bfahy on 09/01/16.
 */
public class VanillaDataSource {

    private VanillaRestClient restClient;
    private Configuration config;

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
        restClient
                .getConfig()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Configuration>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("VanillaDataSource", "onError getting Configuration from network, " + e.getMessage());
                    }

                    @Override
                    public void onNext(Configuration configuration) {
                        config = configuration;
                    }
                });
    }

    public Configuration getConfiguration() {
        return config;
    }

    public boolean isInitialised() {
        return config != null;
    }

    public void searchActors(String query, Callback<ActorResults> callback) {
        restClient.getActors(query, callback);
    }
}
