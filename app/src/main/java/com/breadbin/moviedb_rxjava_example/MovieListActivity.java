package com.breadbin.moviedb_rxjava_example;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.breadbin.moviedb_rxjava_example.model.DataSource;
import com.breadbin.moviedb_rxjava_example.model.MovieResults;
import com.breadbin.moviedb_rxjava_example.model.api.Configuration;
import com.breadbin.moviedb_rxjava_example.movielist.MovieAdapter;
import com.breadbin.moviedb_rxjava_example.movielist.MovieCardViewModel;
import com.breadbin.moviedb_rxjava_example.movielist.MovieViewModelConverter;

import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

/**
 * Created by bfahy on 07/01/16.
 */
public class MovieListActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private ProgressDialog progressDialog;

    private DataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        setTitle(getString(R.string.list_page_title));

        dataSource = DataSource.getInstance();
    }


    @Override
    protected void onStart() {
        super.onStart();

        requestMovies();
    }

    @OnClick(R.id.fab)
    void onClickFab() {
        // TODO Search
    }

    public void showMovies(List<MovieCardViewModel> movies) {
        initRecyclerView(new MovieAdapter(movies));
    }

    public void showLoadingDialog() {
        if (progressDialog == null) {
            initialiseProgressDialog();
        }
        progressDialog.show();
    }

    private void initialiseProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.common_loading));
    }


    public void dismissLoadingDialog() {
        progressDialog.dismiss();
    }

    private void initRecyclerView(RecyclerView.Adapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(false);
    }

    private void requestMovies() {
        Observable<Configuration> configuration = dataSource.getConfiguration();
        Observable<Map<Integer, String>> genres = dataSource.getGenres();
        Observable<MovieResults> movieResults = dataSource.getMovieList(getQueryStartDate(),
                getQueryEndDate());

        Observable
                .zip(genres, configuration, movieResults,
                        new Func3<Map<Integer, String>, Configuration, MovieResults, List<MovieCardViewModel>>() {
                            @Override
                            public List<MovieCardViewModel> call(
                                    Map<Integer, String> genres, Configuration config, MovieResults movieResults) {
                                return new MovieViewModelConverter(config, genres)
                                        .convertToViewModels(movieResults.getResults());
                            }
                        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        showLoadingDialog();
                    }
                })
                .subscribe(new Action1<List<MovieCardViewModel>>() {
                    @Override
                    public void call(List<MovieCardViewModel> movieCardViewModels) {
                        dismissLoadingDialog();
                        showMovies(movieCardViewModels);
                    }
                });
    }

    private DateTime getQueryStartDate() {
        return DateTime.now().minusDays(7);
    }

    private DateTime getQueryEndDate() {
        return DateTime.now();
    }
}

