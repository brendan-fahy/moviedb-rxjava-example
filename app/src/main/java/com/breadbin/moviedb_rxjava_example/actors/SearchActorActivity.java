package com.breadbin.moviedb_rxjava_example.actors;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.breadbin.moviedb_rxjava_example.R;
import com.breadbin.moviedb_rxjava_example.model.ActorResults;
import com.jakewharton.rxbinding.support.v7.widget.RxSearchView;
import com.jakewharton.rxbinding.support.v7.widget.SearchViewQueryTextEvent;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class SearchActorActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Subscription searchViewSubscription;

    private ActorAdapter adapter;

    private ActorViewModelConverter converter;

    private VanillaDataSource dataSource = VanillaDataSource.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_actor);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupRecyclerView();

        converter = new ActorViewModelConverter(dataSource.getConfiguration());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) searchItem.getActionView();

        searchViewSubscription = RxSearchView.queryTextChangeEvents(searchView)
                .filter(new Func1<SearchViewQueryTextEvent, Boolean>() {
                    @Override
                    public Boolean call(SearchViewQueryTextEvent searchViewQueryTextEvent) {
                        return searchViewQueryTextEvent.queryText().length() >= 3;
                    }
                })
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<SearchViewQueryTextEvent>() {
                    @Override
                    public void call(SearchViewQueryTextEvent searchViewQueryTextEvent) {
                        search(searchViewQueryTextEvent.queryText().toString());
                    }
                });

        return super.onCreateOptionsMenu(menu);
    }

    private void search(String query) {

        dataSource.searchActors(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ActorResults>() {
                    @Override
                    public void call(ActorResults actorResults) {
                        adapter.setActors(converter.convertToViewModels(actorResults.getActors()));
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(0);
                    }
                });
    }

    private void setupRecyclerView() {
        adapter = new ActorAdapter(new ArrayList<ActorViewModel>(0));
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStop() {
        super.onStop();
        searchViewSubscription.unsubscribe();
    }
}
