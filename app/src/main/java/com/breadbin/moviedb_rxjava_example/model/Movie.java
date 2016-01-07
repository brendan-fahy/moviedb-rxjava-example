package com.breadbin.moviedb_rxjava_example.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {

  @SerializedName("adult")
  private boolean adult;
  @SerializedName("backdrop_path")
  private String backdropPath;
  @SerializedName("genre_ids")
  private List<Integer> genreIds = new ArrayList<>();
  @SerializedName("id")
  private int id;
  @SerializedName("original_language")
  private String originalLanguage;
  @SerializedName("original_title")
  private String originalTitle;
  @SerializedName("overview")
  private String overview;
  @SerializedName("release_date")
  private String releaseDate;
  @SerializedName("poster_path")
  private String posterPath;
  @SerializedName("popularity")
  private float popularity;
  @SerializedName("title")
  private String title;
  @SerializedName("video")
  private boolean video;
  @SerializedName("vote_average")
  private float voteAverage;
  @SerializedName("vote_count")
  private int voteCount;

  public boolean isAdult() {
    return adult;
  }

  public void setAdult(boolean adult) {
    this.adult = adult;
  }

  public String getBackdropPath() {
    return backdropPath;
  }

  public void setBackdropPath(String backdropPath) {
    this.backdropPath = backdropPath;
  }

  public List<Integer> getGenreIds() {
    return genreIds;
  }

  public void setGenreIds(List<Integer> genreIds) {
    this.genreIds = genreIds;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getOriginalLanguage() {
    return originalLanguage;
  }

  public void setOriginalLanguage(String originalLanguage) {
    this.originalLanguage = originalLanguage;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public void setOriginalTitle(String originalTitle) {
    this.originalTitle = originalTitle;
  }

  public String getOverview() {
    return overview;
  }

  public void setOverview(String overview) {
    this.overview = overview;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(String releaseDate) {
    this.releaseDate = releaseDate;
  }

  public String getPosterPath() {
    return posterPath;
  }

  public void setPosterPath(String posterPath) {
    this.posterPath = posterPath;
  }

  public float getPopularity() {
    return popularity;
  }

  public void setPopularity(float popularity) {
    this.popularity = popularity;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isVideo() {
    return video;
  }

  public void setVideo(boolean video) {
    this.video = video;
  }

  public float getVoteAverage() {
    return voteAverage;
  }

  public void setVoteAverage(float voteAverage) {
    this.voteAverage = voteAverage;
  }

  public int getVoteCount() {
    return voteCount;
  }

  public void setVoteCount(int voteCount) {
    this.voteCount = voteCount;
  }

}
