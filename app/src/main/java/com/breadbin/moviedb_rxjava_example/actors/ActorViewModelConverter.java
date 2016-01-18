package com.breadbin.moviedb_rxjava_example.actors;

import android.net.Uri;

import com.breadbin.moviedb_rxjava_example.model.Actor;
import com.breadbin.moviedb_rxjava_example.model.api.Configuration;
import com.breadbin.moviedb_rxjava_example.movielist.RxJavaRestClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ActorViewModelConverter {

    private static final String TITLE_SEPARATOR = ", ";

    private Configuration config;

    private static final int MAX_KNOWN_FORS = 3;

    public ActorViewModelConverter(Observable<Configuration> configurationObservable) {
        configurationObservable
                .subscribeOn(Schedulers.io())
                .subscribe(new Action1<Configuration>() {
                    @Override
                    public void call(Configuration configuration) {
                        config = configuration;
                    }
                });
    }

    public List<ActorViewModel> convertToViewModels(List<Actor> actors) {
        List<ActorViewModel> viewModels = new ArrayList<>(actors.size());
        for (int i = 0; i < actors.size(); i++) {
            viewModels.add(convertToViewModel(actors.get(i)));
        }
        return viewModels;
    }

    public ActorViewModel convertToViewModel(Actor actor) {
        ActorViewModel viewModel = new ActorViewModel();
        viewModel.setName(actor.getName());
        if (actor.getProfilePath() != null && config != null) {
            viewModel.setImageUri(getImagePath(actor.getProfilePath()));
        }
        viewModel.setRating(String.valueOf(actor.getPopularity()));
        viewModel.setKnownFor(getKnownForMovies(actor));
        return viewModel;
    }

    private String getKnownForMovies(Actor actor) {
        StringBuilder knownFor = new StringBuilder();

        int knownForsCount = Math.min(MAX_KNOWN_FORS, actor.getMovies().size());

        for (int i = 0; i < knownForsCount; i++) {
            if (actor.getMovies().get(i).getTitle() == null) {
                continue;
            }
            knownFor.append(actor.getMovies().get(i).getTitle());
            if (i < knownForsCount - 1) {
                knownFor.append(TITLE_SEPARATOR);
            }
        }
        return knownFor.toString();
    }

    private Uri getImagePath(String posterPath) {
        return Uri.parse(config.getImages().getBaseUrl())
                .buildUpon()
                .appendPath(config.getImages().getPosterSizes().get(0))
                .appendPath(posterPath.replaceFirst("/", ""))
                .appendQueryParameter(RxJavaRestClient.API_KEY_PARAM_NAME, RxJavaRestClient.MOVIEDB_API_KEY)
                .build();
    }
}
