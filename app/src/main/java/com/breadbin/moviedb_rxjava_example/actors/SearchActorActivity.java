package com.breadbin.moviedb_rxjava_example.actors;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void search(String query) {
        if (!dataSource.isInitialised()) {
            new AlertDialog.Builder(this)
                    .setMessage("DataSource not yet initialised! Please try again in a moment.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
        }

        showProgressDialog(query);

        if (converter == null) {
            converter = new ActorViewModelConverter(dataSource.getConfiguration());
        }

        dataSource.searchActors(query, new Callback<ActorResults>() {
            @Override
            public void onResponse(Response<ActorResults> response, Retrofit retrofit) {
                adapter.setActors(converter.convertToViewModels(response.body().getActors()));
                adapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(0);
                hideProgressDialog();
            }

            @Override
            public void onFailure(Throwable t) {

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
