package com.breadbin.moviedb_rxjava_example.model.api;

import com.breadbin.moviedb_rxjava_example.model.Genre;
import com.breadbin.moviedb_rxjava_example.model.MovieResults;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.HashMap;
import java.util.Map;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RestClient {

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd");

  private static final String MOVIEDB_BASE_URL = "http://api.themoviedb.org/";
  public static final String MOVIEDB_API_KEY = "7a2fdb0a7c4e001a1941d1dde66e1f87";
  public static final String API_KEY_PARAM_NAME = "api_key";

  private static RestClient instance;

  private ConfigurationService configService;
  private GenreService genreService;
  private MovieListService movieListService;

  public static RestClient getInstance() {
    if (instance == null) {
      instance = new RestClient();
    }
    return instance;
  }

  private RestClient() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(MOVIEDB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .build();

    configService = retrofit.create(ConfigurationService.class);
    genreService = retrofit.create(GenreService.class);
    movieListService = retrofit.create(MovieListService.class);
  }

  public Observable<Configuration> requestConfig() {
    return configService.getConfiguration(MOVIEDB_API_KEY);
  }

  public Observable<Map<Integer, String>> requestGenres() {
    return genreService.getGenres(MOVIEDB_API_KEY)
        .map(new Func1<Genres, Map<Integer, String>>() {
          @Override
          public Map<Integer, String> call(Genres genres) {
            Map<Integer, String> genresMap = new HashMap<>(genres.getGenres().size());
            for (Genre genre : genres.getGenres()) {
              genresMap.put(genre.getId(), genre.getName());
            }
            return genresMap;
          }
        });
  }

  public Observable<MovieResults> requestMovieList(DateTime queryStartdate, DateTime queryEndDate) {
    return movieListService.getMoviesReleasedBetweenDates(MOVIEDB_API_KEY,
        DATE_TIME_FORMATTER.print(queryStartdate),
        DATE_TIME_FORMATTER.print(queryEndDate));
  }

}
