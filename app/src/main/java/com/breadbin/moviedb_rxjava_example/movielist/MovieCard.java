package com.breadbin.moviedb_rxjava_example.movielist;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.breadbin.moviedb_rxjava_example.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MovieCard extends CardView {

  @Bind(R.id.ivThumbnail)
  ImageView ivThumbnail;

  @Bind(R.id.tvName)
  TextView tvName;

  @Bind(R.id.tvCategory)
  TextView tvCategory;

  @Bind(R.id.tvPopularity)
  TextView tvPopularity;

  @Bind(R.id.tvYear)
  TextView tvYear;

  public MovieCard(Context context) {
    super(context);
  }

  public MovieCard(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public MovieCard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    ButterKnife.bind(this);
  }

  public void bindTo(final MovieCardViewModel movie) {
    tvName.setText(movie.getTitle());
    tvCategory.setText(movie.getGenres());
    tvPopularity.setText(movie.getRating());
    tvYear.setText(movie.getReleaseDate());

    Picasso.with(getContext()).load(movie.getPosterUri()).into(ivThumbnail);

    setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new AlertDialog.Builder(getContext(), R.style.AppTheme_Dialog)
            .setMessage(movie.getOverview())
            .setPositiveButton(android.R.string.ok, null)
            .show();
      }
    });
  }

}
