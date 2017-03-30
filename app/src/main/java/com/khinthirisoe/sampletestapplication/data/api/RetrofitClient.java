package com.khinthirisoe.sampletestapplication.data.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.khinthirisoe.sampletestapplication.BuildConfig.DEBUG;

/**
 * Created by khinthirisoe on 3/30/17.
 */

public class RetrofitClient {

    public static String IP = "guidebook.com";

    public static ApiInterface apiInterface;

    public static ApiInterface getApiInterface() {
        if (apiInterface == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            if (DEBUG) {
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            } else {
                logging.setLevel(HttpLoggingInterceptor.Level.NONE);
            }

            okhttp3.OkHttpClient client = new okhttp3.OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit.Builder restBuilder = new Retrofit.Builder()
                    .baseUrl("http://" + IP + "/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson));

            apiInterface = restBuilder.build().create(ApiInterface.class);
        }
        return apiInterface;
    }
}
