package com.jss.sampleapicall;

public class Movie {
    String movie_name;
    String movie_rating;

    public Movie(String movie_name, String movie_rating, String movie_img_url) {
        this.movie_name = movie_name;
        this.movie_rating = movie_rating;
        this.movie_img_url = movie_img_url;
    }

    String movie_img_url;

    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_rating() {
        return movie_rating;
    }

    public void setMovie_rating(String movie_rating) {
        this.movie_rating = movie_rating;
    }

    public String getMovie_img_url() {
        return movie_img_url;
    }

    public void setMovie_img_url(String movie_img_url) {
        this.movie_img_url = movie_img_url;
    }



}
