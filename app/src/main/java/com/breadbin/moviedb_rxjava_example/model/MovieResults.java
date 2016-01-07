package com.breadbin.moviedb_rxjava_example.model;

import java.util.ArrayList;
import java.util.List;

public class MovieResults {

  private int page;

  private List<Movie> results = new ArrayList<>();

  private int totalPages;

  private int totalResults;

  public int getPage() {
    return page;
  }

  public void setPage(int page) {
    this.page = page;
  }

  public List<Movie> getResults() {
    return results;
  }

  public void setResults(List<Movie> results) {
    this.results = results;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getTotalResults() {
    return totalResults;
  }

  public void setTotalResults(int totalResults) {
    this.totalResults = totalResults;
  }

}
