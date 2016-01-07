package com.breadbin.moviedb_rxjava_example.movielist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.breadbin.moviedb_rxjava_example.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int MOVIE_TYPE = 1;
  private static final int LOGO_TYPE = 2;

  private List<MovieCardViewModel> movies;

  public MovieAdapter(List<MovieCardViewModel> movies) {
    this.movies = movies;
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case MOVIE_TYPE:
        return new MovieViewHolder(LayoutInflater.from(parent.getContext())
            .inflate(R.layout.card_movie, parent, false));
      default:
        return new LogoViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.logo_moviedb, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    switch(getItemViewType(position)) {
      case MOVIE_TYPE:
        MovieViewHolder movieViewHolder = ((MovieViewHolder) holder);
        movieViewHolder.movieCard.bindTo(movies.get(position));
        break;
    }
  }

  @Override
  public int getItemViewType(int position) {
    if (position < movies.size()) {
      return MOVIE_TYPE;
    } else {
      return LOGO_TYPE;
    }
  }

  @Override
  public int getItemCount() {
    return (movies == null) ? 0 : movies.size() + 1;
 }

  public final class MovieViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.movieCard)
    MovieCard movieCard;

    public MovieViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public final class LogoViewHolder extends RecyclerView.ViewHolder {

    public LogoViewHolder(View itemView) {
      super(itemView);
    }
  }
}
