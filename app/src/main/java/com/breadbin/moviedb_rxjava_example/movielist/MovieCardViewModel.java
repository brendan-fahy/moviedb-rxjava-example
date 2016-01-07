package com.breadbin.moviedb_rxjava_example.movielist;

import android.net.Uri;

public class MovieCardViewModel {

  private String title;

  private String rating;

  private String releaseDate;

  private String genres;

  private String overview;

  private Uri posterUri;

  private MovieCardViewModel(String title,
                             String rating,
                             String releaseDate,
                             String genres,
                             String overview,
                             Uri posterUri) {
    this.title = title;
    this.rating = rating;
    this.releaseDate = releaseDate;
    this.genres = genres;
    this.overview = overview;
    this.posterUri = posterUri;
  }

  public String getTitle() {
    return title;
  }

  public String getRating() {
    return rating;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getGenres() {
    return genres;
  }

  public String getOverview() {
    return overview;
  }

  public Uri getPosterUri() {
    return posterUri;
  }

  public static class Builder {
    private String title;
    private String rating;
    private String releaseDate;
    private String genres;
    private String overview;
    private Uri posterUri;

    Builder withTitle(String title) {
      this.title = title;
      return this;
    }

    Builder withRating(String rating) {
      this.rating = rating;
      return this;
    }

    Builder withReleaseDate(String releaseDate) {
      this.releaseDate = releaseDate;
      return this;
    }

    Builder withGenres(String genres) {
      this.genres = genres;
      return this;
    }

    Builder withOverview(String overview) {
      this.overview = overview;
      return this;
    }

    Builder withPosterUri(Uri posterUri) {
      this.posterUri = posterUri;
      return this;
    }

    MovieCardViewModel build() {
      return new MovieCardViewModel(title, rating, releaseDate, genres, overview, posterUri);
    }
  }
}
