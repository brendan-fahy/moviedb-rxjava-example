package com.breadbin.moviedb_rxjava_example.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Actor {

  @SerializedName("adult")
  @Expose
  private boolean adult;
  @SerializedName("id")
  @Expose
  private int id;
  @SerializedName("known_for")
  @Expose
  private List<Movie> knownFor = new ArrayList<Movie>();
  @SerializedName("name")
  @Expose
  private String name;
  @SerializedName("popularity")
  @Expose
  private double popularity;
  @SerializedName("profile_path")
  @Expose
  private String profilePath;

  /**
   *
   * @return
   * The adult
   */
  public boolean isAdult() {
    return adult;
  }

  /**
   *
   * @param adult
   * The adult
   */
  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  /**
   *
   * @return
   * The id
   */
  public int getId() {
    return id;
  }

  /**
   *
   * @param id
   * The id
   */
  public void setId(int id) {
    this.id = id;
  }

  /**
   *
   * @return
   * The knownFor
   */
  public List<Movie> getMovies() {
    return knownFor;
  }

  /**
   *
   * @param knownFor
   * The known_for
   */
  public void setMovie(List<Movie> knownFor) {
    this.knownFor = knownFor;
  }

  /**
   *
   * @return
   * The name
   */
  public String getName() {
    return name;
  }

  /**
   *
   * @param name
   * The name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   *
   * @return
   * The popularity
   */
  public double getPopularity() {
    return popularity;
  }

  /**
   *
   * @param popularity
   * The popularity
   */
  public void setPopularity(double popularity) {
    this.popularity = popularity;
  }

  /**
   *
   * @return
   * The profilePath
   */
  public String getProfilePath() {
    return profilePath;
  }

  /**
   *
   * @param profilePath
   * The profile_path
   */
  public void setProfilePath(String profilePath) {
    this.profilePath = profilePath;
  }

}