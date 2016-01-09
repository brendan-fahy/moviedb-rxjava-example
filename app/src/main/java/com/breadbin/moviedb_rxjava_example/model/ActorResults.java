package com.breadbin.moviedb_rxjava_example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ActorResults {

  @SerializedName("page")
  @Expose
  private int page;
  @SerializedName("results")
  @Expose
  private List<Actor> results = new ArrayList<Actor>();
  @SerializedName("total_pages")
  @Expose
  private int totalPages;
  @SerializedName("total_results")
  @Expose
  private int totalActors;

  /**
   *
   * @return
   * The page
   */
  public int getPage() {
    return page;
  }

  /**
   *
   * @param page
   * The page
   */
  public void setPage(int page) {
    this.page = page;
  }

  /**
   *
   * @return
   * The results
   */
  public List<Actor> getActors() {
    return results;
  }

  /**
   *
   * @param results
   * The results
   */
  public void setActors(List<Actor> results) {
    this.results = results;
  }

  /**
   *
   * @return
   * The totalPages
   */
  public int getTotalPages() {
    return totalPages;
  }

  /**
   *
   * @param totalPages
   * The total_pages
   */
  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  /**
   *
   * @return
   * The totalActors
   */
  public int getTotalActors() {
    return totalActors;
  }

  /**
   *
   * @param totalActors
   * The total_results
   */
  public void setTotalActors(int totalActors) {
    this.totalActors = totalActors;
  }

}