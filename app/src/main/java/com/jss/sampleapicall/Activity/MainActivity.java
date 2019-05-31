package com.jss.sampleapicall.Activity;


import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.ybq.android.spinkit.SpinKitView;
import com.jss.sampleapicall.Adapters.MoviesAdapter;
import com.jss.sampleapicall.Models.Movie;
import com.jss.sampleapicall.Models.MyDividerItemDecoration;
import com.jss.sampleapicall.R;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Context context;
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    LinearLayoutManager mLayoutManager;
    private List<Movie> movieList;
    private MoviesAdapter moviesAdapter;
    private SearchView searchView;
    private Boolean isScrolling = false;
    int currentItems, totalItems, scrollOutItems;
    ProgressBar progressBar;
    int pageCount = 1;
    String queryTitle;
    int noOfPages;
    TextView defaultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        recyclerView = findViewById(R.id.rv_movies);
        movieList = new ArrayList<>();
        moviesAdapter = new MoviesAdapter(this, movieList);
        progressBar = findViewById(R.id.progressBar);
        defaultTv = findViewById(R.id.defaut_tv);

        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 36));
        recyclerView.setAdapter(moviesAdapter);
       // loadMovieData("inception");

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = mLayoutManager.getChildCount();
                totalItems = mLayoutManager.getItemCount();
                scrollOutItems = mLayoutManager.findFirstVisibleItemPosition();

                if(isScrolling && (currentItems + scrollOutItems == totalItems) && pageCount < noOfPages)
                {
                    isScrolling = false;
                    pageCount++;
                    loadMovieData(queryTitle, pageCount);
                }
            }
        });
    }



    private void loadMovieData(String queryTitle, int pageCount) {
        StringBuilder urlSb = new StringBuilder("http://www.omdbapi.com/?s=");
        urlSb.append(queryTitle);
        urlSb.append("&apikey=3429523f");
        urlSb.append("&page=");
        urlSb.append(pageCount);
        progressBar.setVisibility(View.VISIBLE);
        defaultTv.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlSb.toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressBar.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("Search");
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String name = object.getString("Title");
                                String year = object.getString("Year");
                                String imgurl = object.getString("Poster");
                                Movie movie = new Movie(
                                        name,
                                        year,
                                        imgurl

                                );

                                movieList.add(movie);
                            }

                            int totalResults = Integer.parseInt(jsonObject.getString("totalResults"));
                            if(totalResults % 10 == 0){
                                noOfPages = totalResults/10;
                            } else
                                noOfPages = totalResults/10 + 1;


                            moviesAdapter.notifyDataSetChanged();

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){

        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_title_search, menu);


        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                queryTitle = query;
                pageCount = 1;
                movieList.clear();
                loadMovieData(query, pageCount);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                queryTitle = query;
                pageCount = 1;
                movieList.clear();
                loadMovieData(query, pageCount);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        if(id == android.R.id.home){
            onBackPressed();
            return true;
        }



        return super.onOptionsItemSelected(item);
    }




}
