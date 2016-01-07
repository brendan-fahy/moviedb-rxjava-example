package com.breadbin.moviedb_rxjava_example.model;

import android.support.annotation.VisibleForTesting;

import com.breadbin.moviedb_rxjava_example.model.api.Configuration;
import com.breadbin.moviedb_rxjava_example.model.api.RestClient;

import org.joda.time.DateTime;

import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class DataSource {

  private RestClient restClient;
  private Configuration config;
  private Map<Integer, String> genres;

  private static DataSource instance;

  private DataSource() {
    this.restClient = RestClient.getInstance();
  }

  @VisibleForTesting
  DataSource(RestClient restClient) {
    this.restClient = restClient;
  }

  public static DataSource getInstance() {
    if (instance == null) {
      instance = new DataSource();
    }
    return instance;
  }

  public Observable<MovieResults> getMovieList(DateTime queryStartdate, DateTime queryEndDate) {
    return restClient.requestMovieList(queryStartdate, queryEndDate);
  }

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
