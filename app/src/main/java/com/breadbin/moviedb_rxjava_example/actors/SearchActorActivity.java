package com.breadbin.moviedb_rxjava_example.actors;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.breadbin.moviedb_rxjava_example.R;
import com.breadbin.moviedb_rxjava_example.model.ActorResults;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class SearchActorActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private SearchView searchView;

    private ActorAdapter adapter;

    private ActorViewModelConverter converter;

    private ProgressDialog progressDialog;

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

        searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void search(String query) {
        showProgressDialog(query);

        dataSource.searchActors(query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<ActorResults>() {
                    @Override
                    public void call(ActorResults actorResults) {
                        adapter.setActors(converter.convertToViewModels(actorResults.getActors()));
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(0);
                        hideProgressDialog();
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

    private void showProgressDialog(String query) {
        progressDialog = ProgressDialog.show(this, "Searching", "Searching for actors named " + query);
    }

    private void hideProgressDialog() {
        progressDialog.dismiss();
    }
}
