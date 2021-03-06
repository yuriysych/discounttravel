package com.androidapp.ysych.discounttravel.sync;

import com.androidapp.ysych.discounttravel.model.Country;
import com.androidapp.ysych.discounttravel.model.Tours;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface ARIRetrofit {

    @GET(APIContract.INDEX_PHP)
    void getTours(
            @Query("version") int version, Callback<Tours> callback
    );
    @GET(APIContract.INDEX_PHP)
    void getCategories(
            @Query("id_content") int category, Callback<List<Country>> callback
    );
}