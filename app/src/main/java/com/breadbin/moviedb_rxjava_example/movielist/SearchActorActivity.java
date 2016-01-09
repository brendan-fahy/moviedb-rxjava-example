package com.breadbin.moviedb_rxjava_example.movielist;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.breadbin.moviedb_rxjava_example.R;
import com.breadbin.moviedb_rxjava_example.model.ActorResults;
import com.breadbin.moviedb_rxjava_example.model.VanillaDataSource;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class SearchActorActivity extends AppCompatActivity {

    private SearchView searchView;

    private VanillaDataSource dataSource = VanillaDataSource.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_actor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                Snackbar.make(searchView, "Search text change!", Snackbar.LENGTH_SHORT).show();
                return false;
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

        dataSource.searchActors(query, new Callback<ActorResults>() {
            @Override
            public void onResponse(Response<ActorResults> response, Retrofit retrofit) {
                Log.d("SearchActorActivity", response.body().getActors().size() + " actors found.");
                Toast.makeText(getApplicationContext(), response.body().getTotalActors() + " actors found.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });
    }
}
