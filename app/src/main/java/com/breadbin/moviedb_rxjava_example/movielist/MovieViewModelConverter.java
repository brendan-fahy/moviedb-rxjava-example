package com.breadbin.moviedb_rxjava_example.movielist;

import android.net.Uri;

import com.breadbin.moviedb_rxjava_example.model.Movie;
import com.breadbin.moviedb_rxjava_example.model.api.Configuration;
import com.breadbin.moviedb_rxjava_example.model.api.RxJavaRestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MovieViewModelConverter {

  private static final String RATING_FORMAT = "%.1f/10";

  private Configuration config;

  private Map<Integer, String> genres;

  public MovieViewModelConverter(Configuration config, Map<Integer, String> genres) {
    this.config = config;
    this.genres = genres;
  }

  public List<MovieCardViewModel> convertToViewModels(List<Movie> movies) {
    List<MovieCardViewModel> movieViewModels = new ArrayList<>(movies.size());
    for (Movie movie : movies) {
      movieViewModels.add(convertToViewModel(movie));
    }
    return movieViewModels;
  }

  private MovieCardViewModel convertToViewModel(Movie movie) {
    MovieCardViewModel.Builder builder = new MovieCardViewModel.Builder()
        .withTitle(movie.getTitle())
        .withOverview(movie.getOverview())
        .withGenres(getFormattedGenres(movie.getGenreIds()))
        .withReleaseDate(getFormattedReleaseDate(movie.getReleaseDate()))
        .withRating(getFormattedRating(movie.getVoteAverage()));

    if (movie.getPosterPath() != null) {
      builder = builder.withPosterUri(getPosterUri(movie.getPosterPath()));
    }

    return builder.build();
  }

  private String getFormattedGenres(final List<Integer> genreIds) {
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < genreIds.size(); i++) {
      builder.append(genres.get(genreIds.get(i)));
      if (i < genreIds.size() - 1) {
        builder.append(", ");
      }
    }
    return builder.toString();
  }

  private Uri getPosterUri(String posterPath) {
    return Uri.parse(config.getImages().getBaseUrl())
        .buildUpon()
        .appendPath(config.getImages().getPosterSizes().get(0))
        .appendPath(posterPath.replaceFirst("/", ""))
        .appendQueryParameter(RxJavaRestClient.API_KEY_PARAM_NAME, RxJavaRestClient.MOVIEDB_API_KEY)
        .build();
  }

  private String getFormattedReleaseDate(String releaseDate) {
    return RxJavaRestClient.DATE_TIME_FORMATTER.parseDateTime(releaseDate).year().getAsString();
  }

  private String getFormattedRating(float voteAverage) {
    return String.format(RATING_FORMAT, voteAverage);
  }
}
