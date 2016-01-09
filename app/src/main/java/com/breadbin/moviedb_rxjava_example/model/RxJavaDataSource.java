package com.breadbin.moviedb_rxjava_example.model;

import android.support.annotation.VisibleForTesting;

import com.breadbin.moviedb_rxjava_example.model.api.Configuration;
import com.breadbin.moviedb_rxjava_example.model.api.RxJavaRestClient;

import org.joda.time.DateTime;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxJavaDataSource {

    private RxJavaRestClient restClient;
    private Configuration config;
    private Map<Integer, String> genres;

    private static RxJavaDataSource instance;

    private RxJavaDataSource() {
        this.restClient = RxJavaRestClient.getInstance();
    }

    @VisibleForTesting
    RxJavaDataSource(RxJavaRestClient restClient) {
        this.restClient = restClient;
    }

    public static RxJavaDataSource getInstance() {
        if (instance == null) {
            instance = new RxJavaDataSource();
        }
        return instance;
    }

    public Observable<MovieResults> getMovieList(DateTime queryStartdate, DateTime queryEndDate) {
        return restClient.requestMovieList(queryStartdate, queryEndDate);
    }

    /**
     * Uses the "concat" and the "first" operators to demonstrate loading data from multiple sources
     * as per Dan Lew's excellent blog post (http://blog.danlew.net/2015/06/22/loading-data-from-multiple-sources-with-rxjava/)
     *
     * An Observable is created which simply emits the value of the instance variable,
     * "Configuration config".
     * A second Observable is created which gets a Configuration from the API and emits this.
     *
     * The "concat" operator is used to combine the event streams of both of these Observables.
     *
     * The "first" operator is used with a predicate to determine whether or not an event is valid.
     * In this simple case, it checks if the value of the Configuration object is null. If so, then
     * the event is not valid, and is ignored.
     * Therefore, the first time this method is called, the instance variable will be null, and the
     * Observable will wait for the network response to return, and emit that.
     * On all subsequent calls (in the lifetime of the RxJavaDataSource object), the instance variable will
     * not be null, and so this will be emitted.
     *
     * There is no reason the predicate has to be a null check. Checking something within the object,
     * such as a timestamp, would be a better idea.
     * Similarly, the "concat" operator can be used on more than 2 Observables (up to 9), so we could
     * have up to 9 sources. Adding a local cache/storage Observable would make a lot of sense,
     * as per Dan Lew's original article.
     *
     * @return an Observable of a Configuration, from memory if one exists, or the server if it doesn't.
     */
    public Observable<Configuration> getConfiguration() {
        Observable<Configuration> memoryConfig = Observable.create(new Observable.OnSubscribe<Configuration>() {
            @Override
            public void call(Subscriber<? super Configuration> subscriber) {
                subscriber.onNext(config);
                subscriber.onCompleted();
            }
        });
        Observable<Configuration> networkConfig = restClient.requestConfig()
                .doOnNext(new Action1<Configuration>() {
                    @Override
                    public void call(Configuration configuration) {
                        config = configuration;
                    }
                });

        return Observable
                .concat(memoryConfig, networkConfig)
                .first(new Func1<Configuration, Boolean>() {
                    @Override
                    public Boolean call(Configuration configuration) {
                        return configuration != null;
                    }
                });
    }

    /**
     * Returns an Observable of a Map of genre IDs (Integers) to genre names (Strings), from memory
     * if it exists, or the server if it doesn't.
     *
     * Uses the "concat" and the "first" operators to demonstrate loading data from multiple sources
     * as per Dan Lew's excellent blog post (http://blog.danlew.net/2015/06/22/loading-data-from-multiple-sources-with-rxjava/)
     *
     * See "getConfiguration" for a fuller explanation.
     *
     * @return an Observable of a Map of genre IDs (Integers) to genre names (Strings), from memory
     * if it exists, or the server if it doesn't.
     */
    public Observable<Map<Integer, String>> getGenres() {
        Observable<Map<Integer, String>> memoryGenres = Observable
                .create(new Observable.OnSubscribe<Map<Integer, String>>() {
                    @Override
                    public void call(Subscriber<? super Map<Integer, String>> subscriber) {
                        subscriber.onNext(genres);
                        subscriber.onCompleted();
                    }
                });
        Observable<Map<Integer, String>> networkGenres = restClient.requestGenres()
                .doOnNext(new Action1<Map<Integer, String>>() {
                    @Override
                    public void call(Map<Integer, String> integerStringMap) {
                        genres = integerStringMap;
                    }
                });

        return Observable
                .concat(memoryGenres, networkGenres)
                .first(new Func1<Map<Integer, String>, Boolean>() {
                    @Override
                    public Boolean call(Map<Integer, String> integerStringMap) {
                        return integerStringMap != null;
                    }
                });
    }
}
