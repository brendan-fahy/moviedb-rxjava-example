package com.breadbin.moviedb_rxjava_example.actors;

import com.breadbin.moviedb_rxjava_example.model.ActorResults;
import com.breadbin.moviedb_rxjava_example.model.api.Configuration;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

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
    }

    public Observable<Configuration> getConfiguration() {

        Observable<Configuration> memoryObs = Observable
                .create(new Observable.OnSubscribe<Configuration>() {
                    @Override
                    public void call(Subscriber<? super Configuration> subscriber) {
                        subscriber.onNext(config);
                        subscriber.onCompleted();
                    }
                });

        Observable<Configuration> networkObs = restClient.getConfig()
                .doOnNext(new Action1<Configuration>() {
                    @Override
                    public void call(Configuration configuration) {
                        config = configuration;
                    }
                });

        return Observable.concat(memoryObs, networkObs)
                .first(new Func1<Configuration, Boolean>() {
                    @Override
                    public Boolean call(Configuration configuration) {
                        return configuration != null;
                    }
                });
    }

    public Observable<ActorResults> searchActors(String query) {
        return restClient.getActors(query);
    }
}
