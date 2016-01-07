package com.breadbin.moviedb_rxjava_example.model.api;

import android.support.annotation.VisibleForTesting;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Images {

  @SerializedName("base_url")
  private String baseUrl;

  @SerializedName("poster_sizes")
  private List<String> posterSizes;

  @VisibleForTesting
  public Images(String baseUrl, List<String> posterSizes) {
    this.baseUrl = baseUrl;
    this.posterSizes = posterSizes;
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public List<String> getPosterSizes() {
    return posterSizes;
  }
}
