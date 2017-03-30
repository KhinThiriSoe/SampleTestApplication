package com.khinthirisoe.sampletestapplication.data.api;

import com.khinthirisoe.sampletestapplication.data.model.Books;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by khinthirisoe on 3/30/17.
 */

public interface ApiInterface {

    @GET("service/v2/upcomingGuides")
    Call<Books> getBookLists();

}
