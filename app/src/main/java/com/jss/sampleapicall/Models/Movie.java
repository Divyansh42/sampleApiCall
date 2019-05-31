package com.jss.sampleapicall.Models;

public class Movie {
    private String movie_name;
    private String movie_year;
    private String movie_img_url;

    public Movie(String movie_name, String movie_rating, String movie_img_url) {
        this.movie_name = movie_name;
        this.movie_year = movie_rating;
        this.movie_img_url = movie_img_url;
    }



    public String getMovie_name() {
        return movie_name;
    }

    public void setMovie_name(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getMovie_year() {
        return movie_year;
    }

    public void setMovie_year(String movie_year) {
        this.movie_year = movie_year;
    }

    public String getMovie_img_url() {
        return movie_img_url;
    }

    public void setMovie_img_url(String movie_img_url) {
        this.movie_img_url = movie_img_url;
    }



}
