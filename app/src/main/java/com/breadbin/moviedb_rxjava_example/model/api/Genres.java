package com.breadbin.moviedb_rxjava_example.model.api;

import com.breadbin.moviedb_rxjava_example.model.Genre;

import java.util.List;

public class Genres {

  private List<Genre> genres;

  public List<Genre> getGenres() {
    return genres;
  }

  public void setGenres(List<Genre> genres) {
    this.genres = genres;
  }
}
