package com.breadbin.moviedb_rxjava_example.actors;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.breadbin.moviedb_rxjava_example.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ActorCard extends CardView {

    @Bind(R.id.ivThumbnail)
    ImageView ivThumbnail;

    @Bind(R.id.tvName)
    TextView tvName;

    @Bind(R.id.tvKnownFor)
    TextView tvKnownFor;

    @Bind(R.id.tvPopularity)
    TextView tvPopularity;

    public ActorCard(Context context) {
        super(context);
    }

    public ActorCard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ActorCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void bindTo(final ActorViewModel actor) {
        tvName.setText(actor.getName());
        tvKnownFor.setText(actor.getKnownFor());
        tvPopularity.setText(actor.getRating());

        Picasso.with(getContext()).load(actor.getImageUri()).into(ivThumbnail);
    }

}
