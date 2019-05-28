package com.jss.sampleapicall;


import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
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


import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    private TextView movieNameTv;
    private TextView movieRatingTv;
    private ImageView moviePoster;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        movieNameTv = findViewById(R.id.tv_movie_name);
        movieRatingTv = findViewById(R.id.tv_movie_rating);
        moviePoster = findViewById(R.id.movie_poster);
        loadData();
    }



    private void loadData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.omdbapi.com/?i=tt3896198&apikey=3429523f",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                                JSONObject object = new JSONObject(response);
                                String name = object.getString("Title");
                                String rating = object.getString("imdbRating");
                                String imgurl = object.getString("Poster");
                               /* Movie movie = new Movie(
                                        name,
                                        rating,
                                        imgurl

                                );*/
                                movieNameTv.setText(name);
                                movieRatingTv.setText(rating);
                                Glide.with(context)
                                        .load(imgurl)
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(moviePoster);


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

}
